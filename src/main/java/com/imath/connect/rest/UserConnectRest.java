package com.imath.connect.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.security.SecurityManager;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;
import com.imath.connect.util.Photo;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class UserConnectRest {
    @Inject private Logger LOG;
    @Inject private UserConnectController ucc;
    @Inject private ProjectController pc;
    private static String LOG_PRE = Constants.LOG_PREFIX_SYSTEM + "[UserConnectRest]";
    
    @GET
    @Path(Constants.getUserByUserName + "/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUserName(@PathParam("userName") String userName, @Context SecurityContext sc) {
        LOG.info(LOG_PRE + "[" + Constants.getUserByUserName + "]" + userName);
        try {
            UserConnect user = ucc.getUserConnectByUserName(userName);
            SecurityManager.secureBasic(user.getUserName(), sc);
            UserConnectDTO retDTO = new UserConnectDTO();
            retDTO.convert(user);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.getUser + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("uuid_user") String uuid, @Context SecurityContext sc) {
        LOG.info(LOG_PRE + "[" + Constants.getUser + "]" + uuid);
        try {
            UserConnect user = ucc.getUserConnect(uuid);
            SecurityManager.secureBasic(user.getUserName(), sc);
            UserConnectDTO retDTO = new UserConnectDTO();
            retDTO.convert(user);
            return Response.status(Response.Status.OK).entity(retDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path(Constants.getColUsersByProjectUser + "/{uuid_project}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColUsersByProject(@PathParam("uuid_project") String uuid_project, @Context SecurityContext sc) {
        LOG.info(LOG_PRE + "[" + Constants.getColUsersByProjectUser + "]" + uuid_project);
        try {
            String userName = null;
            UserConnect owner = null;
            if (sc!=null) {
                if (sc.getUserPrincipal()!=null) {
                    userName = sc.getUserPrincipal().getName();
                    owner = ucc.getUserConnectByUserName(userName);
                }
            }
            Project project = pc.getProject(uuid_project);
            List<UserConnect> users = ucc.getCollaborationUsersByProject(uuid_project);
            //Here we check that the current user is really a collaborator. If not, we issue an exception
            Iterator<UserConnect> it = users.iterator();
            boolean found = false;
            while(it.hasNext() && !found && (owner!=null)) {
                UserConnect user = it.next();
                found = (user.getUUID().equals(owner.getUUID()));
            }
            if (!found) {
                List<UserConnectDTO> usersDTO = this.convertToDTO(users);
                return Response.status(Response.Status.OK).entity(usersDTO).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    public List<UserConnectDTO> convertToDTO(List<UserConnect> users) {
        List<UserConnectDTO> usersDTO = new ArrayList<UserConnectDTO>();
        if (users!=null) {
            for(UserConnect user: users) {
                UserConnectDTO userDTO = new UserConnectDTO();
                userDTO.convert(user);
                usersDTO.add(userDTO);            
            }
        }
        return usersDTO;
    }
    
    public UserConnectDTO convertToDTO(UserConnect users) {
        UserConnectDTO usersDTO = new UserConnectDTO();
        if (users!=null) {
            UserConnectDTO userDTO = new UserConnectDTO();
            userDTO.convert(users);
        }
        return usersDTO;
    }
    
    /**
     * Permits to modify user's profile
     * @throws Exception 
     */

    @POST
    @Path(Constants.updateProfile + "/{uuid_user}")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadUsersProfile(MultipartFormDataInput input, @Context SecurityContext sc) throws Exception {
        //TODO: warning when a filename exists. Add some flag to notify about overwriting files etc... 
        LOG.info(LOG_PRE + "[" + Constants.updateProfile + "]");
        UserConnectDTO usersDTO = new UserConnectDTO();

        try {
            
            String nameUser = sc.getUserPrincipal().getName();
            UserConnect userConnect = ucc.getUserConnectByUserName(nameUser);          
            
            //usersDTO = this.convertToDTO(userConnect);

            Photo photo = new Photo();
            String fileName = "";
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            List<InputPart> inputParts = uploadForm.get("uploadedFile");
            
            for (InputPart inputPart : inputParts) {

             try {
                
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);
                byte [] bytes = photo.getPhotoByte(inputStream);
                
                ucc.updateUserConnectByte(userConnect.getUUID(), bytes);
                userConnect = ucc.getUserConnectByUserName(nameUser);
                usersDTO.convert(userConnect);
                //usersDTO = this.convertToDTO(userConnect);                
                return Response.status(Response.Status.OK).entity(usersDTO).build();

              }
             
              catch (IOException e) {

                  LOG.info("Error updating the photograph");
                  return Response.status(Response.Status.BAD_REQUEST).build();

              }
            
            }

        }
        catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();

    }
    
    public static class UserConnectDTO {
        public String UUID;
        public String userName;
        public String eMail;
        public String phone1;
        public String phone2;
        public String organization;
        public Date creationDate;
        public Date lastConnection;
        public byte[] photo;
        
        public void convert(UserConnect user) {
            this.UUID = user.getUUID();
            this.userName = user.getUserName();
            this.eMail = user.getEMail();
            this.phone1 = user.getPhone1();
            this.phone2 = user.getPhone2();
            this.creationDate = user.getCreationDate();
            this.lastConnection = user.getLastConnection();
            this.organization = user.getOrganization();
            this.photo = user.getPhoto();
        }
    }
    
    /**
     * header sample
     * {
     *  Content-Type=[image/png], 
     *  Content-Disposition=[form-data; name="file"; filename="filename.extension"]
     * }
     **/
    //get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {
 
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
 
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
 
                String[] name = filename.split("=");
 
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }
 
    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {
 
        File file = new File(filename);
 
        if (!file.exists()) {
            file.createNewFile();
        }
 
        FileOutputStream fop = new FileOutputStream(file);
 
        fop.write(content);
        fop.flush();
        fop.close();
 
    }
    
}
