package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class MainDB {

	@Inject private EntityManager em;
	
	public void makePersistent(Object obj) throws Exception {
	    em.persist(obj);
	    em.flush();
	 }
	
	public void delete(Object obj) throws Exception {
	    em.remove(obj);
	    em.flush();
	}

	
	// For testing purposes only
	public void setEntityManager(EntityManager em) {
	    this.em = em;
	}
}
