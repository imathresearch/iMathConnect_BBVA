package com.imath.connect.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.InstanceRest.InstanceDTO;
import com.imath.connect.rest.UserConnectRest.UserConnectDTO;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;
import com.imath.connect.util.IMathCloudInterface;
import com.imath.connect.security.SecurityManager;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class ProjectRest {
    @Inject private ProjectController pc;
    @Inject private UserConnectController ucc;
    @Inject private InstanceController ic;
    @Inject private IMathCloudInterface imathcloud;
    
    @Inject private Logger LOG;
    
    @GET
    @Path(Constants.newProject + "/{uuid_user}/{name}/{desc}/{uuid_instance}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newProject(@PathParam("uuid_user") String uuid_user, @PathParam("name") String name, @PathParam("desc") String desc, 
            @PathParam("uuid_instance") String uuid_instance, @Context SecurityContext sc) {
        ProjectDTO projectDTO = null;
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Instance instance = ic.getInstance(uuid_instance);
            Project project = pc.newProject(name, desc, owner, instance, imathcloud);
            projectDTO = new ProjectDTO();
            projectDTO.convert(project, null);
            return Response.status(Response.Status.OK).entity(projectDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.updateProject + "/{uuid_user}/{uuid_project}/{desc}/{uuid_instance}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProject(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @PathParam("desc") String desc, @PathParam("uuid_instance") String uuid_instance, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Project project = pc.getProject(uuid_project);
            if (!project.getOwner().getUUID().equals(owner.getUUID())) {
                throw new Exception("Not enough privileges");
            }
            project = pc.updateProject(uuid_project, desc, uuid_instance);
            ProjectDTO retDTO = convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
            
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path(Constants.addCollaborator + "/{uuid_user}/{uuid_project}/{uuid_col}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCollaborator(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @PathParam("uuid_col") String uuid_col, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Project project = pc.getProject(uuid_project);
            if (!project.getOwner().getUUID().equals(owner.getUUID())) {
                throw new Exception("Not enough privileges");
            }
            List<String> uuids = new ArrayList<String>();
            uuids.add(uuid_col);
            project = pc.addCollaborators(uuid_project, uuids);
            ProjectDTO retDTO = convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    
    /**
     * Add a collaborator by username or email
     * @param uuid_user
     * @param uuid_project
     * @param other         The user name or email of the collaborator
     * @param sc
     * @return
     */
    @POST
    @Path(Constants.addCollaboratorByUserNameOrEmail + "/{uuid_user}/{uuid_project}/{other}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCollaboratorByOther(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @PathParam("other") String other, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Project project = pc.getProject(uuid_project);
            if (!project.getOwner().getUUID().equals(owner.getUUID())) {
                throw new Exception("Not enough privileges");
            }
            UserConnect collaborator;
            if(other.contains("@")) {
                collaborator = ucc.getUserByEMail(other);
            } else {
                collaborator = ucc.getUserConnectByUserName(other);
            }
            List<String> uuids = new ArrayList<String>();
            uuids.add(collaborator.getUUID());
            project = pc.addCollaborators(uuid_project, uuids);
            ProjectDTO retDTO = convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path(Constants.removeCollaborator + "/{uuid_user}/{uuid_project}/{uuid_col}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCollaborator(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @PathParam("uuid_col") String uuid_col, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Project project = pc.getProject(uuid_project);
            if (!project.getOwner().getUUID().equals(owner.getUUID())) {
                throw new Exception("Not enough privileges");
            }
            project = pc.removeCollaborator(uuid_project, uuid_col);
            ProjectDTO retDTO = convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path(Constants.removeProject + "/{uuid_user}/{uuid_project}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeProject(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Project project = pc.getProject(uuid_project);
            // We check that the user is the owner of the project
            if (!project.getOwner().getUUID().equals(owner.getUUID())) {
                throw new Exception("Not enough privileges");
            }
            // We check that the list of collaborators is empty
            List<UserConnect> users =ucc.getCollaborationUsersByProject(project.getUUID());
            if (users.size()>0) {
                throw new Exception("Not possible to remove the project if it has collaborators");
            }
            pc.removeProject(project.getUUID(), imathcloud);
            return Response.status(Response.Status.OK).build();
            
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(Constants.getProject + "/{uuid_user}/{uuid_project}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProject(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @Context SecurityContext sc) {
        //TODO: IT
        LOG.info("getProject called with uuid: " + uuid_user + " and uuid_project:" + uuid_project);
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            Project project = pc.getProject(uuid_project);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            if (!owner.getUUID().equals(project.getOwner().getUUID()))  {
                throw new Exception ("No privileges");
            }
            ProjectDTO retDTO = convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    // WARNING: This is a security hole!!!!!
    // TODO: change it before production release
    @GET
    @Path(Constants.getProjectCredentials + "/{uuid_user}/{uuid_project}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectCredentials(@PathParam("uuid_user") String uuid_user, @PathParam("uuid_project") String uuid_project, @Context SecurityContext sc) {
        // TODO IT
        try {
            UserConnect user = ucc.getUserConnect(uuid_user);
            Project project = pc.getProject(uuid_project);
            SecurityManager.secureBasic(user.getUserName(), sc);
            System.out.println(user.getUUID());
            UserConnect userOnwer = project.getOwner();
            System.out.println(userOnwer.getUUID());
            if (!user.getUUID().equals(project.getOwner().getUUID())) {    // If the petitioner is not the owner of the project we check if its a collaborator 
                List<UserConnect> cols = ucc.getCollaborationUsersByProject(uuid_project);
                Iterator<UserConnect> it = cols.iterator();
                boolean found = false;
                while(it.hasNext() && !found) {
                    UserConnect col = it.next();
                    found = (col.getUUID().equals(user.getUUID()));
                }
                if (!found) throw new Exception ("Not enough privileges");
            }
            // At this point we know that the user is the owner of the project or a collaborator
            ProjectAdminDTO retDTO = new ProjectAdminDTO();
            retDTO.convert(project);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.ownProjects + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnProjects(@PathParam("uuid_user") String uuid_user, @Context SecurityContext sc) {
    	LOG.info("getOwnProjects called with uuid: " + uuid_user);
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            List<Project> myProjects = pc.getOwnProjects(uuid_user);
            List<ProjectDTO> retDTO = convertList(myProjects);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(Constants.colProjects + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColProjects(@PathParam("uuid_user") String uuid_user, @Context SecurityContext sc) {
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            List<Project> myProjects = pc.getCollaborationProjects(uuid_user);
            List<ProjectDTO> retDTO = convertList(myProjects);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    private ProjectDTO convert(Project project) {
        ProjectDTO elemDTO = new ProjectDTO();
        List<UserConnect> users =ucc.getCollaborationUsersByProject(project.getUUID()); 
        elemDTO.convert(project, users);
        return elemDTO;
    }
    
    private List<ProjectDTO> convertList(List<Project> projects) {
        List<ProjectDTO> retDTO = new ArrayList<ProjectDTO>();
        if (projects != null) { 
            for(Project p: projects) {
                //ProjectDTO elemDTO = new ProjectDTO();
                //List<UserConnect> users =ucc.getCollaborationUsersByProject(p.getUUID()); 
                //elemDTO.convert(p, users);
                retDTO.add(convert(p));
            }
        }
        return retDTO;
    }
    
    static public class ProjectAdminDTO {
        public String UUID;
        public String name;
        public String key;
        public String linuxGroup;
        public String url;
        public void convert(Project project) {
            this.UUID = project.getUUID();
            this.name = project.getName();
            this.key = project.getKey();
            this.linuxGroup = project.getLinuxGroup();
            this.url = project.getInstance().getUrl();
        }
    }
    
    static public class ProjectDTO {
        public String UUID;
        public Date creationDate;
        public String name;
        public String desc;
        public InstanceDTO instance;
        public UserConnectDTO owner;
        public List<UserConnectDTO> userCol; 
        
        public void convert(Project project, List<UserConnect> users) {
            this.UUID = project.getUUID();
            this.creationDate = project.getCreationDate();
            this.name = project.getName();
            this.desc = project.getDescription();
            this.instance = new InstanceDTO();
            this.instance.convert(project.getInstance());
            this.owner = new UserConnectDTO();
            if (project.getOwner()!=null) {
                this.owner.convert(project.getOwner());
            }
            this.userCol = new ArrayList<UserConnectDTO>();
            if(users!=null) {
            	for(UserConnect user:users) {
            		UserConnectDTO newDTO = new UserConnectDTO();
            		newDTO.convert(user);
            		this.userCol.add(newDTO);
            	}
            }
        }
    }
    
    // Just for testing purposes
    
    public ProjectController getProjectController() {
    	return this.pc;
    }
    
    public void setIMathCloud(IMathCloudInterface imathcloud) {
        this.imathcloud = imathcloud;
    }
}
