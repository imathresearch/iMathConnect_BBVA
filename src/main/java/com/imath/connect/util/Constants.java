package com.imath.connect.util;

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
    static public final String ownProjects = "/ownProjects";
    
    // gets the collaborative projects given a user
    static public final String colProjects = "/colProjects";
    
    // get the standard configurations
    static public final String configurations = "/configurations";
    
    // gets the user information given the username
    static public final String getUserByUserName = "/getUserByUserName";
    
    // gets the user information given the uuid of the user
    static public final String getUser = "/getUser";
    
    // gets the users that collaborate on a project, given the project
    static public final String getColUsersByProjectUser = "/getColUsersByProject";
    
}
