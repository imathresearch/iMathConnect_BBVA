package com.imath.connect.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotificationTest {
    Notification test;
    String uUID = "ID";
    String subject = "subject";
    String text = "the text";
    Date creationDate = new Date();
    Integer type = 1;
    Set<UserConnect> users = new HashSet<UserConnect>();
    
    
    @Before
    public void setUp() throws Exception {
        test = new Notification();
        test.setUUID(uUID);
        test.setCreationDate(creationDate);
        test.setSubject(subject);
        test.setText(text);
        test.setType(type);
        test.setNotificationUsers(users);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetUUID() {
        assertEquals(uUID,test.getUUID());
    }
    
    @Test
    public void testGetCreationDate() {
        assertEquals(creationDate.getTime(),test.getCreationDate().getTime());
    }
    
    @Test
    public void testGetSubject() {
        assertEquals(subject,test.getSubject());
    }
    
    @Test
    public void testGetText() {
        assertEquals(text,test.getText());
    }
    
    @Test
    public void testGetType() {
        assertEquals(type,test.getType());
    }
    
    @Test
    public void testGetUsers() {
        assertEquals(users,test.getNotificationUsers());
    }
}
