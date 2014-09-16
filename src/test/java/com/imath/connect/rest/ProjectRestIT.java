package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.IMathCloudAccess;
import com.imath.connect.rest.ProjectRest;
import com.imath.connect.rest.ProjectRest.ProjectAdminDTO;
import com.imath.connect.rest.ProjectRest.ProjectDTO;
import com.imath.connect.rest.UserConnectRest.UserConnectDTO;

@RunWith(Arquillian.class)
public class ProjectRestIT extends AbstractIT{
    @Inject InstanceController ic;
    @Inject UserConnectController ucc;
    @Inject ProjectController pc;
    @Inject ProjectRest prEndPoint;
    
    // We mock iMathCloudAccess
    
    @Before
    public void setUp() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    //Happy path 
    @Test
    public void newProjectIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        String name = "myProject";
        String desc = " A very nice project";
        
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.newProject("no_user", name, desc, "no_instance", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- We add a new UserConnect, and pass it, but instance is still not there
        UserConnect owner = ucc.newUserConnect("myselffffrghnb", "hola@pepehhgre.com", "imath", "958183402", "958183402");
        rest = prEndPoint.newProject(owner.getUUID(), name, desc, "no_instance", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- We add a new Instance, then, everything should be fine
        Instance instance = ic.newInstance(0, 0, 0, "123.333.44.55","inst", owner);
        rest = prEndPoint.newProject(owner.getUUID(), name, desc, instance.getUUID(), null);
        
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
    public void getProjectCredentialsIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        String name = "myProject";
        String desc = " A very nice project";
        
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.getProjectCredentials("noid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Exception path: Project does not exists
        UserConnect owner = ucc.newUserConnect("myselfqqqazGGG", "GGGhola@pepehhj.com", "imath", "958183402", "958183402");
        rest = prEndPoint.getProjectCredentials(owner.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- Exception path: Project exists but petitioner is not the owner and is not a collaborator
        Instance instance = ic.newInstance(0, 0, 0, "127.0.0.1", "inst", owner);
        UserConnect owner2 = ucc.newUserConnect("myselfffGG", "hoGGla@ppellk.com", "imath", "958183402", "958183402");
        
        Project project = pc.newProject(name, desc, owner2, instance,imathcloud);
        rest = prEndPoint.getProjectCredentials(owner.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 4.- Happy path: we add the user as a collaborator
        List<String> uuids = new ArrayList<String>();
        uuids.add(owner.getUUID());
        pc.addCollaborators(project.getUUID(), uuids);
        rest = prEndPoint.getProjectCredentials(owner.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        ProjectAdminDTO pDTO = (ProjectAdminDTO) rest.getEntity();
        assertEquals(project.getUUID(), pDTO.UUID);
        assertEquals(project.getName(), pDTO.name);
        assertEquals(project.getLinuxGroup(), pDTO.linuxGroup);
        assertEquals(project.getInstance().getUrl(), pDTO.url);
        assertTrue(pDTO.key != null);
        
        // 5.- Happy path: we check that if the petitioner is the owner, the credentials are obtained
        rest = prEndPoint.getProjectCredentials(owner2.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        pDTO = (ProjectAdminDTO) rest.getEntity();
        assertEquals(project.getUUID(), pDTO.UUID);
        assertEquals(project.getName(), pDTO.name);
        assertEquals(project.getLinuxGroup(), pDTO.linuxGroup);
        assertEquals(project.getInstance().getUrl(), pDTO.url);
        assertTrue(pDTO.key != null);
        
    }
    
    @Test
    public void addCollaboratorIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        String name = "myProject";
        String desc = " A very nice project";
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.addCollaborator("noid", "nouuid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Exception path: Project does not exists
        UserConnect owner = ucc.newUserConnect("myselfqqqaz", "hola@pepehhj.com", "imath", "958183402", "958183402");
        rest = prEndPoint.addCollaborator(owner.getUUID(), "nouuid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- Exception path: Project does not belong to owner
        Instance instance = ic.newInstance(0, 0, 0, "127.0.0.1","inst", owner);
        UserConnect owner2 = ucc.newUserConnect("myselfff", "hola@ppellk.com", "imath", "958183402", "958183402");
        Project project = pc.newProject(name, desc, owner2, instance, imathcloud);
        rest = prEndPoint.addCollaborator(owner.getUUID(), project.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 4.- Exception path: Collaborator does not exists
        Project project2 = pc.newProject(name+"h", desc+"h", owner, instance, imathcloud);
        rest = prEndPoint.addCollaborator(owner.getUUID(), project2.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 5.- Happy path
        rest = prEndPoint.addCollaborator(owner.getUUID(), project2.getUUID(), owner2.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        ProjectDTO projDTO = (ProjectDTO) rest.getEntity();
        List<UserConnectDTO> list = projDTO.userCol;
        assertEquals(1,list.size());
        assertEquals(owner2.getUUID(), list.get(0).UUID);
        // Also, we check that we retrieve the same when we directly access the DB
        List<UserConnect> user = ucc.getCollaborationUsersByProject(project2.getUUID());
        assertEquals(1,user.size());
        assertEquals(owner2.getUUID(), user.get(0).getUUID());
    }
    
    @Test
    public void addCollaboratorByOtherIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        String name = "myProject";
        String desc = " A very nice project";
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.addCollaboratorByOther("noid", "nouuid", "nousermail", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Exception path: Project does not exists
        UserConnect owner = ucc.newUserConnect("myselfqQQqqaz", "holQQa@pepehhj.com", "imath", "958183402", "958183402");
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), "nouuid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- Exception path: Project does not belong to owner
        Instance instance = ic.newInstance(0, 0, 0, "127.0.0.1", "inst", owner);
        UserConnect owner2 = ucc.newUserConnect("myselfQQff", "holQQa@ppellk.com", "imath", "958183402", "958183402");
        Project project = pc.newProject(name, desc, owner2, instance, imathcloud);
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), project.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 4.- Exception path: userName does not exists
        Project project2 = pc.newProject(name+"h", desc+"h", owner, instance, imathcloud);
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), project2.getUUID(), "nouname", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 5.- Exception path: email does not exists
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), project2.getUUID(), "noname@mail.com", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 6.- Happy path: Add collaborator by userName
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), project2.getUUID(), owner2.getUserName(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        ProjectDTO projDTO = (ProjectDTO) rest.getEntity();
        List<UserConnectDTO> list = projDTO.userCol;
        assertEquals(1,list.size());
        assertEquals(owner2.getUUID(), list.get(0).UUID);
        // Also, we check that we retrieve the same when we directly access the DB
        List<UserConnect> user = ucc.getCollaborationUsersByProject(project2.getUUID());
        assertEquals(1,user.size());
        assertEquals(owner2.getUUID(), user.get(0).getUUID());
        
        // 7.- Happy path: Add collaborator by email
        UserConnect owner3 = ucc.newUserConnect("myQQQselfqqqazDDD", "mygoodmail@pepemail.com", "imath", "958183402", "958183402");
        rest = prEndPoint.addCollaboratorByOther(owner.getUUID(), project2.getUUID(), owner3.getEMail(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        projDTO = (ProjectDTO) rest.getEntity();
        list = projDTO.userCol;
        assertEquals(2,list.size());
        // Also, we check that we retrieve the same when we directly access the DB
        user = ucc.getCollaborationUsersByProject(project2.getUUID());
        assertEquals(2,user.size());
    }
    
    
    @Test
    public void removeCollaboratorIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        String name = "myProject";
        String desc = " A very nice project";
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.removeCollaborator("noid", "nouuid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Exception path: Project does not exists
        UserConnect owner = ucc.newUserConnect("zmyselfqqq", "hola@pepessskk.com", "imath", "958183402", "958183402");
        rest = prEndPoint.removeCollaborator(owner.getUUID(), "nouuid", "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- Exception path: Project does not belong to owner
        Instance instance = ic.newInstance(0, 0, 0, "127.0.0.1", "inst", owner);
        UserConnect owner2 = ucc.newUserConnect("myselfffaaa", "hola@ppeqqq.com", "imath", "958183402", "958183402");
        Project project = pc.newProject(name, desc, owner2, instance, imathcloud);
        rest = prEndPoint.removeCollaborator(owner.getUUID(), project.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 4.- OK path: If collaborator does not exists, the entity remains the same, and returns OK 
        Project project2 = pc.newProject(name+"h", desc+"h", owner, instance, imathcloud);
        rest = prEndPoint.removeCollaborator(owner.getUUID(), project2.getUUID(), "nouuid", null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        // 5.- Happy path: We add two collaborator and remove one.
        UserConnect owner3 = ucc.newUserConnect("myselfffdd", "ddhola@ppejjj.com", "imath", "958183402", "958183402");
        rest = prEndPoint.addCollaborator(owner.getUUID(), project2.getUUID(), owner2.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        rest = prEndPoint.addCollaborator(owner.getUUID(), project2.getUUID(), owner3.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        
        List<UserConnect> user = ucc.getCollaborationUsersByProject(project2.getUUID());
        assertEquals(2,user.size()); 
        // We make sure they are in the DB
        assertTrue(owner2.getUUID().equals(user.get(1).getUUID()) || owner3.getUUID().equals(user.get(1).getUUID()));
        assertTrue(owner2.getUUID().equals(user.get(1).getUUID()) || owner3.getUUID().equals(user.get(1).getUUID()));
        // here we remove it
        rest = prEndPoint.removeCollaborator(owner.getUUID(), project2.getUUID(), owner2.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        ProjectDTO projDTO = (ProjectDTO) rest.getEntity();
        List<UserConnectDTO> list = projDTO.userCol;
        assertEquals(1,list.size());
        assertEquals(owner3.getUUID(), list.get(0).UUID);
        // and we check again in the DB that everythng have been modified
        List<UserConnect> user2 = ucc.getCollaborationUsersByProject(project2.getUUID());
        assertEquals(1,user2.size());
        assertEquals(owner3.getUUID(), user2.get(0).getUUID());
    }
    
    @Test
    public void getOwnProjectsIT() throws Exception {
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
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
        
        Instance instance1 = ic.newInstance(0, 0, 0, "123.333.44.55", "inst", owner);
        Instance instance2 = ic.newInstance(0, 0, 0, "123.333.44.55", "inst", other);
        
        Project project1 = pc.newProject(name1, desc1, owner, instance1, imathcloud);
        Project project2 = pc.newProject(name2, desc2, owner, instance1, imathcloud);
        Project project3 = pc.newProject(name3, desc3, other, instance2, imathcloud);
        
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
    	Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
    	pc.setIMathCloudAccess(imathcloud);
    	prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        // 1.- Base case: User does not exists
        Response rest = prEndPoint.getColProjects("no user", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Base case: User exists and has private projects but does not collaborate in any
        UserConnect owner = ucc.newUserConnect("ipinyolll", "hola@ppeppp.com", "iath", "953333402", "933383402");
        Instance instance1 = ic.newInstance(0, 0, 0, "123.333.44.55", "inst", owner);
        Project project = pc.newProject("myprojectone", "mydesc", owner, instance1);
        rest = prEndPoint.getColProjects(owner.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<ProjectDTO> projectsDTO = (List<ProjectDTO>) rest.getEntity();
        assertEquals(0,projectsDTO.size());
        
        // 3.- We add more projects and the user owner collaborates in it
        UserConnect other = ucc.newUserConnect("ipinyolllother", "hhhb@ppeppp.com", "iath", "953333402", "933383402");
        Instance instance2 = ic.newInstance(0, 0, 0, "133.366.44.55", "inst", other);
        String name1="myprojjj";
        String desc1="mydesccc";
        String name2="gyprojjjfff";
        String desc2="gydescccfff";
        String name3="dddgyprojjjfff";
        String desc3="dddgydescccfff";
        
        Project project1 = pc.newProject(name1, desc1, other, instance2, imathcloud);
        Project project2 = pc.newProject(name2, desc2, other, instance2, imathcloud);
        Project project3 = pc.newProject(name3, desc3, other, instance2, imathcloud);
        
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
    
    @Test 
    public void removeProjectIT() throws Exception {
        Mock_IMathCloudAccess imathcloud = new Mock_IMathCloudAccess();
        pc.setIMathCloudAccess(imathcloud);
        prEndPoint.getProjectController().setIMathCloudAccess(imathcloud);
        
        // 1.- Exception path: UserConnect does not exists
        Response rest = prEndPoint.removeProject("nouuid", "nouuid proj", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 2.- Exception path: Project does not exists
        UserConnect owner = ucc.newUserConnect("myselfqQQqqazOYEAH", "holQQaMAIL@pepehhj.com", "imath", "958183402", "958183402");
        rest = prEndPoint.removeProject(owner.getUUID(), "nouuid", null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 3.- Exception path: Project does not belong to owner
        Instance instance = ic.newInstance(0, 0, 0, "127.0.0.1", "inst", owner);
        UserConnect owner2 = ucc.newUserConnect("myselfQQffOYEAH", "holQQMAILa@ppellk.com", "imath", "958183402", "958183402");
        Project project = pc.newProject("QQWWname", "QQWWEEdesc", owner2, instance, imathcloud);
        rest = prEndPoint.removeProject(owner.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 4.- Exception path: Project has collaborators
        UserConnect coll = ucc.newUserConnect("myselfColqQQqqazOYEAH", "holSQQMAILaCol@pepehhj.com", "imath", "958183402", "958183402");
        List<String> uuids = new ArrayList<String>();
        uuids.add(coll.getUUID());
        pc.addCollaborators(project.getUUID(), uuids);
        rest = prEndPoint.removeProject(owner.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), rest.getStatus());
        
        // 5.- Happy path: Everything works fine
        pc.removeCollaborator(project.getUUID(), coll.getUUID());
        rest = prEndPoint.removeProject(owner2.getUUID(), project.getUUID(), null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
    }
    
}
