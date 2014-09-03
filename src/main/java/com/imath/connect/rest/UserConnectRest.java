package com.imath.connect.rest;

import java.util.Date;
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
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class UserConnectRest {
    @Inject private Logger LOG;
    @Inject private UserConnectController ucc;
    
    @GET
    @Path(Constants.getUserByUserName + "/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUserName(@PathParam("userName") String userName, @Context SecurityContext sc) {
        try {
            UserConnect user = ucc.getUserConnectByUserName(userName);
            SecurityManager.secureBasic(user.getUserName(), sc);
            UserConnectDTO retDTO = new UserConnectDTO();
            retDTO.convert(user);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.getUser + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("uuid_user") String uuid, @Context SecurityContext sc) {
        try {
            UserConnect user = ucc.getUserConnect(uuid);
            SecurityManager.secureBasic(user.getUserName(), sc);
            UserConnectDTO retDTO = new UserConnectDTO();
            retDTO.convert(user);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    public static class UserConnectDTO {
        public String UUID;
        public String userName;
        public String eMail;
        public String phone1;
        public String phone2;
        public String organization;
        public Date creationDate;
        public Date lastConnection;
        
        public void convert(UserConnect user) {
            this.UUID = user.getUUID();
            this.userName = user.getUserName();
            this.eMail = user.getEMail();
            this.phone1 = user.getPhone1();
            this.phone2 = user.getPhone2();
            this.creationDate = user.getCreationDate();
            this.lastConnection = user.getLastConnection();
            this.organization = user.getOrganization();
        }
    }
}
