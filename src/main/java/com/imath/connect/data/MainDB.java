package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.imath.connect.model.UserAccess;
import com.imath.connect.model.UserJBoss;

@RequestScoped
public class MainDB {

	@Inject private EntityManager em;
	@Inject private UserConnectDB userConnectDB;
	@Inject private ProjectDB projectDB;
	@Inject private StandardConfigurationDB standardConfiguration;
	@Inject private InstanceDB instanceDB;
	@Inject private NotificationDB notificationDB;
	@Inject private UserAccessDB userAccessDB;
	@Inject private UserJBossDB userJBossDB;
	
	public void makePersistent(Object obj) throws Exception {
	    em.persist(obj);
	    em.flush();
	 }
	
	public void delete(Object obj) throws Exception {
	    em.remove(obj);
	    em.flush();
	}

	public UserConnectDB getUserConnectDB() {
	    return this.userConnectDB;
	}
	
	public ProjectDB getProjectDB() {
	    return this.projectDB;
	}
	
	public StandardConfigurationDB getStandardConfigurationDB() {
	    return this.standardConfiguration;
	}
	
	public InstanceDB getInstanceDB() {
	    return this.instanceDB;
	}
	
	public NotificationDB getNotificationDB() {
		return this.notificationDB;
	}
	
	public UserAccessDB getUserAccessDB(){
		return this.userAccessDB;
	}
	
	public UserJBossDB getUserJBossDB(){
		return this.userJBossDB;
	}
	
	
	// For testing purposes only
	public void setEntityManager(EntityManager em) {
	    this.em = em;
	}
	
	public void setUserConnectDB(UserConnectDB userConnectDB) {
	    this.userConnectDB = userConnectDB;
	}
	
	public void setProjectDB(ProjectDB projectDB) {
	    this.projectDB = projectDB;
	}
	
	public void setStandardConfigurationDB(StandardConfigurationDB standardConfigurationDB) {
	    this.standardConfiguration = standardConfigurationDB;
	}
	
	public void setInstanceDB(InstanceDB instanceDB) {
	    this.instanceDB = instanceDB;
	}
	
	public void setNotificationDB(NotificationDB notificationDB) {
		this.notificationDB = notificationDB;
	}
}
