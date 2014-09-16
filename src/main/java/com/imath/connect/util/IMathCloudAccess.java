package com.imath.connect.util;

import javax.ejb.Stateless;

import com.api.iMathCloud;      // The iMathCloud API

@Stateless
public class IMathCloudAccess implements IMathCloudInterface{
	
	// Important to be synchronized!!!
	// Since iMathCloud.setBaseURL is static, only one instance of such base is possible and thus,
	// if two threads access the method, some conflicts may appear.
    @Override
    public synchronized void newProject(String privateProjectName, String keyAccess, String publicProjectName, String baseURL) throws Exception {
		iMathCloud.setBaseURL(baseURL);
		iMathCloud.registerUser(privateProjectName, keyAccess, publicProjectName, "");
		iMathCloud.setBaseURL(null);
	}
	
    @Override
    public synchronized void removeProject(String privateProjectName, String baseURL) throws Exception {
	    iMathCloud.setBaseURL(baseURL);
	    // Not implemented yet
	    iMathCloud.setBaseURL(null);
	}
}
