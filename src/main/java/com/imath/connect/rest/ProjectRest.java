package com.imath.connect.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
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
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;
import com.imath.connect.security.SecurityManager;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class ProjectRest {
    @Inject private ProjectController pc;
    @Inject private UserConnectController ucc;
    @Inject private InstanceController ic;
    @Inject private Logger LOG;
    
    @GET
    @Path(Constants.newProject + "/{name}/{desc}/{uuid_user}/{uuid_instance}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newProject(@PathParam("name") String name, @PathParam("desc") String desc, 
            @PathParam("uuid_user") String uuid_user, @PathParam("uuid_instance") String uuid_instance, @Context SecurityContext sc) {
        
        ProjectDTO projectDTO = null;
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            Instance instance = ic.getInstance(uuid_instance);
            Project project = pc.newProject(name, desc, owner, instance);
            projectDTO = new ProjectDTO();
            projectDTO.convert(project);
            return Response.status(Response.Status.OK).entity(projectDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.ownProjects)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnProjects(String uuid_user, @Context SecurityContext sc) {
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
    @Path(Constants.colProjects)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColProjects(String uuid_user, @Context SecurityContext sc) {
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
    
    private List<ProjectDTO> convertList(List<Project> projects) {
        List<ProjectDTO> retDTO = new ArrayList<ProjectDTO>();
        if (projects != null) { 
            for(Project p: projects) {
                ProjectDTO elemDTO = new ProjectDTO();
                elemDTO.convert(p);
                retDTO.add(elemDTO);
            }
        }
        return retDTO;
    }
    
    static public class ProjectDTO {
        public String UUID;
        public Date creationDate;
        public String name;
        public String desc;
        
        public void convert(Project project) {
            this.UUID = project.getUUID();
            this.creationDate = project.getCreationDate();
            this.name = project.getName();
            this.desc = project.getDescription();
        }
    }
}
