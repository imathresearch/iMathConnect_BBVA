package com.imath.connect.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.imath.connect.model.UserJBossRoles;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserJBossRolesController extends AbstractController {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserJBossRoles newUserJBossRoles(String userName, String role) throws Exception {
		UserJBossRoles user = new UserJBossRoles();
		user.setUsername(userName);
		user.setRole(role);
        this.db.makePersistent(user);
        return user;
    }

}
