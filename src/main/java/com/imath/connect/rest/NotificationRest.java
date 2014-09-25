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

import com.imath.connect.model.Notification;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.ProjectRest.ProjectDTO;
import com.imath.connect.security.SecurityManager;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.NotificationController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class NotificationRest {
	@Inject private NotificationController nc;
	@Inject private UserConnectController ucc;
    @Inject private InstanceController ic;
    @Inject private Logger LOG;
    private static String LOG_PRE = Constants.LOG_PREFIX_SYSTEM + "[NotificationRest]";
    
    @GET
    @Path(Constants.getNotifications + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotifications(@PathParam("uuid_user") String uuid_user, @Context SecurityContext sc) {
        LOG.info(LOG_PRE + "[" + Constants.getNotifications + "]" + uuid_user);
        try {
            UserConnect owner = ucc.getUserConnect(uuid_user);
            List<Notification> myNotifications = nc.getNotificationsByUser(uuid_user);
            SecurityManager.secureBasic(owner.getUserName(), sc);
            List<NotificationDTO> retDTO = convertList(myNotifications);
            System.out.println("Number of notifications " + retDTO.size());
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    private NotificationDTO convert(Notification notification) {
    	NotificationDTO elemDTO = new NotificationDTO();
        elemDTO.convert(notification);
        return elemDTO;
    }
    
    private List<NotificationDTO> convertList(List<Notification> notifications) {
        List<NotificationDTO> retDTO = new ArrayList<NotificationDTO>();
        if (notifications != null) { 
            for(Notification n: notifications) {
                retDTO.add(convert(n));
            }
        }
        return retDTO;
    }
    
    static public class NotificationDTO{
    	public String UUID;
    	public String subject;
    	public String text;
    	public Date creationDate;
    	public Integer type;
    	
    	public void convert(Notification notification){
    		this.UUID = notification.getUUID();
    		this.subject = notification.getSubject();
    		this.text = notification.getText();
    		this.creationDate = notification.getCreationDate();
    		this.type = notification.getType();
    	}
    }

}
