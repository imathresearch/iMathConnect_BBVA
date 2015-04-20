package com.imath.connect.util;

public interface SecurityInterface {
    public void updateSystemPasswordDB(String userName, String password) throws Exception;
    public String encryptHexMd5Password(String password) throws Exception;
    public void createLinuxUser(String userName) throws Exception;
    public void createSystemUserDB(String userName, String password, String role) throws Exception;
}
