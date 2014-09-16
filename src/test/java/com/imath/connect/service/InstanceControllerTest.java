package com.imath.connect.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers;

import com.imath.connect.data.InstanceDB;
import com.imath.connect.data.MainDB;
import com.imath.connect.model.Instance;
import com.imath.connect.model.UserConnect;

public class InstanceControllerTest {
    private InstanceController pc;
    private MainDB db;
    
    @Mock
    private Logger LOG;
    
    @Mock
    private EntityManager em;
    
    @Mock 
    private InstanceDB instanceDB;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        db = new MainDB();
        db.setEntityManager(em);
        db.setInstanceDB(instanceDB);
        
        pc = new InstanceController();
        pc.setMainDB(db);
        pc.setLog(LOG);
    }
    
    @Test
    public void newUserConnectTest() throws Exception {
        long cpu = 4;
        double ram = 1.34;
        double stg = 23.4;
        String url = "158.127.56.89:8080";
        String name = "myinstance";
        UserConnect owner = new UserConnect();
        owner.setUUID("ID");
        
        Instance instance = pc.newInstance(cpu, ram, stg, url, name, owner);
        assertEquals(cpu, instance.getCpu());
        assertEquals(ram, instance.getRam(),0.001);
        assertEquals(stg, instance.getStg(),0.001);
        assertEquals(url, instance.getUrl());
        assertNotNull(instance.getCreationDate());
        
        verify(em).persist((Instance)Matchers.anyObject());
    }
    
    @Test
    public void getInstancesTest() throws Exception {
        String UUID = "id";
        String name = "myinstance";
        UserConnect owner = new UserConnect();
        owner.setUUID(UUID);
        List<Instance> publicInstaces = new ArrayList<Instance>();
        publicInstaces.add(pc.newInstance(4, 2, 2, "111.222.111.222",name, null));
        
        List<Instance> privateInstaces = new ArrayList<Instance>();
        privateInstaces.add(pc.newInstance(5, 1, 7, "151.252.151.252", name,owner));
        
        when(db.getInstanceDB().findByPublic()).thenReturn(publicInstaces);
        when(db.getInstanceDB().findByOwner(UUID)).thenReturn(privateInstaces);
        
        List<Instance> tempPublic = pc.getInstances(null);
        List<Instance> tempPrivate = pc.getInstances(UUID);
        assertEquals(publicInstaces, tempPublic);
        assertEquals(privateInstaces, tempPrivate);
    }
}
