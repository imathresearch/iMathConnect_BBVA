package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserAccess;

@RequestScoped
public class UserJBossDB {
	
	 
	@Inject private EntityManager em;

	 
	 /**
	     * Returns a {@link UserAccess} from the given UUID
	     * @param UUID The UUID of the {@link UserAccess}
	     * @author imath
	     */
	public UserJBossDB findById(String name) {
        em.flush();
        try {
            return em.find(UserJBossDB.class, name);
        } catch (Exception e) {
            return null;
        }
    }

}
