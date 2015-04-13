package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserAccess;
import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class MainDB {
	
	
	@PersistenceContext(unitName="model")
	@PersistenceUnit(unitName="model")
	private EntityManager emModel = EntityManagerUtil.getEntityManager("model");
   

	
	@Inject private UserConnectDB userConnectDB;
	@Inject private ProjectDB projectDB;
	@Inject private StandardConfigurationDB standardConfiguration;
	@Inject private InstanceDB instanceDB;
	@Inject private NotificationDB notificationDB;
	@Inject private UserAccessDB userAccessDB;
	
	public void makePersistent(Object obj) throws Exception {
		emModel.persist(obj);
		emModel.flush();
	 }
	
	public void delete(Object obj) throws Exception {
		emModel.remove(obj);
		emModel.flush();
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
	
	// For testing purposes only
	public void setEntityManager(EntityManager em) {
	    this.emModel = em;
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
