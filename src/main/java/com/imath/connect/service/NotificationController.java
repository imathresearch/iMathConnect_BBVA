package com.imath.connect.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.Notification;
import com.imath.connect.model.UserConnect;

/**
 * @author imath
 *
 * The controller class for {@link Notification}
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NotificationController extends AbstractController {
	@Inject
	UserConnectController ucc;
	
	/**
	 * Creates a new Notification
	 * @param subject	The subject of the notification
	 * @param text		The core
	 * @param type		the type: 0 for-everybody, 1: for a subset of users
	 * @param uuids		If type is 0, this is ignored. If type is 1, the List<String> containing uuids of the users
	 * @return			The newly created {@link Notification}
	 * @throws Exception
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Notification newNotification(String subject, String text, Integer type, List<String> uuids) throws Exception {
    	if (type.intValue() < 0 || type.intValue() >1) throw new Exception("Bad Type");
    	Notification notif = new Notification();
    	Set<UserConnect> users = new HashSet<UserConnect>();
    	if (uuids != null) {
    		for (String uuid: uuids) {
    			UserConnect user = ucc.getUserConnect(uuid);
    			users.add(user);
    		}
    	}
    	notif.setNotificationUsers(users);
    	notif.setSubject(subject);
    	notif.setText(text);
    	notif.setType(type);
    	notif.setCreationDate(new Date());
    	db.makePersistent(notif);
    	return notif;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Notification getNotification(String uuid_notification) throws EntityNotFoundException{
    	Notification notif = db.getNotificationDB().findById(uuid_notification);
    	if (notif == null) throw new EntityNotFoundException("Notification not found uuid: " + uuid_notification);
    	return notif;
    }
    
    /**
     * Returns the list of notifications (PUBLIC and PRIVATES) for a given user. 
     * Only notifications created after the last connection of the user are retrieved
     * @param uuid_user
     * @return
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Notification> getNotificationsByUser(String uuid_user) throws Exception{
    	UserConnect user = db.getUserConnectDB().findById(uuid_user);
    	List<Notification> privateNotifications = db.getNotificationDB().findPrivateByUserDate(uuid_user, user.getLastConnection()); 	
    	List<Notification> publicNotifications = db.getNotificationDB().findPublicByDate(user.getLastConnection());
    	List<Notification> allNotifications = new ArrayList<Notification>();
    	allNotifications.addAll(privateNotifications);
    	allNotifications.addAll(publicNotifications);
    	return allNotifications;
    }
    
    /**
     * Removes a given notification 
     * @param uuid_notification
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeNotification(String uuid_notification) throws Exception{
    	Notification notif = this.getNotification(uuid_notification);
    	db.delete(notif);
    }
    
    /**
     * Removes a given notification for the given list of users_ids
     * @param uuid_notification
     * @throws Exception
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeNotificationForUser(String uuid_notification, List<String> uuids) throws Exception{
        //TODO: Unit test
    	Notification notif = this.getNotification(uuid_notification);
    	Set<UserConnect> usersNotif = notif.getNotificationUsers();
    	List<UserConnect> toRemove = new ArrayList<UserConnect>();
    	Map<String, String> map = new HashMap<String, String>();
    	for(String uuid:uuids) {
    		map.put(uuid, uuid);
    	}
    	for (UserConnect user: usersNotif) {
    		if(map.containsKey(user.getUUID())) {
    			toRemove.add(user);
    		}
    	}
    	notif.getNotificationUsers().removeAll(toRemove);
    	db.makePersistent(notif);
    }
    
    // For testing purposes only
    public void setUserConnectController(UserConnectController ucc) {
        this.ucc = ucc;
    }
    
}
