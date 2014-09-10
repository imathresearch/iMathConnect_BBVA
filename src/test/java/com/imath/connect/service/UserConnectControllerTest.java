package com.imath.connect.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.Date;
import java.util.logging.Logger;

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

public class UserConnectControllerTest {
    private UserConnectController pc;
    private MainDB db;
    
    @Mock
    private Logger LOG;
    
    @Mock
    private EntityManager em;
    
    @Mock 
    private UserConnectDB userConnectDB;
    
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
        
        UserConnect peer = pc.newUserConnect(userName, eMail, org, phone1, phone2);
        assertEquals(eMail, peer.getEMail());
        assertEquals(peer.getCreationDate(), peer.getLastConnection());
        assertNotNull(peer.getCreationDate());
        assertEquals(phone1, peer.getPhone1());
        assertEquals(phone2, peer.getPhone2());
        assertEquals(userName, peer.getUserName());
        assertEquals(org, peer.getOrganization());
        verify(em).persist((UserConnect)Matchers.anyObject());
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
    
    private UserConnect createUserConnect(String UUID) {
        UserConnect peer = new UserConnect();
        peer.setUUID(UUID);
        peer.setCurrentConnection(new Date());
        return peer;
    }
}
