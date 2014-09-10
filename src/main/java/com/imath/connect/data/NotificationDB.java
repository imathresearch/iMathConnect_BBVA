package com.imath.connect.data;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
     * Returns the list of PRIVATE notifications given a user 
     * @param UUID
     * @return
     */
    public List<Notification> findPrivateByUser(String UUID) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> criteria = cb.createQuery(Notification.class);
        Root<Notification> notification = criteria.from(Notification.class);
        Join<Notification,UserConnect> join = notification.join("notificationUsers");
        Predicate p1 = cb.equal(join.get("UUID"), UUID);      
        criteria.select(notification).where(p1);
        List<Notification> out = em.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Returns the list of PRIVATE notifications of a given user, created after the given date 
     * @param UUID
     * @param date
     * @return
     */
    public List<Notification> findPrivateByUserDate(String UUID, Date date) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> criteria = cb.createQuery(Notification.class);
        Root<Notification> notification = criteria.from(Notification.class);
        Join<Notification,UserConnect> join = notification.join("notificationUsers");
        Predicate p1 = cb.equal(join.get("UUID"), UUID);
        Predicate p2 = cb.greaterThanOrEqualTo(notification.get("creationDate").as(Date.class),date);
        criteria.select(notification).where(cb.and(p1,p2));
        List<Notification> out = em.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Returns the list of PUBLIC notifications created after the given date 
     * @param date
     * @return
     */
    public List<Notification> findPublicByDate(Date date){
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> criteria = cb.createQuery(Notification.class);
        Root<Notification> notification = criteria.from(Notification.class);
        Predicate p1 = cb.equal(notification.get("type"), 0);
        Predicate p2 = cb.greaterThanOrEqualTo(notification.get("creationDate").as(Date.class),date);
        criteria.select(notification).where(cb.and(p1,p2));
        List<Notification> out = em.createQuery(criteria).getResultList();
        return out;
    }
}
