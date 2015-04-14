package com.imath.connect.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.security.MessageDigest;
import java.util.Properties;

import javax.inject.Inject;

import com.imath.connect.service.UserJBossController;
import com.imath.connect.service.UserJBossRolesController;

/**
 * Implements a set of utilities to create users in the jboss 
 * @author iMath
 *
 */

public class Security {
    
    /**
     * Change or set parameters user=password in JBOSS authentication files.
     * The generic format for these key - values is:
     * <userName>=hex(md5(<password>))
     * Which value password contains the string -> userName:ApplicationRealm:password
     * The role is static: WebAppUser
     * @param userName
     * @param password
     * @throws Exception
     */
    
    private static Object lock = new Object();
    
    public static void updateSystemPassword(String userName, String password) throws Exception {
        synchronized(lock) {
            String hexPass = generateHexMd5Password(userName, password);
            
            // Here the pass in hex(md5) is set as property
            updateProperty(userName, hexPass.toString(), Constants.USERS_FILE);
            updateProperty(userName, hexPass.toString(), Constants.USERS_DOMAIN_FILE);
            
            // Here the role "WebAppUser" is set as property
            updateProperty(userName, "WebAppUser", Constants.ROLES_FILE);
            updateProperty(userName, "WebAppUser", Constants.ROLES_DOMAIN_FILE);
        }

    }
    
    /**
     * Generate the properties that jboss uses to store passwords in its properties files.
     * The generic format for these key - values is:
     * <userName>=hex(md5(<password>))
     * Which value password contains the string -> userName:ApplicationRealm:password
     * @param userName
     * @param password
     * @return the hex(md5) generated from the password
     * @throws Exception
     */
    public static String generateHexMd5Password(String userName, String password) throws Exception {
       
        // To generate md5 of password stored in JBOSS' files
        MessageDigest md = MessageDigest.getInstance("MD5");
        String concatPass = userName + ":ApplicationRealm:" + password;
        byte[] md5Pass = md.digest(concatPass.getBytes());
        
        //convert the md5 byte to hex format method 1
        StringBuffer hexPass = new StringBuffer();
        for (int i=0;i<md5Pass.length;i++) {
            String hex=Integer.toHexString(0xff & md5Pass[i]);
            if(hex.length()==1) hexPass.append('0');
            hexPass.append(hex);
        }
        
        return hexPass.toString();
    }
    
    public static String encryptHexMd5Password(String password) throws Exception {
        
        // To generate md5 of password stored in JBOSS' files
        MessageDigest md = MessageDigest.getInstance("MD5");
        String concatPass = password;
        byte[] md5Pass = md.digest(concatPass.getBytes());
        
        //convert the md5 byte to hex format method 1
        StringBuffer hexPass = new StringBuffer();
        for (int i=0;i<md5Pass.length;i++) {
            String hex=Integer.toHexString(0xff & md5Pass[i]);
            if(hex.length()==1) hexPass.append('0');
            hexPass.append(hex);
        }
        
        return hexPass.toString();
    }
    
    
    
    private static void updateProperty(String key, String value, String propertiesPath) {
        try {
            // Get property of userName in JBOSS' file
            FileReader reader = new FileReader(propertiesPath);
            Properties props = new Properties();
            props.load(reader);
            props.setProperty(key, value);
            
            // We store the updated value.
            File f = new File(propertiesPath);
            OutputStream out = new FileOutputStream( f );
            props.store(out, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void createLinuxUser(String userName) throws Exception {
        synchronized(lock) {
            ProcessBuilder pb = new ProcessBuilder(Constants.ADD_USER_LINUX, "-d",  "/home/" + userName, "-m", userName, "-g", Constants.IMATHSYSTEMGROUP);
            pb.redirectInput(Redirect.INHERIT);
            pb.redirectOutput(Redirect.INHERIT);
            pb.redirectError(Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor();
        }
    }
    
    public static void createSystemUser(String userName, String password, String role) throws Exception {
        // We add the system user
        //Process p = Runtime.getRuntime().exec(Constants.ADD_USER_CLI + " -a " + userName + " " + password + " > /dev/tty");
        synchronized(lock) {
            /*ProcessBuilder pb = new ProcessBuilder(Constants.ADD_USER_CLI, "-a",  userName, password);
    
            pb.redirectInput(Redirect.INHERIT);
            pb.redirectOutput(Redirect.INHERIT);
            pb.redirectError(Redirect.INHERIT);
    
            Process p = pb.start();
            int signal = p.waitFor();
            
            System.out.println("SIGNAL " + signal);
            
            // We add the role of the user if role is not null
            if (role != null) {
                String line = userName + "=" + role;
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constants.ROLES_FILE, true), "UTF-8"));
                writer.append(line + "\n");
                writer.close();
            }*/
        	
        	
        	String hexPass = generateHexMd5Password(userName, password);
        	
        	
            // Here the pass in hex(md5) is set as property
            updateProperty(userName, hexPass.toString(), Constants.USERS_FILE);
            updateProperty(userName, hexPass.toString(), Constants.USERS_DOMAIN_FILE);
            
            // We add the role of the user if role is not null
            if (role != null) {
                String line = userName + "=" + role;
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constants.ROLES_FILE, true), "UTF-8"));
                writer.append(line + "\n");
                writer.close();
            }
            
        	
        	
        }
    }
}
