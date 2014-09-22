package com.imath.connect.rest;

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

import com.imath.connect.model.UserConnect;
import com.imath.connect.security.SecurityManager;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class GeneralRest {
    
    @Inject private UserConnectController ucc;
    @Inject private InstanceController ic;
    @Inject private ProjectController pc;
    @Inject private Logger LOG;
    
    @GET
    @Path(Constants.getInfo + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@PathParam("uuid_user") String uuid_user, @Context SecurityContext sc) {
        LOG.info("[iMath][" + Constants.getInfo + "]");
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            InfoDTO ret = new InfoDTO();
            
            ret.numProjects = pc.countProjects();
            ret.numInstances = ic.countInstances();
            ret.numUsers = ucc.countUsers();
            ret.numUsersCol = ucc.countUsersCol();
            
            return Response.status(Response.Status.OK).entity(ret).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    static public class InfoDTO {
        public long numProjects;
        public long numInstances;
        public long numUsers;
        public long numUsersCol;
    }
}
