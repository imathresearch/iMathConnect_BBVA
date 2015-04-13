package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.imath.connect.model.UserAccess;
import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class UserAccessDB {
	
	
	@PersistenceContext(unitName="model")
	@PersistenceUnit(unitName="model")
	private EntityManager emModel = EntityManagerUtil.getEntityManager("model");
	/**
     * Returns a {@link UserAccess} from the given UUID
     * @param UUID The UUID of the {@link UserAccess}
     * @author imath
     */
    public UserAccess findById(String UUID) {
        emModel.flush();
        try {
            return emModel.find(UserAccess.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
	
}
