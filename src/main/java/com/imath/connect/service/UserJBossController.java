package com.imath.connect.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.imath.connect.model.UserAccess;
import com.imath.connect.model.UserConnect;
import com.imath.connect.model.UserJBoss;
import com.imath.connect.util.Constants;
import com.imath.connect.util.Encryptor;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserJBossController extends AbstractController {
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserJBoss newUserJBoss(String userName, String password) throws Exception {
		UserJBoss user = new UserJBoss();
		user.setUsername(userName);
		user.setPassword(password);
        this.db.makePersistent(user);
        return user;
    }

}
