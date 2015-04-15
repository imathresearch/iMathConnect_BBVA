package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserAccess;
import com.imath.connect.model.UserJBoss;

@RequestScoped
public class UserJBossDB {
	
	 
	@Inject private EntityManager em;

	 
	 /**
	     * Returns a {@link UserAccess} from the given UUID
	     * @param UUID The UUID of the {@link UserAccess}
	     * @author imath
	     */
	public UserJBoss findByUserName(String name) {
        em.flush();
        try {
            return em.find(UserJBoss.class, name);
        } catch (Exception e) {
            return null;
        }
    }

}
