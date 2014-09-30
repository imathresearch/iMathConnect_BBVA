package com.imath.connect.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import com.imath.connect.model.UserConnect;
import com.imath.connect.util.Photo;

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
    public UserConnect newUserConnect(String userName, String eMail, String organization, String phone1, String phone2, byte[] PhotoByte) throws Exception {
    	Date past = new Date(1081157732 * 1000);
    	Date now = new Date();
        UserConnect peer = new UserConnect();
        peer.setEMail(eMail);
        peer.setLastConnection(past);
        peer.setCurrentConnection(now);
        peer.setCreationDate(now);
        peer.setPhone1(phone1);
        peer.setPhone2(phone2);
        peer.setOrganization(organization);
        peer.setUserName(userName);
        peer.setPhoto(PhotoByte);
        this.db.makePersistent(peer);
        return peer;
    }
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserConnect newUserConnectInvitation(String eMail) throws Exception {
        String userName = findUserName(eMail);
        String org = findOrganization(eMail);
        Date past = new Date(1081157732 * 1000);
        Date now = new Date();
        UserConnect peer = new UserConnect();
        peer.setEMail(eMail);
        peer.setLastConnection(past);
        peer.setCurrentConnection(past);
        peer.setCreationDate(now);
        peer.setOrganization(org);
        peer.setUserName(userName);
        this.db.makePersistent(peer);
        return peer;
    }
    
    private String findOrganization(String eMail) throws Exception {
        String errMsg = "No possible userName";
        String [] split = eMail.split("@");
        if (split.length!=2) throw new Exception(errMsg);
        String []aux= split[1].split("\\.");
        if (aux.length!=2) throw new Exception(errMsg);
        return aux[0];
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private String findUserName(String eMail) throws Exception {
        String errMsg = "No possible userName";
        String [] split = eMail.split("@");
        if (split.length!=2) throw new Exception(errMsg);
        String pre = split[0];
        String potencialUserName = keepLetters(pre);
        if (pre.trim().equals("")) throw new Exception(errMsg);
        UserConnect user = db.getUserConnectDB().findByUserName(potencialUserName);
        if (user!=null) {
            String []aux= split[1].split("\\.");
            if (aux.length!=2) throw new Exception(errMsg);
            potencialUserName = potencialUserName + "AT" + keepLetters(aux[0]);
            user = db.getUserConnectDB().findByUserName(potencialUserName);
            if (user!=null) throw new Exception(errMsg);
        }
        return potencialUserName;
    }
    
    private String keepLetters(String pre) {
        StringBuffer out = new StringBuffer("");
        for(int i=0; i<pre.length(); i++) {
            char ch = pre.charAt(i);
            if (Character.isLetter(ch)) {
                out.append(ch);
            }
        }
        return out.toString();
    }
    
    /**
     * Updates the date of the last connection
     * @param UUID
     * @throws EntityNotFoundException if the entity is not found
     * @throws PersistenceException if there is an error when persisting the entity
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void setCurrentConnection(String UUID) throws EntityNotFoundException, PersistenceException {
        UserConnect peer = this.db.getUserConnectDB().findById(UUID);
        if (peer == null) {
            throw new EntityNotFoundException();  
        }
        peer.setLastConnection(peer.getCurrentConnection());
        peer.setCurrentConnection(new Date());
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
    public UserConnect getUserConnectByUserName(String userName) throws Exception {
        UserConnect peer = this.db.getUserConnectDB().findByUserName(userName);
        if (peer == null) {
            throw new Exception();  
        }
        return peer;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<UserConnect> getCollaborationUsersByProject(String UUID_project) {
        return db.getUserConnectDB().findByProject(UUID_project);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public UserConnect getUserByEMail(String eMail) throws Exception {
        UserConnect peer = db.getUserConnectDB().findByEMail(eMail);
        if (peer == null) {
            throw new Exception();  
        }
        return peer;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateUserConnectByte(String UUID, byte[] FileByteFormat) throws Exception {
        UserConnect peer = this.getUserConnect(UUID);
        if (peer!=null) {
            peer.setPhoto(FileByteFormat);
            this.db.makePersistent(peer);
        }
    }
    
    public long countUsers() {
        return db.getUserConnectDB().countUsers();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long countUsersCol() {
        return db.getUserConnectDB().countUsersCol();
    }
}
