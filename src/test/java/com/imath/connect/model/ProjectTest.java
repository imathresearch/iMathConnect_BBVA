package com.imath.connect.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {
    Project test;
    String uUID = "ID";
    UserConnect owner = new UserConnect();
    Date creationDate = new Date();
    String description = "New project";
    Instance instance = new Instance();
    String key = "wwwlllddd";
    String name = "MyProject";
    Set<UserConnect> collaborators = new HashSet<UserConnect>();
    String group = "mygroup";
    
    
    @Before
    public void setUp() throws Exception {
        test = new Project();
        test.setUUID(uUID);
        test.setOwner(owner);
        test.setCreationDate(creationDate);
        test.setDescription(description);
        test.setInstance(instance);
        test.setKey(key);
        test.setName(name);
        collaborators.add(owner);
        test.setCollaborators(collaborators);
        test.setLinuxGroup(group);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetUUID() {
        assertEquals(uUID,test.getUUID());
    }
    
    @Test
    public void testGetOwner() {
        assertEquals(owner,test.getOwner());
    }
    
    @Test
    public void testGetCreationDate() {
        assertEquals(creationDate,test.getCreationDate());
    }
    
    @Test
    public void testGetDescription() {
        assertEquals(description,test.getDescription());
    }
    
    @Test
    public void testGetInstance() {
        assertEquals(instance,test.getInstance());
    }
    
    @Test
    public void testGetKey() {
        assertEquals(key,test.getKey());
    }
    
    @Test
    public void testGetName() {
        assertEquals(name,test.getName());
    }
    
    @Test
    public void testGetCollaborators() {
        assertEquals(collaborators,test.getCollaborators());
        Set<UserConnect> col = test.getCollaborators();
        assertTrue(col.contains(owner));
        assertEquals(1, col.size());
    }
    
    @Test
    public void testGetGroup() {
        assertEquals(group, test.getLinuxGroup());
    }
}

