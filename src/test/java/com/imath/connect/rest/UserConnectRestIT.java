package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.UserConnectRest.UserConnectDTO;
import com.imath.connect.service.UserConnectController;

@RunWith(Arquillian.class)
public class UserConnectRestIT extends AbstractIT {
    @Inject UserConnectController ucc;
    @Inject UserConnectRest ucrEndPoint;
    
    @Before
    public void setUp() throws Exception {}
    
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

}
