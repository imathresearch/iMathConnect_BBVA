package com.imath.connect.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class StandardConfigurationTest {
    StandardConfiguration test;
    String uUID = "ID";
    double ram = 223.34;
    double stg = 122.43;
    long cpu = 18;
    
    
    @Before
    public void setUp() throws Exception {
        test = new StandardConfiguration();
        test.setUUID(uUID);
        test.setRam(ram);
        test.setStg(stg);
        test.setCpu(cpu);
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
}
