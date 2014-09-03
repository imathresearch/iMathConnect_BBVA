package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javassist.bytecode.LineNumberAttribute.Pc;

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
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.rest.ProjectRest;
import com.imath.connect.rest.ProjectRest.ProjectDTO;

@RunWith(Arquillian.class)
public class ProjectRestIT extends AbstractIT{
    @Inject InstanceController ic;
    @Inject UserConnectController ucc;
    @Inject ProjectController pc;
    @Inject ProjectRest prEndPoint;
    
    @Before
    public void setUp() throws Exception {}
    
    @After
    public void tearDown() throws Exception {
    }
    
    //Happy path 
    @Test
    public void newProjectIT() throws Exception {

        String name = "myProject";
        String desc = " A very nice project";
        
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.newProject(name, desc, "no_user", "no_instance", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- We add a new UserConnect, and pass it, but instance is still not there
        UserConnect owner = ucc.newUserConnect("myself", "hola@pepe.com", "imath", "958183402", "958183402");
        rest = prEndPoint.newProject(name, desc, owner.getUUID(), "no_instance", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- We add a new Instance, then, everything should be fine
        Instance instance = ic.newInstance(0, 0, 0, "123.333.44.55", owner);
        rest = prEndPoint.newProject(name, desc, owner.getUUID(), instance.getUUID(), null);
        
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        ProjectDTO projectDTO = (ProjectDTO) rest.getEntity();
        String UUID_project = projectDTO.UUID;
        
        Project project = pc.getProject(UUID_project);
        
        assertEquals(project.getName(), projectDTO.name);
        assertEquals(project.getDescription(), projectDTO.desc);
        Date projDate = project.getCreationDate();
        Date recDate = projectDTO.creationDate;
        assertEquals(projDate.getTime(), recDate.getTime());
        
    }
}
