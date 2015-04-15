package com.imath.connect.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserJBoss getUserJBoss(String userName) throws EntityNotFoundException {
		UserJBoss peer = this.db.getUserJBossDB().findByUserName(userName);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        return peer;
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updatePassword(String userName, String password) throws EntityNotFoundException, PersistenceException {
		UserJBoss peer = this.db.getUserJBossDB().findByUserName(userName);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        peer.setPassword(password);
        try {
            this.db.makePersistent(peer);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

}
