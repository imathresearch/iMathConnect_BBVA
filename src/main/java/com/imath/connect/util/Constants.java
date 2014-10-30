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
    
    // update user profile
    static public final String updateProfile = "/updateProfile";

    // Gets general information of the system
    static public final String getInfo = "/getInfo";
    
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
    static public final String WELLCOME_TEMPLATE = "welcomeEmail.html"; // The html template for wellcome email
    static public final String RECOVER_TEMPLATE = "recoverPassEmail.html"; // The html template for password recovery email

    static public final String INVITATION_TEMPLATE = "projectInvitationEmail.html"; // The html template for invitation mails
    static public final String INVITATION_TEMPLATE_NEW_USER = "newUserInvitationEmail.html"; // The html template new users through invitation
    
    static public final String LOG_PREFIX_SYSTEM = "[IMATH][CONNECT]";    // The prefix of the system
    
    static public final String ADMIN_FILE_EMAIL = "/iMathCloud/adminFileEmail.txt";
    static public final String NEW_USER_ADMIN_NOTIFICATION_TEMPLATE = "adminEmail.html";
    
    static public final long EPOCH_SEC  = 1081157732;
    static public final long EPOCH_MIL = EPOCH_SEC * 1000; 
    
    static public final String GOOGLE_ACCOUNT="google";
    static public final String GITHUB_ACCOUNT="github";
    static public final String LINKEDIN_ACCOUNT="linkedin";
        
    static public final String CLIENTID_GOOGLE_DEVELOPMENT = "249154387346-7gbn63lqnvu5t36pjgvdt3pol6cpsrrn.apps.googleusercontent.com";
    static public final String CLIENTSECRET_GOOGLE_DEVELOPMENT = "WrxDPHUttLa89PuzNtVFnEEA";
    static public final String CLIENTID_GOOGLE_PRODUCTION = "249154387346-ss5gal7drl94lae8no254qeph8l0nbdl.apps.googleusercontent.com";
    static public final String CLIENTSECRET_GOOGLE_PRODUCTION = "V6unIux0QBhFVIrc2WiBkBqk";
    
    static public final String CLIENTID_GOOGLE = CLIENTID_GOOGLE_DEVELOPMENT; // It must be changed in production
    static public final String CLIENTSECRET_GOOGLE = CLIENTSECRET_GOOGLE_DEVELOPMENT; // It must be change in production
        
    static public final String CLIENTID_LINKEDIN_DEVELOPMENT = "756jsh07iqy4ej";
    static public final String CLIENTSECRET_LINKEDIN_DEVELOPMENT = "T8sITuOxF6XnesqU";
    static public final String CLIENTID_LINKEDIN_PRODUCTION = "75y40x21uoekuj";
    static public final String CLIENTSECRET_LINKEDIN_PRODUCTION = "Fux7NpqdwYUDUCTm";
    
    static public final String CLIENTID_LINKEDIN = CLIENTID_LINKEDIN_DEVELOPMENT; // It must be changed in production
    static public final String CLIENTSECRET_LINKEDIN = CLIENTSECRET_LINKEDIN_DEVELOPMENT; // It must be change in production
    
    static public final String CLIENTID_GITHUB_DEVELOPMENT = "1a219ace0063d4c2358f";
    static public final String CLIENTSECRET_GITHUB_DEVELOPMENT = "90e3c2ef1d39f584250f17ae8e5c7df2e3f27ef5";
    static public final String CLIENTID_GITHUB_PRODUCTION = "690cba2489d8c0285e43";
    static public final String CLIENTSECRET_GITHUB_PRODUCTION = "25e83fd44b5950d758c9375b77fdbd58875db658";
    
    static public final String CLIENTID_GITHUB = CLIENTID_GITHUB_DEVELOPMENT; // It must be changed in production
    static public final String CLIENTSECRET_GITHUB = CLIENTSECRET_GITHUB_DEVELOPMENT; // It must be change in production
    
    
    static public String IMATH_HOST() {
        return "127.0.0.1"; // To be changed in production!!!!!!!
    	

        /*
        try {
            InetAddress addr = InetAddress.getLocalHost();            
            return addr.getHostAddress();
          } catch (UnknownHostException e) {
              return "127.0.0.1";
          }
        }*/
    }
}
