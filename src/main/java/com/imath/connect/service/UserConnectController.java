package com.imath.connect.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import com.imath.connect.model.UserConnect;

/**
 * @author imath
 *
 * The controller class for UserConnect
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserConnectController extends AbstractController{
    
    /**
     * Creates a new {@link UserConnect} in the database
     * @param userName
     * @param eMail
     * @param organization
     * @param phone1
     * @param phone2
     * @return
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserConnect newUserConnect(String userName, String eMail, String organization, String phone1, String phone2) throws Exception {
        Date now = new Date();
        UserConnect peer = new UserConnect();
        peer.setEMail(eMail);
        peer.setLastConnection(now);
        peer.setCreationDate(now);
        peer.setPhone1(phone1);
        peer.setPhone2(phone2);
        peer.setOrganization(organization);
        peer.setUserName(userName);
        this.db.makePersistent(peer);
        return peer;
    }
    
    /**
     * Updates the date of the last connection
     * @param UUID
     * @throws EntityNotFoundException if the entity is not found
     * @throws PersistenceException if there is an error when persisting the entity
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void setLastConnection(String UUID) throws EntityNotFoundException, PersistenceException {
        UserConnect peer = this.db.getUserConnectDB().findById(UUID);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        peer.setLastConnection(new Date());
        try {
            this.db.makePersistent(peer);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserConnect getUserConnect(String UUID) throws EntityNotFoundException {
        UserConnect peer = this.db.getUserConnectDB().findById(UUID);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        return peer;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserConnect getUserConnectByUserName(String userName) throws EntityNotFoundException {
        UserConnect peer = this.db.getUserConnectDB().findByUserName(userName);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        return peer;
    }
}
