package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserAccess;
import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class UserJBossDB {
	
	 
	 @PersistenceContext(unitName="authentication")
	 @PersistenceUnit(unitName="authentication")
     private EntityManager emAuth = EntityManagerUtil.getEntityManager("authentication");
	 
	 /**
	     * Returns a {@link UserAccess} from the given UUID
	     * @param UUID The UUID of the {@link UserAccess}
	     * @author imath
	     */
	public UserJBossDB findById(String name) {
        emAuth.flush();
        try {
            return emAuth.find(UserJBossDB.class, name);
        } catch (Exception e) {
            return null;
        }
    }

}
