package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserJBossRoles;

@RequestScoped
public class AuthenticationDB {
	
	@PersistenceContext(unitName="authentication")
	@PersistenceUnit(unitName="authentication")
	private EntityManager emAuthentication;
	
	
	@Inject private UserJBossDB userJBossDB;
	@Inject private UserJBossRoles userJBossRolesDB;

	
	public void makePersistent(Object obj) throws Exception {
		emAuthentication.persist(obj);
		emAuthentication.flush();
	 }
	
	public void delete(Object obj) throws Exception {
		emAuthentication.remove(obj);
		emAuthentication.flush();
	}

	public UserJBossDB getUserJBossDB() {
	    return this.userJBossDB;
	}
	
	public UserJBossRoles getUserJBossRoles() {
	    return this.userJBossRolesDB;
	}
}
