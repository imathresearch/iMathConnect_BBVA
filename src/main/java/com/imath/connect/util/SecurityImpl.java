package com.imath.connect.util;

import javax.inject.Inject;

public class SecurityImpl implements SecurityInterface {

	@Inject Security sc;
	
    @Override
    public void updateSystemPassword(String userName, String password)
            throws Exception {
        sc.updateSystemPassword(userName, password);

    }
    
    @Override
    public void updateSystemPasswordDB(String userName, String password)
            throws Exception {
        sc.updateSystemPasswordDB(userName, password);

    }

    @Override
    public String generateHexMd5Password(String userName, String password)
            throws Exception {
        // TODO Auto-generated method stub
        return sc.generateHexMd5Password(userName, password);
    }
    
    @Override
    public String encryptHexMd5Password(String password)
            throws Exception {
        // TODO Auto-generated method stub
        return sc.encryptHexMd5Password(password);
    }

    @Override
    public void createLinuxUser(String userName) throws Exception {
        // TODO Auto-generated method stub
        sc.createLinuxUser(userName);
    }

    @Override
    public void createSystemUser(String userName, String password, String role)
            throws Exception {
        sc.createSystemUser(userName, password, role);
        
    }
    
    @Override
    public void createSystemUserDB(String userName, String password, String role)
            throws Exception {
        sc.createSystemUserDB(userName, password, role);
        
    }
    

}
