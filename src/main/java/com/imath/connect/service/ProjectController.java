package com.imath.connect.service;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.util.Encryptor;
import com.imath.connect.util.IMathCloudAccess;
import com.imath.connect.util.IMathCloudInterface;
import com.imath.connect.util.Util;

/**
 * @author imath
 *
 * The controller class for {@link Instance}
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProjectController extends AbstractController {
    
    @Inject UserConnectController ucc;
    @Inject InstanceController ic;
    
    @Inject IMathCloudInterface imathcloud;
    
    /**
     * Creates and return a new Project
     * @param name
     * @param desc
     * @param owner
     * @param instance
     * @return The newly created project
     * @throws Exception If persistence fails
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project newProject(String name, String desc, UserConnect owner, Instance instance) throws Exception {
        Encryptor.init();
        Project project = new Project();
        project.setName(name);
        project.setDescription(desc);
        project.setCreationDate(new Date());
        project.setOwner(owner);
        project.setInstance(instance);
        project.setKey(Util.randomString());
        project.setLinuxGroup(name+"XYZ"+owner.getUserName());
        db.makePersistent(project);
        try {
        	imathcloud.newProject(project.getLinuxGroup(), project.getKey(), instance.getUrl());
        } catch (Exception e) {
        	// Don't get why the project is created anyway! On exception, the transaction should roll back
        	// Investigate
        	db.delete(project);
        	throw e;
        }
        return project;
    }
    
    //********* for testing purposes only
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project newProject(String name, String desc, UserConnect owner, Instance instance, IMathCloudInterface imathcloud) throws Exception {
    	this.imathcloud = imathcloud;
    	return newProject(name,desc,owner,instance);
    }
    //*********
    /**
     * Updates the project data
     * @param uuid
     * @param desc
     * @param uuid_instance
     * @return
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project updateProject(String uuid, String desc, String uuid_instance) throws Exception {
        Project project = this.getProject(uuid);
        Instance instance = ic.getInstance(uuid_instance);
        project.setInstance(instance);
        project.setDescription(desc);
        db.makePersistent(project);
        return project;
    }
    
    /**
     * Adds a list of Users to the collaborator list 
     * @param UUID_project
     * @param users
     * @return the new {@link Project}
     * @throws EntityNotFoundException
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project addCollaborators(String UUID_project, List<String> users) throws EntityNotFoundException, Exception {
        
        Project project = this.getProject(UUID_project);
        if (users==null) return project;
        if (users.size()==0) return project;
        Set<UserConnect> cols = project.getCollaborators();
        if (cols==null) {
            cols = new HashSet<UserConnect>();
        }
        for(String uuid:users) {
            UserConnect userConnect = ucc.getUserConnect(uuid);
            cols.add(userConnect);
        }
        project.setCollaborators(cols);
        db.makePersistent(project);
        return project;
    }
    
    /**
     * Removes the given uuid_user from the list of collaborators of the given uuid_project
     * @param UUID_project
     * @param UUID_user
     * @return The updated {@link Project}
     * @throws EntityNotFoundException
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project removeCollaborator(String UUID_project, String UUID_user) throws EntityNotFoundException, Exception {
        Project project = this.getProject(UUID_project);
        if (UUID_user==null) return project;
        Set<UserConnect> cols = project.getCollaborators();
        if (cols==null) {
            return project;
        }
        UserConnect toRemove = null;
        for(UserConnect userConnect:cols) {
            if (userConnect.getUUID().equals(UUID_user)) {
                toRemove = userConnect;
            }
        }
        if (toRemove == null) return project;
        cols.remove(toRemove);
        project.setCollaborators(cols);
        db.makePersistent(project);
        return project;
    }
    
    /**
     * Returns the projects owned by the given UserConnect UUID
     * @param UUID
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Project> getOwnProjects(String UUID_user) {
    	Encryptor.init();
        List<Project> projects = db.getProjectDB().findByOwner(UUID_user);
        // We do this to have access without open transaction to all data of the project
        return projects;
    }
    
    /**
     * Returns the projects in which the given UserConnect UUID is collaborating 
     * @param UUID
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Project> getCollaborationProjects(String UUID_user) {
    	Encryptor.init();
        return db.getProjectDB().findByCollaborators(UUID_user);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project getProject(String UUID) throws EntityNotFoundException {
    	Encryptor.init();
        Project project = this.db.getProjectDB().findById(UUID);
        if (project == null) {
            throw new EntityNotFoundException();  
        }
        return project;
    }
    
    // just for testing purposes
    
    public void setUserConnectController(UserConnectController ucc) {
        this.ucc = ucc;
    }
    
    public void setIMathCloudAccess(IMathCloudInterface imathcloud) {
    	this.imathcloud = imathcloud;
    }
}
