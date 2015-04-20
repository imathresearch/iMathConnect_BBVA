package com.imath.connect.util;

import javax.inject.Inject;

public class SecurityImpl implements SecurityInterface {

	@Inject Security sc;
	
   
    @Override
    public void updateSystemPasswordDB(String userName, String password)
            throws Exception {
        sc.updateSystemPasswordDB(userName, password);

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
    public void createSystemUserDB(String userName, String password, String role)
            throws Exception {
        sc.createSystemUserDB(userName, password, role);
        
    }
    

}
