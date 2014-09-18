package com.imath.connect.util;

public interface IMathCloudInterface {
	public void newProject(String privateProjectName, String keyAccess, String publicProjectName, String baseURL) throws Exception;
	public void removeProject(String privateProjectName, String key, String baseURL) throws Exception;
}
