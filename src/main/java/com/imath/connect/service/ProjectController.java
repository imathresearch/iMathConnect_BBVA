package com.imath.connect.service;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.util.Encryptor;
import com.imath.connect.util.Util;

/**
 * @author imath
 *
 * The controller class for {@link Instance}
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ProjectController extends AbstractController {
    
    @Inject 
    UserConnectController ucc;
    
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
        return db.getProjectDB().findByOwner(UUID_user);
    }
    
    /**
     * Returns the projects in which the given UserConnect UUID is collaborating 
     * @param UUID
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Project> getCollaborationProjects(String UUID_user) {
        return db.getProjectDB().findByCollaborators(UUID_user);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Project getProject(String UUID) throws EntityNotFoundException {
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
}
