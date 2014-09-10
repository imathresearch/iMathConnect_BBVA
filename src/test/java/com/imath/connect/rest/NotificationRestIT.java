package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.imath.connect.model.Notification;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.NotificationRest.NotificationDTO;
import com.imath.connect.rest.ProjectRest.ProjectDTO;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.NotificationController;
import com.imath.connect.service.UserConnectController;

@RunWith(Arquillian.class)
public class NotificationRestIT extends AbstractIT{
	@Inject InstanceController ic;
    @Inject UserConnectController ucc;
    @Inject NotificationController nc;
    @Inject NotificationRest ntEndPoint;
    
    @Test
    public void getNotificationsIT() throws Exception {
    	// 1.- Base case: User does not exists
        Response rest = ntEndPoint.getNotifications("no user", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Base case: User exists but no notifications yet
        UserConnect owner1 = ucc.newUserConnect("ammartinez", "amt@amt.com", "iath", "953333402", "933383402");
        rest = ntEndPoint.getNotifications(owner1.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<NotificationDTO> notificationsDTO = (List<NotificationDTO>) rest.getEntity();
        assertEquals(0,notificationsDTO.size());
        
        // 3.- Normal case: We add several public and private notifications
        
        // This notification should be retrieved by user owner1 (created before the notification)
        Notification n0 = nc.newNotification("Public Notification 0", "This is a public notification nº0", 0, null);
        
        UserConnect owner2 = ucc.newUserConnect("andrea", "andrea@andrea.com", "rriath", "933333402", "944383402");
        
        // This notification should be retrieved for both users
        Notification n1 = nc.newNotification("Public Notification 1", "This is a public notification nº1", 0, null);
        
        List<String> uuids = new ArrayList<String>();
        uuids.add(owner2.getUUID());
        
        // This notification should be retrieved by user owner2
        Notification n2 = nc.newNotification("Private Notification 0", "This is a private notification nº0", 1, uuids);
        
        uuids.add(owner1.getUUID());
        
        // This notification should be retrieved for both users
        Notification n3 = nc.newNotification("Private Notification 1", "This is a private notification nº1", 1, uuids);
        
        rest = ntEndPoint.getNotifications(owner2.getUUID(), null);
        
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        notificationsDTO = (List<NotificationDTO>) rest.getEntity();       
        assertEquals(3,notificationsDTO.size());
        for(NotificationDTO ntDTO: notificationsDTO) {
            Notification n = null;
            if (ntDTO.UUID.equals(n1.getUUID())) {
                n = n1;
            } else{
            	if (ntDTO.UUID.equals(n2.getUUID())) {
            		n = n2;
            	}
                else{
                	if (ntDTO.UUID.equals(n3.getUUID())){
                		n = n3;
                	}
                	
                }
            }
            assertEquals(n.getSubject(), ntDTO.subject);
            assertEquals(n.getText(), ntDTO.text);
            assertEquals(n.getType(), ntDTO.type);
        }
        
        // 3.1. We retrieve the notification of the user to make sure everything works
        rest = ntEndPoint.getNotifications(owner1.getUUID(), null);
        
        notificationsDTO = (List<NotificationDTO>) rest.getEntity();        
        assertEquals(3,notificationsDTO.size());
        for(NotificationDTO ntDTO: notificationsDTO) {
            Notification n = null;
            if (ntDTO.UUID.equals(n0.getUUID())) {
                n = n0;
            } else{
            	if (ntDTO.UUID.equals(n1.getUUID())) {
            		n = n1;
            	}
                else{
                	if (ntDTO.UUID.equals(n3.getUUID())){
                		n = n3;
                	}
                	
                }
            }
            assertEquals(n.getSubject(), ntDTO.subject);
            assertEquals(n.getText(), ntDTO.text);
            assertEquals(n.getType(), ntDTO.type);
        }
        
        // 4. Modify the current connection time (and therefore the last connection) to check that the corrects
        // notifications are retrieved.
        // Setting twice the currentConnection, lastConnection value will be bigger that the creation date of any previous notification
        ucc.setCurrentConnection(owner1.getUUID());
        ucc.setCurrentConnection(owner1.getUUID());
        rest = ntEndPoint.getNotifications(owner1.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());        
        notificationsDTO = (List<NotificationDTO>) rest.getEntity();
        assertEquals(0,notificationsDTO.size());
        
        
    }

}
