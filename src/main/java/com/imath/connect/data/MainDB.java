package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class MainDB {

	@Inject private EntityManager em;
	@Inject private UserConnectDB userConnectDB;
	@Inject private ProjectDB projectDB;
	@Inject private StandardConfigurationDB standardConfiguration;
	@Inject private InstanceDB instanceDB;
	
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
}
