package com.imath.connect.util;

public class SecurityImpl implements SecurityInterface {

    @Override
    public void updateSystemPassword(String userName, String password)
            throws Exception {
        Security.updateSystemPassword(userName, password);

    }

    @Override
    public String generateHexMd5Password(String userName, String password)
            throws Exception {
        // TODO Auto-generated method stub
        return Security.generateHexMd5Password(userName, password);
    }

    @Override
    public void createLinuxUser(String userName) throws Exception {
        // TODO Auto-generated method stub
        Security.createLinuxUser(userName);
    }

    @Override
    public void createSystemUser(String userName, String password, String role)
            throws Exception {
        Security.createSystemUser(userName, password, role);
        
    }

}
