package com.imath.connect.util;

import javax.ejb.Stateful;
import javax.ejb.Stateless;

import com.api.iMathCloud;

@Stateless
public class IMathCloudAccess implements IMathCloudInterface{
	
	// Important to be synchronized!!!
	// Since iMathCloud.setBaseURL is static, only one instance of such base is possible and thus,
	// if two threads access the method, some conflicts may appear.
	public synchronized void newProject(String privateProjectName, String keyAccess, String publicProjectName, String baseURL) throws Exception {
		iMathCloud.setBaseURL(baseURL);
		iMathCloud.registerUser(privateProjectName, keyAccess, publicProjectName, "");
		iMathCloud.setBaseURL(null);
	}
}
