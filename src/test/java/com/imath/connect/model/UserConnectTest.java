package com.imath.connect.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserConnectTest {
    UserConnect user;
    String UUID = "IDENT";
    Date creation = new Date();
    Date lastCon = new Date();
    Date lastMessage = new Date();
    String eMail = "test@test.com";
    String phone1="1234567";
    String phone2 = "987654";
    String userName = "username";
    String org = "imath";
    
    @Before
    public void setUp() throws Exception {
        user = new UserConnect();
        user.setUUID(UUID);
        user.setCreationDate(creation);
        user.setLastConnection(lastCon);
        user.setEMail(eMail);
        user.setPhone1(phone1);
        user.setPhone2(phone2);
        user.setUserName(userName);
        user.setOrganization(org);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetUUID() {
        assertEquals(UUID,user.getUUID());
    }
    
    @Test
    public void testGetLastConnection() {
        assertEquals(lastCon, user.getLastConnection());
    }
    
    @Test
    public void testCreationDate() {
        assertEquals(creation, user.getCreationDate());
    }
    
    @Test
    public void testGetEMail() {
        assertEquals(eMail, user.getEMail());
    }
    
    @Test
    public void testGetPhone1() {
        assertEquals(phone1, user.getPhone1());
    }
    
    @Test
    public void testGetPhone2() {
        assertEquals(phone2, user.getPhone2());
    }
    
    @Test
    public void testGetOrganization() {
        assertEquals(org, user.getOrganization());
    }
}
