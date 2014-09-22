package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.rest.GeneralRest.InfoDTO;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;

@RunWith(Arquillian.class)
public class GeneralRestIT extends AbstractIT {
    @Inject InstanceController ic;
    @Inject UserConnectController ucc;
    @Inject ProjectController pc;
    @Inject GeneralRest grEndPoint;
    Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();

    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void getInfoIT() throws Exception {
        // 1.- Exception path: UserConnect does not exists
        Response rest = grEndPoint.getInfo("no user", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Case base: User exists, but nothing else. So, everything is at 0 except number of users which is 1
        UserConnect owner = ucc.newUserConnect("myselffffrghnb", "hola@pepehhgre.com", "imath", "958183402", "958183402");
        rest = grEndPoint.getInfo(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        InfoDTO infoDTO = (InfoDTO) rest.getEntity();
        assertEquals(1, infoDTO.numUsers);
        assertEquals(0, infoDTO.numProjects);
        assertEquals(0, infoDTO.numInstances);
        assertEquals(0, infoDTO.numUsersCol);
        
        // 3.- Normal case: we add more users, projects, instances and collaborators
        // In total: 3 users, 2 projects, 4 instances, 3 collaborators
        UserConnect owner2 = ucc.newUserConnect("mysAghnb", "ha@pepehsshgre.com", "imath", "958183402", "958183402");
        UserConnect owner3 = ucc.newUserConnect("mysghndsb", "ha@pepqwehhgre.com", "imath", "958183402", "958183402");
        Instance instance = ic.newInstance(0, 0, 0, "133.333.44.55","inst", owner);
        Instance instance2 = ic.newInstance(0, 0, 0, "143.333.44.55","inst", owner);
        Instance instance3 = ic.newInstance(0, 0, 0, "153.333.44.55","inst", owner);
        Instance instance4 = ic.newInstance(0, 0, 0, "163.333.44.55","inst", owner);
        Project project = pc.newProject("p", "d", owner, instance,imathcloud);
        Project project2 = pc.newProject("pp", "dd", owner2, instance3,imathcloud);
        
        List<String> uuids = new ArrayList<String>();
        uuids.add(owner2.getUUID());
        uuids.add(owner3.getUUID());
        pc.addCollaborators(project.getUUID(), uuids);
        
        uuids = new ArrayList<String>();
        uuids.add(owner.getUUID());
        pc.addCollaborators(project2.getUUID(), uuids);
        
        rest = grEndPoint.getInfo(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        infoDTO = (InfoDTO) rest.getEntity();
        assertEquals(3, infoDTO.numUsers);
        assertEquals(2, infoDTO.numProjects);
        assertEquals(4, infoDTO.numInstances);
        assertEquals(3, infoDTO.numUsersCol);
        
    }
}
