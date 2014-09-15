package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.AbstractIT.Mock_IMathCloudAccess;
import com.imath.connect.rest.UserConnectRest.UserConnectDTO;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.IMathCloudAccess;
import com.imath.connect.util.IMathCloudInterface;

@RunWith(Arquillian.class)
public class UserConnectRestIT extends AbstractIT {
    @Inject UserConnectController ucc;
    @Inject InstanceController ic;
    @Inject ProjectController pc;
    @Inject UserConnectRest ucrEndPoint;
    
    // We mock iMathCloudAccess
    private IMathCloudInterface imathcloud = new Mock_IMathCloudAccess();
    
    @Before
    public void setUp() throws Exception {
    	pc.setIMathCloudAccess(imathcloud);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void getUserConnectIT() throws Exception {
        //1.- Case Base Exception: DB completely empty
        Response rest = ucrEndPoint.getUser("no id", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        rest = ucrEndPoint.getUserByUserName("no id", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        //2.- Normal Case Exception: We populate the DB but asking for non-existing user
        String userName1 = "user";
        String eMail1 = "hola@gmail.com";
        String org1 = "org";
        String phone11 = "938888888";
        String phone12 = "938833888";
        
        String userName2 = "resu";
        String eMail2 = "aloh@gmail.com";
        String org2 = "gro";
        String phone21 = "931111888";
        String phone22 = "334833888";
        UserConnect user1 = ucc.newUserConnect(userName1, eMail1, org1, phone11, phone12);
        UserConnect user2 = ucc.newUserConnect(userName2, eMail2, org2, phone21, phone22);
        
        rest = ucrEndPoint.getUser("noooo", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        rest = ucrEndPoint.getUserByUserName("noooo", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        //3.- Normal case: asking for real users
        rest = ucrEndPoint.getUser(user1.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        UserConnectDTO userDTO = (UserConnectDTO) rest.getEntity();
        assertEquals(user1.getCreationDate().getTime(), userDTO.creationDate.getTime());
        assertEquals(user1.getLastConnection().getTime(), userDTO.lastConnection.getTime());
        assertEquals(user1.getUserName(), userDTO.userName);
        assertEquals(user1.getEMail(), userDTO.eMail);
        assertEquals(user1.getOrganization(), userDTO.organization);
        assertEquals(user1.getPhone1(), userDTO.phone1);
        assertEquals(user1.getPhone2(), userDTO.phone2);
        
        rest = ucrEndPoint.getUserByUserName(user2.getUserName(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        userDTO = (UserConnectDTO) rest.getEntity();
        assertEquals(user2.getCreationDate().getTime(), userDTO.creationDate.getTime());
        assertEquals(user2.getLastConnection().getTime(), userDTO.lastConnection.getTime());
        assertEquals(user2.getUserName(), userDTO.userName);
        assertEquals(user2.getEMail(), userDTO.eMail);
        assertEquals(user2.getOrganization(), userDTO.organization);
        assertEquals(user2.getPhone1(), userDTO.phone1);
        assertEquals(user2.getPhone2(), userDTO.phone2);
    }
    
    @Test
    public void getCollaboratorsByProjectIT() throws Exception {
        //1.- Case Base Exception: DB completely empty
        Response rest = ucrEndPoint.getColUsersByProject("no project", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        //2.- Normal case: we add a project but does not have collaborators
        String userName1 = "theOnee";
        String email1 = "hola@pepe.com";
        String org1 = "org";
        String phone11 = "988888888";
        String phone12 = "999999999";
        
        UserConnect theOne1 = ucc.newUserConnect(userName1, email1, org1, phone11, phone12);
        UserConnect owner = ucc.newUserConnect("owner", "hla@ppe.com", "iath", "953333402", "933383402");
        Instance instance = ic.newInstance(0, 0, 0, "hola", owner);
        Project project = pc.newProject("myProject", "myProject", owner, instance, imathcloud);
        rest = ucrEndPoint.getColUsersByProject(project.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
		List<UserConnectDTO> users = (List<UserConnectDTO>) rest.getEntity();
        assertEquals(0, users.size());
        
        //3.- Normal case: Now we add some collaborators
        String userName2 = "theOneee";
        String email2 = "holass@pepe.com";
        String org2 = "org";
        String phone21 = "988888888";
        String phone22 = "999999999";
        UserConnect theOne2 = ucc.newUserConnect(userName2, email2, org2, phone21, phone22);
        List<String> users_uuid = new ArrayList<String>();
        users_uuid.add(theOne1.getUUID());
        users_uuid.add(theOne2.getUUID());
        pc.addCollaborators(project.getUUID(), users_uuid);
        rest = ucrEndPoint.getColUsersByProject(project.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
		List<UserConnectDTO> users2 = (List<UserConnectDTO>) rest.getEntity();
        assertEquals(2, users2.size());
        for(UserConnectDTO uDTO: users2) {
        	UserConnect u = null;
        	if (uDTO.UUID.equals(theOne1.getUUID())) {
        		u = theOne1;
        	} else if (uDTO.UUID.equals(theOne2.getUUID())) {
        		u = theOne2;
        	}
        	assertEquals(u.getUserName(), uDTO.userName);
        	assertEquals(u.getOrganization(), uDTO.organization);
        	assertEquals(u.getPhone1(), uDTO.phone1);
        	assertEquals(u.getPhone2(), uDTO.phone2);
        	assertEquals(u.getLastConnection().getTime(), uDTO.lastConnection.getTime());
        	assertEquals(u.getCreationDate().getTime(), uDTO.creationDate.getTime());
        }
        
    }
    
    

}
