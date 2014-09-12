package com.imath.connect.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers;

import com.imath.connect.data.MainDB;
import com.imath.connect.data.ProjectDB;
import com.imath.connect.data.UserConnectDB;
import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.util.IMathCloudAccess;

public class ProjectControllerTest {
    private ProjectController pc;
    private MainDB db;
    
    @Mock 
    UserConnectController ucc;
    
    @Mock
    private Logger LOG;
    
    @Mock
    private EntityManager em;
    
    @Mock 
    private UserConnectDB userConnectDB;
    
    @Mock
    private ProjectDB projectDB;
    
    @Mock 
    private IMathCloudAccess imathcloud;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        db = new MainDB();
        db.setEntityManager(em);
        db.setUserConnectDB(userConnectDB);
        db.setProjectDB(projectDB);
        
        pc = new ProjectController();
        pc.setUserConnectController(ucc);
        pc.setIMathCloudAccess(imathcloud);
        pc.setMainDB(db);
        pc.setLog(LOG);
    }
    
    @Test
    public void newProjectTest() throws Exception {
        String name = "project";
        String desc = "the project";
        String userName = "owner";
        UserConnect owner = new UserConnect();
        owner.setUserName(userName);
        Instance instance = new Instance();
        
        Project project = pc.newProject(name, desc, owner, instance);
        
        assertEquals(name, project.getName());
        assertEquals(desc, project.getDescription());
        assertEquals(owner, project.getOwner());
        assertEquals(instance, project.getInstance());
        assertEquals(name+"_"+userName, project.getLinuxGroup());
        verify(em).persist((Project)Matchers.anyObject());
    }
    
    // Happy Path plus basic cases
    @Test
    public void addCollaboratorsTest() throws Exception {
        String UUID_project = "id_project";
        String UUID_user1 = "iduser1";
        String UUID_user2 = "iduser2";
        String UUID_user3 = "iduser3";
        
        Project project = new Project();
        project.setUUID(UUID_project);
        
        when(db.getProjectDB().findById(UUID_project)).thenReturn(project);
        
        // 1.- Case base1: We give null stuff
        Project ret = pc.addCollaborators(UUID_project, null);
        assertNull(ret.getCollaborators());
        verify(em,times(0)).persist(project);
        
        // 2.- Case base: We give an empty list. It behave as null list 
        ret = pc.addCollaborators(UUID_project, new ArrayList<String>());
        assertNull(ret.getCollaborators());
        verify(em,times(0)).persist(project);
        
        // 3.- Normal case: We add two collaborators when the collaborators list is still null
        UserConnect user1 = createUserConnect(UUID_user1);
        UserConnect user2 = createUserConnect(UUID_user2);
        when(ucc.getUserConnect(UUID_user1)).thenReturn(user1);
        when(ucc.getUserConnect(UUID_user2)).thenReturn(user2);
        List<String> uuids = new ArrayList<String>();
        uuids.add(UUID_user1);
        uuids.add(UUID_user2);
        ret = pc.addCollaborators(UUID_project, uuids);
        assertNotNull(ret.getCollaborators());
        Set<UserConnect> dev = ret.getCollaborators();
        assertEquals(2,dev.size());
        for(UserConnect e:dev) {
            assertTrue(e == user1 || e== user2);    // Yes, object comparison
        }
        verify(em).persist(project);
        
        // 4.- Normal case: Passing again null or empty list does not modify anything
        ret = pc.addCollaborators(UUID_project, null);
        assertNotNull(ret.getCollaborators());
        verify(em,times(1)).persist(project);
        assertEquals(2,ret.getCollaborators().size());
        ret = pc.addCollaborators(UUID_project, new ArrayList<String>());
        assertNotNull(ret.getCollaborators());
        verify(em,times(1)).persist(project);
        
        // 5.- Normal case: Adding one extra collaborator
        UserConnect user3 = createUserConnect(UUID_user3);
        when(ucc.getUserConnect(UUID_user3)).thenReturn(user3);
        List<String> uuids2 = new ArrayList<String>();
        uuids2.add(UUID_user3);
        ret = pc.addCollaborators(UUID_project, uuids2);
        assertNotNull(ret.getCollaborators());
        dev = ret.getCollaborators();
        assertEquals(3,dev.size());
        for(UserConnect e:dev) {
            assertTrue(e == user1 || e== user2 || e == user3);    // Yes, object comparison
        }
        verify(em, times(2)).persist(project);
    }
    
    @Test
    public void removeCollaboratorTest() throws Exception {
        String UUID_project = "id_project";
        String UUID_user1 = "iduser1";
        String UUID_user2 = "iduser2";
        
        Project project = new Project();
        //Project project = pc.newProject(name, desc, owner, instance);
        project.setUUID(UUID_project);
        
        when(db.getProjectDB().findById(UUID_project)).thenReturn(project);
        // 1.- Case base: no collaborators, null string
        Project ret = pc.removeCollaborator(UUID_project, null);
        assertNull(ret.getCollaborators());
        verify(em,times(0)).persist(project);
        
        // 2.- Case base: no collaborators, empty string
        ret = pc.removeCollaborator(UUID_project, "");
        assertNull(ret.getCollaborators());
        verify(em,times(0)).persist(project);
        
        // 3.- Case base: no collaborators, non-empty string
        UserConnect user1 = createUserConnect(UUID_user1);
        when(ucc.getUserConnect(UUID_user1)).thenReturn(user1);
        ret = pc.removeCollaborator(UUID_project, UUID_user1);
        assertNull(ret.getCollaborators());
        verify(em,times(0)).persist(project);
       
        // 4.- Normal case: some collaborators but ID is not present
        UserConnect user2 = createUserConnect(UUID_user2);
        when(ucc.getUserConnect(UUID_user2)).thenReturn(user2);
        List<String> cols = new ArrayList<String>();
        cols.add(UUID_user1);
        cols.add(UUID_user2);
        pc.addCollaborators(UUID_project, cols);
        verify(em).persist(project);
        
        ret = pc.removeCollaborator(UUID_project, "NO ID");
        assertEquals(2, ret.getCollaborators().size());
        verify(em,times(1)).persist(project);
        
        // 5.- Normal case: we remove one id
        ret = pc.removeCollaborator(UUID_project, UUID_user1);
        assertEquals(1, ret.getCollaborators().size());
        verify(em, times(2)).persist(project);
        for(UserConnect e:ret.getCollaborators()) {
            assertTrue(e == user2);
        }
        
        // 6.- Normal case: we remove the other one
        ret = pc.removeCollaborator(UUID_project, UUID_user2);
        assertEquals(0, ret.getCollaborators().size());
        verify(em,times(3)).persist(project);
        
    }
    
    private UserConnect createUserConnect(String UUID) {
        UserConnect peer = new UserConnect();
        peer.setUUID(UUID);
        return peer;
    }

}
