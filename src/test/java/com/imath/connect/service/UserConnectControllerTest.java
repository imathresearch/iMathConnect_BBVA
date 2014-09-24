package com.imath.connect.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers;

import com.imath.connect.data.MainDB;
import com.imath.connect.data.UserConnectDB;
import com.imath.connect.model.UserConnect;
import com.imath.connect.util.Constants;
import com.imath.connect.util.Photo;

public class UserConnectControllerTest {
    private UserConnectController pc;
    private MainDB db;
    
    @Mock
    private Logger LOG;
    
    @Mock
    private EntityManager em;
    
    @Mock 
    private UserConnectDB userConnectDB;
    
    private Photo photo = new Photo();
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        db = new MainDB();
        db.setEntityManager(em);
        db.setUserConnectDB(userConnectDB);
        
        pc = new UserConnectController();
        pc.setMainDB(db);
        pc.setLog(LOG);
    }

    @Test
    public void newUserConnectTest() throws Exception {
        String userName = "user";
        String eMail = "test@test.com";
        String org = "imath";
        String phone1 ="933333333";
        String phone2 = "111222334";
        String RECOVER_IMAGE_NAME = "blue-arr.png"; // Image example
        byte[] photoByte = photo.getPhotoByte(RECOVER_IMAGE_NAME);
        
        UserConnect peer = pc.newUserConnect(userName, eMail, org, phone1, phone2, photoByte);
        assertEquals(eMail, peer.getEMail());
        assertEquals(peer.getCreationDate(), peer.getLastConnection());
        assertNotNull(peer.getCreationDate());
        assertEquals(phone1, peer.getPhone1());
        assertEquals(phone2, peer.getPhone2());
        assertEquals(userName, peer.getUserName());
        assertEquals(org, peer.getOrganization());
        assertArrayEquals(photoByte, peer.getPhoto());
        verify(em).persist((UserConnect)Matchers.anyObject());
    }
    
    
    @Test
    public void newUserConnectInvitationTest() throws Exception {
        String eMail = "t_e_s2t89@test.com";
        String expectedUserName = "test";
        String expectedUserNameAlter = "testATtest";
        //1.- Exception case: no valid email is passed
        try {
            pc.newUserConnectInvitation("");
            fail("Expected Exception");
        } catch (Exception e) {
            verify(em,times(0)).persist((UserConnect)Matchers.anyObject());
        }
        try {
            pc.newUserConnectInvitation("NoMail");
            fail("Expected Exception");
        } catch (Exception e) {
            // Fine
            verify(em,times(0)).persist((UserConnect)Matchers.anyObject());
        }
        try {
            pc.newUserConnectInvitation("mail@mail@mail.com");
            fail("Expected Exception");
        } catch (Exception e) {
            // Fine
            verify(em,times(0)).persist((UserConnect)Matchers.anyObject());
        }
        
        //2.- Happy path. first username does not exists:
        when(db.getUserConnectDB().findByUserName(expectedUserName)).thenReturn(null);
        UserConnect user = pc.newUserConnectInvitation(eMail);
        assertEquals(expectedUserName, user.getUserName());
        assertEquals(eMail, user.getEMail());
        assertEquals("test", user.getOrganization());
        assertTrue(user.getCurrentConnection()==null);
        assertTrue(user.getLastConnection()==null);
        verify(em).persist((UserConnect)Matchers.anyObject());
        
        //3.- Happy path. first username exists but composed no
        UserConnect userExist = new UserConnect();
        when(db.getUserConnectDB().findByUserName(expectedUserName)).thenReturn(userExist);
        when(db.getUserConnectDB().findByUserName(expectedUserNameAlter)).thenReturn(null);
        user = pc.newUserConnectInvitation(eMail);
        assertEquals(expectedUserNameAlter, user.getUserName());
        assertEquals(eMail, user.getEMail());
        assertEquals("test", user.getOrganization());
        assertTrue(user.getCurrentConnection()==null);
        assertTrue(user.getLastConnection()==null);
        verify(em, times(2)).persist((UserConnect)Matchers.anyObject());
        
        //4.- Exception path. All usernames exist
        UserConnect userExist2 = new UserConnect();
        when(db.getUserConnectDB().findByUserName(expectedUserName)).thenReturn(userExist);
        when(db.getUserConnectDB().findByUserName(expectedUserNameAlter)).thenReturn(userExist2);
        try {
            pc.newUserConnectInvitation(eMail);
            fail();
        } catch (Exception e) {
            //Fine
            verify(em, times(2)).persist((UserConnect)Matchers.anyObject());
        }
    }
    
    //Happy Path setLastConnection
    @Test
    public void setCurrentConnectionTest() throws Exception {
        String UUID = "ident";
        UserConnect peer = createUserConnect(UUID);
        when(db.getUserConnectDB().findById(UUID)).thenReturn(peer);
        pc.setCurrentConnection(UUID);
        
        assertTrue(peer.getCurrentConnection() != null);
        assertTrue(peer.getLastConnection() != null);
        verify(em).persist((UserConnect)Matchers.anyObject());
    }
    
    //Exception Path one setLastConnection
    @Test
    public void setCurrentConnectionExceptionTest() throws Exception {
        String UUID = "ident";
        when(db.getUserConnectDB().findById(UUID)).thenReturn(null);
        try {
            pc.setCurrentConnection(UUID);
            fail();
        } catch (EntityNotFoundException e) {
            // Fine
        } catch (Exception e) {
            fail();
        }
        verify(em,times(0)).persist((UserConnect)Matchers.anyObject());
    }

    //Exception Path two setLastConnection
    @Test
    public void setCurrentConnectionException2Test() throws Exception {
        String UUID = "ident";
        UserConnect peer = createUserConnect(UUID);
        when(db.getUserConnectDB().findById(UUID)).thenReturn(peer);
        doThrow(new TransactionRequiredException()).when(em).persist(peer);
        try {
            pc.setCurrentConnection(UUID);
            fail();
        } catch (PersistenceException e) {
            // Fine
        } catch (Exception e) {
            fail();
        }
    }
    
    //Exception Path two setLastConnection
    @Test
    public void setPhoto() throws Exception {
        
        String UUID = "ident";
        UserConnect peer = new UserConnect();
        peer.setUUID(UUID);
        peer.setCurrentConnection(new Date());
        peer.setPhoto(null);
        when(db.getUserConnectDB().findById(UUID)).thenReturn(peer);
        doThrow(new TransactionRequiredException()).when(em).persist(peer);
        
        try {
            byte[] photoByteFormat = photo.getPhotoByte("logoiMath.jpg");
            pc.updateUserConnectByte(UUID, photoByteFormat);
            fail();
        } catch (PersistenceException e) {
            // Fine
        } catch (Exception e) {
            fail();
        }

        
    }

    
    private UserConnect createUserConnect(String UUID) {
        UserConnect peer = new UserConnect();
        peer.setUUID(UUID);
        peer.setCurrentConnection(new Date());
        peer.setPhoto(null);
        return peer;
    }
    
}
