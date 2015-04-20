package com.imath.connect.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	
	// iMath Port and iMath Host
    static public String IMATH_PORT = 								"imath.port";
    static public String IMATH_HOST = 								"imath.host";
    
    static public String TEST = 									"test.profile";
    
    static public String CLIENTID_GOOGLE = 						"client.id.google"; // It must be changed in production
    static public String CLIENTSECRET_GOOGLE = 					"client.secret.google"; // It must be change in production
    
    static public String CLIENTID_LINKEDIN =  						"client.id.linkedin"; // It must be changed in production
    static public String CLIENTSECRET_LINKEDIN = 					"client.secret.linkedin"; // It must be change in production
    
    static public String CLIENTID_GITHUB = 						"client.id.github"; // It must be changed in production
    static public String CLIENTSECRET_GITHUB = 					"client.secret.github"; // It must be change in production
    
    public static String CONFIG_PROPERTIES_FILE = 					"config.properties";
    
    private static PropertyParser prop = new PropertyParser();
    
    /**
     * Upload the configuration from config.properties files
     */
    private static void uploadConfiguration() throws IOException {
        InputStream input = null;

        try {
            String filename = CONFIG_PROPERTIES_FILE;
            input = AppConfig.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                throw new IOException("No " + filename + " has found!");
            }
            prop.load(input);

        } catch (IOException ex) {
            throw new IOException("Properties file error", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new IOException("Error closing properties file", e);
                }
            }
        }
    }
    
    public static String getProp(String key) throws IOException {
        if (prop.isEmpty()) {
            uploadConfiguration();
        }
        return prop.getProperty(key);
    }

}
