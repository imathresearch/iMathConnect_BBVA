package com.imath.connect.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.UserAccess;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserAccessController extends AbstractController{
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserAccess getUserAccess(String UUID) throws EntityNotFoundException {
        UserAccess access = this.db.getUserAccessDB().findById(UUID);
        if (access == null) {
            throw new EntityNotFoundException();  
        }
        return access;
    }

}
