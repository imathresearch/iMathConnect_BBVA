package com.imath.connect.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAccessTest {
	
	UserAccess access;
    String UUIDA = "IDENT";
    String password = "PASS";
	String accessSource = "SRC";
	UserConnect user;
    String UUID = "IDENT";
    Date creation = new Date();
    Date lastCon = new Date();
    Date currCon = new Date();
    Date lastMessage = new Date();
    String eMail = "test@test.com";
    String phone1="1234567";
    String phone2 = "987654";
    String userName = "username";
    String org = "imath";
    byte[] urlPhoto = new byte[2];
    
	 @Before
	    public void setUp() throws Exception {
	        access = new UserAccess();
	        access.setAccessSource(accessSource);
	        access.setPassword(password);
	        access.setUUID(UUIDA);
	        user = new UserConnect();
	        user.setUUID(UUID);
	        user.setCreationDate(creation);
	        user.setLastConnection(lastCon);
	        user.setCurrentConnection(currCon);
	        user.setEMail(eMail);
	        user.setPhone1(phone1);
	        user.setPhone2(phone2);
	        user.setUserName(userName);
	        user.setOrganization(org);
	        user.setPhoto(urlPhoto);
	        access.setUser(user);
	        user.setUserAccess(access);
	    }
	    
	    @After
	    public void tearDown() throws Exception {
	    }
	    
	    @Test
	    public void testGetUUID() {
	        assertEquals(UUIDA,access.getUUID());
	    }
	    
	    @Test
	    public void testGetPassword() {
	        assertEquals(password, access.getPassword());
	    }
	    
	    @Test
	    public void testGetAccessSource() {
	        assertEquals(accessSource, access.getAccessSource());
	    }
	    
	    @Test
	    public void testGetUser() {
	        assertEquals(user, access.getUser());
	    }
	    	    

}
