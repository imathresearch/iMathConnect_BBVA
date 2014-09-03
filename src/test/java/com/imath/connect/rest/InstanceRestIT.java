package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.imath.connect.model.Instance;
import com.imath.connect.model.UserConnect;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.rest.InstanceRest.InstanceDTO;;

@RunWith(Arquillian.class)
public class InstanceRestIT extends AbstractIT{
    
    @Inject InstanceController ic;
    @Inject UserConnectController ucc;
    @Inject InstanceRest irEndPoint;
    
    @Before
    public void setUp() throws Exception {}
    
    @After
    public void tearDown() throws Exception {
    }
    
    // Testing public and private retrievers 
    @Test
    public void getInstancesPublicIT() throws Exception {
        
        // 1.- Base case: No public instances exists.
        Response rest = irEndPoint.getInstances("", null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        @SuppressWarnings("unchecked")
        List<InstanceDTO> listDTO = (List<InstanceDTO>) rest.getEntity();
        assertEquals(0, listDTO.size());
        
        // 2.- We create some public instances
        String IP1 = "123.333.44.55";
        long cpu1 = 2;
        double ram1= 4.5;
        double stg1 = 56.9;
        
        String IP2 = "123.777.44.55";
        long cpu2 = 5;
        double ram2= 6.9;
        double stg2 = 776.8;
        
        Instance instance1 = ic.newInstance(cpu1, ram1, stg1, IP1, null);
        Instance instance2 = ic.newInstance(cpu2, ram2, stg2, IP2, null);
        
        rest = irEndPoint.getInstances("", null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        @SuppressWarnings("unchecked")
        List<InstanceDTO> listDTO2 = (List<InstanceDTO>) rest.getEntity();
        assertEquals(2, listDTO2.size());
        for(InstanceDTO e: listDTO2) {
            Instance inst=null;
            if (e.UUID.equals(instance1.getUUID())) {
                inst = instance1;
            } else {
                inst = instance2;
            }
            assertEquals(inst.getCpu(), e.cpu);
            assertEquals(inst.getRam(), e.ram, 0.001);
            assertEquals(inst.getStg(), e.stg, 0.001);
            assertEquals(inst.getUrl(), e.url);
        }
        
        // 3.- Now we ask for the private ones given a user
        UserConnect owner = ucc.newUserConnect("myself", "hola@pepe.com", "imath", "958183402", "958183402");
        rest = irEndPoint.getInstances(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<InstanceDTO> listDTO3 = (List<InstanceDTO>) rest.getEntity();
        assertEquals(0, listDTO3.size());
        
        // 4.- Now we add a single instance whose owner is the one just created, and another one
        String IP3 = "123.777.44.55";
        long cpu3 = 5;
        double ram3= 6.9;
        double stg3 = 776.8;
        String IP4 = "1.777.44.55";
        long cpu4 = 8;
        double ram4= 9.9;
        double stg4 = 786.8;
        
        UserConnect notOwner = ucc.newUserConnect("myselff", "hola2@pepe.com", "imath2", "358183402", "758183402");
        Instance instance3 = ic.newInstance(cpu3, ram3, stg3, IP3, owner);
        ic.newInstance(cpu4, ram4, stg4, IP4, notOwner);
        
        // 4.1 We check that still when asking for public ones, we receive only two
        rest = irEndPoint.getInstances("", null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        @SuppressWarnings("unchecked")
        List<InstanceDTO> listDTO4 = (List<InstanceDTO>) rest.getEntity();
        assertEquals(2, listDTO2.size());
        for(InstanceDTO e: listDTO4) {
            Instance inst=null;
            if (e.UUID.equals(instance1.getUUID())) {
                inst = instance1;
            } else {
                inst = instance2;
            }
            assertEquals(inst.getCpu(), e.cpu);
            assertEquals(inst.getRam(), e.ram, 0.001);
            assertEquals(inst.getStg(), e.stg, 0.001);
            assertEquals(inst.getUrl(), e.url);
        }
        
        // 4.2 We get the private instances of owner
        rest = irEndPoint.getInstances(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        @SuppressWarnings("unchecked")
        List<InstanceDTO> listDTO5 = (List<InstanceDTO>) rest.getEntity();
        assertEquals(1, listDTO5.size());
        InstanceDTO e = listDTO5.get(0);
        assertEquals(instance3.getCpu(), e.cpu);
        assertEquals(instance3.getRam(), e.ram, 0.001);
        assertEquals(instance3.getStg(), e.stg, 0.001);
        assertEquals(instance3.getUrl(), e.url);
    }

}
