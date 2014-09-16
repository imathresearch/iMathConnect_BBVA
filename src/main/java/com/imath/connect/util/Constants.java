package com.imath.connect.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
    static public final String VERSION = "agora";
    
    // The REST url paths 
    static public final String baseURL = "/api/" + VERSION;
    
    // The EndPonts URLS of the API
    // Adds a new project
    static public final String newProject = "/newProject";
    
    // Gets the instances, both public and private (given the user)
    static public final String instances = "/instances";
    
    // Gets the own projects given the user
    static public final String ownProjects = "/getOwnProjects";
    
    // Gets the own projects given the user
    static public final String getProject = "/getProject";
    
    static public final String getProjectCredentials = "/getProjectCredentials";
    // Gets the notifications given the user
    static public final String getNotifications = "/getNotifications";
    
    // Updates the information of a project
    static public final String updateProject = "/updateProject";
    
    // gets the collaborative projects given a user
    static public final String colProjects = "/getColProjects";
    
    // get the standard configurations
    static public final String configurations = "/configurations";
    
    // gets the user information given the username
    static public final String getUserByUserName = "/getUserByUserName";
    
    // gets the user information given the uuid of the user
    static public final String getUser = "/getUser";
    
    // gets the user information given the uuid of the user
    static public final String newUser = "/newUser";
    
    // gets the users that collaborate on a project, given the project
    static public final String getColUsersByProjectUser = "/getColUsersByProject";
    
    // Adds a new collaborator to the given project
    static public final String addCollaborator = "/addCollaborator";
    
    static public final String addCollaboratorByUserNameOrEmail = "/addCollaboratorByOther";
    
    // remove a collaborator of the given project
    static public final String removeCollaborator = "/removeCollaborator";
    
    // remove a project
    static public final String removeProject = "/removeProject";
    
    // IMPORTANT: We assume that JBOSS is launched from JBOSS-HOME/bin
    static public final String ADD_USER_CLI = "./add-user.sh";
    static public final String ADD_USER_LINUX = "useradd";
    
    static public final String ROLES_FILE = "../standalone/configuration/application-roles.properties";
    static public final String USERS_FILE = "../standalone/configuration/application-users.properties";
    static public final String ROLES_DOMAIN_FILE = "../domain/configuration/application-roles.properties";
    static public final String USERS_DOMAIN_FILE = "../domain/configuration/application-users.properties";
    static public final String SYSTEM_ROLE = "WebAppUser";
    
    static public final String IMATHSYSTEMGROUP = "imathuser";      // The linux system group. All imath users will belong to this group.
    static public final String IMATH_PORT = "8080";
    static public final String IMATH_HTTP = "http://";
    static public final String WELLCOME_TEMPLATE = "welcomeTemplate.html"; // The html template for wellcome email
    static public final String RECOVER_TEMPLATE = "recoverPassTemplate.html"; // The html template for password recovery email
    
    static public String IMATH_HOST() {
        try {
            InetAddress addr = InetAddress.getLocalHost();            
            return addr.getHostAddress();
          } catch (UnknownHostException e) {
              return "127.0.0.1";
          }
    }

}
