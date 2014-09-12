package com.imath.connect.util;

import javax.ejb.Stateful;
import javax.ejb.Stateless;

import com.api.iMathCloud;

@Stateless
public class IMathCloudAccess implements IMathCloudInterface{
	
	// Important to be synchronized!!!
	// Since iMathCloud.setBaseURL is static, only one instance of such base is possible and thus,
	// if two threads access the method, some conflicts may appear.
	public synchronized void newProject(String projectName, String keyAccess, String baseURL) throws Exception {
		iMathCloud.setBaseURL(baseURL);
		iMathCloud.registerUser(projectName, keyAccess, "");
		iMathCloud.setBaseURL(null);
	}
}
