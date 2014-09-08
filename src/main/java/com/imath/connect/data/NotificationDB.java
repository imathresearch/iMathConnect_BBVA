package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.imath.connect.model.Notification;
import com.imath.connect.model.UserConnect;

/**
 * The Notification DB repository
 * @author imath
 *
 */

@RequestScoped
public class NotificationDB {
    @Inject
    private EntityManager em;
    
    /**
     * Returns a {@link Notification} from the given UUID
     * @param UUID The UUID of the {@link Notification}
     * @author imath
     */
    public Notification findById(String UUID) {
        em.flush();
        try {
            return em.find(Notification.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Returns the list of notifications given a user 
     * @param UUID
     * @return
     */
    public List<Notification> findByUser(String UUID) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> criteria = cb.createQuery(Notification.class);
        Root<Notification> notification = criteria.from(Notification.class);
        Join<Notification,UserConnect> projectJoin = notification.join("notificationUsers");
        Predicate p1 = cb.equal(projectJoin.get("UUID"), UUID);      
        criteria.select(notification).where(p1);
        List<Notification> out = em.createQuery(criteria).getResultList();
        return out;
    }
}
