package com.imath.connect.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.imath.connect.data.MainDB;
import com.imath.connect.data.NotificationDB;
import com.imath.connect.data.UserConnectDB;
import com.imath.connect.model.Notification;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;

public class NotificationControllerTest {
    private MainDB db;
    private NotificationController nc;
    
    @Mock 
    UserConnectController ucc;
    
    @Mock
    private Logger LOG;
    
    @Mock
    private EntityManager em;
    
    @Mock 
    private UserConnectDB userConnectDB;
    
    @Mock
    private NotificationDB notificationDB;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        db = new MainDB();
        db.setEntityManager(em);
        db.setUserConnectDB(userConnectDB);
        db.setNotificationDB(notificationDB);
        
        nc = new NotificationController();
        nc.setLog(LOG);
        nc.setMainDB(db);
        nc.setUserConnectController(ucc);
    }
    
    @Test
    public void newNotificationTest() throws Exception {
        String subject = "subject";
        String text = "text";
        Integer type = 0;
        UserConnect user1 = new UserConnect();
        UserConnect user2 = new UserConnect();
        String uuid1 = "uuid1";
        String uuid2 = "uuid2";
        user1.setUUID(uuid1);
        user2.setUUID(uuid2);
        
        // 1.- Type exception. If type <> 0 or <> 1 exception
        try {
            nc.newNotification(subject, text, 2, null);
            fail("exception was excpeted");
        } catch (Exception e) {
            // Fine
        }
        try {
            nc.newNotification(subject, text, -1, null);
            fail("exception was excpeted");
        } catch (Exception e) {
            // Fine
        }
        
        // 2.- Standard case
        when(ucc.getUserConnect(uuid1)).thenReturn(user1);
        when(ucc.getUserConnect(uuid2)).thenReturn(user2);
        List<String> uuids = new ArrayList<String>();
        uuids.add(uuid2);
        uuids.add(uuid1);
        Notification notif = nc.newNotification(subject, text, type, uuids);
        assertEquals(subject, notif.getSubject());
        assertEquals(text, notif.getText());
        assertEquals(type, notif.getType());
        Set<UserConnect> rets = notif.getNotificationUsers();
        assertEquals(2, rets.size());
        int c1=0;
        int c2=0;
       
        for(UserConnect u:rets) {
            if (u.getUUID().equals(uuid1)) c1++;
            if (u.getUUID().equals(uuid2)) c2++;
        }
        assertEquals(1,c1);
        assertEquals(1,c2);
        verify(em).persist((Notification)Matchers.anyObject());
    }
}
