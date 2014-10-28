package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.imath.connect.model.UserAccess;

@RequestScoped
public class UserAccessDB {
	
	@Inject
    private EntityManager em;
	
	/**
     * Returns a {@link UserAccess} from the given UUID
     * @param UUID The UUID of the {@link UserAccess}
     * @author imath
     */
    public UserAccess findById(String UUID) {
        em.flush();
        try {
            return em.find(UserAccess.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
	
}
