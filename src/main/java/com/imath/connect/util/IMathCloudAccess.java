package com.imath.connect.util;

import javax.ejb.Stateless;

import com.api.iMathCloud;      // The iMathCloud API
import com.util.AuthenticUser;

@Stateless
public class IMathCloudAccess implements IMathCloudInterface{
	
	// Important to be synchronized!!!
	// Since iMathCloud.setBaseURL is static, only one instance of such base is possible and thus,
	// if two threads access the method, some conflicts may appear.
    
    private Object lock = new Object();     // A mutex to sync
    
    @Override
    public synchronized void newProject(String privateProjectName, String keyAccess, String publicProjectName, String baseURL) throws Exception {
        
        synchronized(lock) {
            iMathCloud.setBaseURL(baseURL);
            iMathCloud.registerUser(privateProjectName, keyAccess, publicProjectName, "");
            iMathCloud.setBaseURL(null);
        }
	}
	
    @Override
    public synchronized void removeProject(String privateProjectName, String key, String baseURL) throws Exception {
        synchronized(lock) {
            iMathCloud.setBaseURL(baseURL);
            AuthenticUser authUser = new AuthenticUser(privateProjectName, key);
            iMathCloud.removeUser(authUser);
            iMathCloud.setBaseURL(null);
        }
	}
    
    
}
