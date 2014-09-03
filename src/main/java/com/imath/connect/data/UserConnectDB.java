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

import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
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
    
    /**
     * Returns a {@link UserConnect} from the given userName
     * @param userName
     * @return
     * @author imath
     */
    public UserConnect findByUserName(String userName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserConnect> criteria = cb.createQuery(UserConnect.class);
        Root<UserConnect> userConnect = criteria.from(UserConnect.class);
        Predicate p1 = cb.equal(userConnect.get("userName"), userName);      
        criteria.select(userConnect).where(p1);
        List<UserConnect> out = em.createQuery(criteria).getResultList();
        if (out == null) return null;
        if (out.size()==0) return null;
        return out.get(0);
    }
    
    public List<UserConnect> findByProject(String UUID_project) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserConnect> criteria = cb.createQuery(UserConnect.class);
        Root<UserConnect> userConnect = criteria.from(UserConnect.class);
        Join<UserConnect, Project> userJoin = userConnect.join("projects");
        Predicate p1 = cb.equal(userJoin.get("UUID"), UUID_project);      
        criteria.select(userConnect).where(p1);
        List<UserConnect> out = em.createQuery(criteria).getResultList();
        return out;
    }
    
}
