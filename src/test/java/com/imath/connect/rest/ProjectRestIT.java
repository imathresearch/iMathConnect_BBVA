package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
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
    
    @Test
    public void getOwnProjectsIT() throws Exception {
        // 1.- Base case: User does not exists
        Response rest = prEndPoint.getOwnProjects("no user", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Base case: User exists but no projects yet
        UserConnect owner = ucc.newUserConnect("ipinyol", "hola@ppe.com", "iath", "953333402", "933383402");
        rest = prEndPoint.getOwnProjects(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO = (List<ProjectDTO>) rest.getEntity();
        assertEquals(0,projectsDTO.size());
        
        // 3.- Normal case: We add an instance and two projects for the user and another project for another user
        String name1="myproj";
        String desc1="mydesc";
        String name2="gyprojjj";
        String desc2="gydesccc";
        String name3="otherproj";
        String desc3="descotherproj";
        UserConnect other = ucc.newUserConnect("ramones", "ho@ppe.com", "rriath", "933333402", "944383402");
        
        Instance instance1 = ic.newInstance(0, 0, 0, "123.333.44.55", owner);
        Instance instance2 = ic.newInstance(0, 0, 0, "123.333.44.55", other);
        
        Project project1 = pc.newProject(name1, desc1, owner, instance1);
        Project project2 = pc.newProject(name2, desc2, owner, instance1);
        Project project3 = pc.newProject(name3, desc3, other, instance2);
        
        rest = prEndPoint.getOwnProjects(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO2 = (List<ProjectDTO>) rest.getEntity();
        assertEquals(2, projectsDTO2.size());
        for(ProjectDTO pDTO: projectsDTO2) {
            Project p = null;
            if (pDTO.UUID.equals(project1.getUUID())) {
                p = project1;
            } else if (pDTO.UUID.equals(project2.getUUID())) {
                p = project2;
            }
            assertEquals(p.getDescription(), pDTO.desc);
            assertEquals(p.getName(), pDTO.name);
            assertEquals(p.getCreationDate().getTime(), pDTO.creationDate.getTime());
        }
        
        // 3.1- We retrieve the other project to make sure everything works
        rest = prEndPoint.getOwnProjects(other.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO3 = (List<ProjectDTO>) rest.getEntity();
        assertEquals(1, projectsDTO3.size());
        for(ProjectDTO pDTO: projectsDTO3) {
            Project p = project3;
            assertEquals(p.getDescription(), pDTO.desc);
            assertEquals(p.getName(), pDTO.name);
            assertEquals(p.getCreationDate().getTime(), pDTO.creationDate.getTime());
        }
    }
    
    @Test
    public void getColProjectsIT() throws Exception {
        // 1.- Base case: User does not exists
        Response rest = prEndPoint.getColProjects("no user", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Base case: User exists and has private projects but does not collaborate in any
        UserConnect owner = ucc.newUserConnect("ipinyolll", "hola@ppeppp.com", "iath", "953333402", "933383402");
        Instance instance1 = ic.newInstance(0, 0, 0, "123.333.44.55", owner);
        Project project = pc.newProject("myprojectone", "mydesc", owner, instance1);
        rest = prEndPoint.getColProjects(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO = (List<ProjectDTO>) rest.getEntity();
        assertEquals(0,projectsDTO.size());
        
        // 3.- We add more projects and the user owner collaborates in it
        UserConnect other = ucc.newUserConnect("ipinyolllother", "hhhb@ppeppp.com", "iath", "953333402", "933383402");
        Instance instance2 = ic.newInstance(0, 0, 0, "133.366.44.55", other);
        String name1="myprojjj";
        String desc1="mydesccc";
        String name2="gyprojjjfff";
        String desc2="gydescccfff";
        String name3="dddgyprojjjfff";
        String desc3="dddgydescccfff";
        
        Project project1 = pc.newProject(name1, desc1, other, instance2);
        Project project2 = pc.newProject(name2, desc2, other, instance2);
        Project project3 = pc.newProject(name3, desc3, other, instance2);
        
        List<String> usersIds = new ArrayList<String>();
        usersIds.add(owner.getUUID());
        pc.addCollaborators(project1.getUUID(), usersIds);
        pc.addCollaborators(project3.getUUID(), usersIds);
        
        rest = prEndPoint.getColProjects(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO2 = (List<ProjectDTO>) rest.getEntity();
        assertEquals(2,projectsDTO2.size());
        for(ProjectDTO pDTO: projectsDTO2) {
            Project p = null;
            if (pDTO.UUID.equals(project1.getUUID())) {
                p = project1;
            } else if (pDTO.UUID.equals(project3.getUUID())) {
                p = project3;
            }
            assertEquals(p.getDescription(), pDTO.desc);
            assertEquals(p.getName(), pDTO.name);
            assertEquals(p.getCreationDate().getTime(), pDTO.creationDate.getTime());
        }
    }
}
