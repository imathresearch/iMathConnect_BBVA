package com.imath.connect.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstanceTest {
    Instance test;
    String uUID = "ID";
    double ram = 223.34;
    double stg = 122.43;
    long cpu = 18;
    String url ="123.567.8.9:8080";
    String name = "my instance";
    UserConnect user = new UserConnect();
    
    @Before
    public void setUp() throws Exception {
        test = new Instance();
        test.setUUID(uUID);
        test.setRam(ram);
        test.setStg(stg);
        test.setCpu(cpu);
        test.setOwner(user);
        test.setUrl(url);
        test.setName(name);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetUUID() {
        assertEquals(uUID,test.getUUID());
    }
    
    @Test
    public void testGetRam() {
        assertEquals(ram,test.getRam(),0.0001);
    }
    
    @Test
    public void testGetStg() {
        assertEquals(stg,test.getStg(),0.0001);
    }
    
    @Test
    public void testGetCpu() {
        assertEquals(cpu,test.getCpu(),0.0001);
    }
    
    @Test
    public void testGetUrl() {
        assertEquals(url,test.getUrl());
    }
    
    @Test
    public void testGetOwner() {
        assertEquals(user,test.getOwner());
    }
    
    @Test
    public void testGetName() {
        assertEquals(name, test.getName());
    }
}
