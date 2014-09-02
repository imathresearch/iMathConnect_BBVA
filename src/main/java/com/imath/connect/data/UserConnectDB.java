package com.imath.connect.data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.imath.connect.model.UserConnect;

@RequestScoped
public class UserConnectDB {
    /**
     * The UserConnect DB repository
     * @author imath
     *
     */
    @Inject
    private EntityManager em;
    
    /**
     * Returns a {@link UserConnect} from the given UUID
     * @param UUID The UUID of the {@link UserConnect}
     * @author imath
     */
    public UserConnect findById(String UUID) {
        em.flush();
        try {
            return em.find(UserConnect.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
}
