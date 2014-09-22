package com.imath.connect.util;

import javax.ejb.Stateless;

import com.api.iMathCloud;      // The iMathCloud API
import com.util.AuthenticUser;

@Stateless
public class IMathCloudAccess implements IMathCloudInterface{
	
    // Sync is produced at the iMathCloud object level
    
    @Override
    public synchronized void newProject(String privateProjectName, String keyAccess, String publicProjectName, String baseURL) throws Exception {
        iMathCloud.registerUserSync(privateProjectName, keyAccess, publicProjectName, "", baseURL);
	}
	
    @Override
    public synchronized void removeProject(String privateProjectName, String key, String baseURL) throws Exception {
        AuthenticUser authUser = new AuthenticUser(privateProjectName, key);
        iMathCloud.removeUserSync(authUser, baseURL);
    }
}
