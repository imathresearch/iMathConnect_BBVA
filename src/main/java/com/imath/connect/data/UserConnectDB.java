package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;
import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class UserConnectDB {
    /**
     * The UserConnect DB repository
     * @author imath
     *
     */
    
    @PersistenceContext(unitName="model")
    @PersistenceUnit(unitName="model")
    private EntityManager emModel = EntityManagerUtil.getEntityManager("model");
    
    /**
     * Returns a {@link UserConnect} from the given UUID
     * @param UUID The UUID of the {@link UserConnect}
     * @author imath
     */
    public UserConnect findById(String UUID) {
        emModel.flush();
        try {
            return emModel.find(UserConnect.class, UUID);
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
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<UserConnect> criteria = cb.createQuery(UserConnect.class);
        Root<UserConnect> userConnect = criteria.from(UserConnect.class);
        Predicate p1 = cb.equal(userConnect.get("userName"), userName);      
        criteria.select(userConnect).where(p1);
        List<UserConnect> out = emModel.createQuery(criteria).getResultList();
        if (out == null) return null;
        if (out.size()==0) return null;
        return out.get(0);
    }
    
    /**
     * Returns a {@link UserConnect} from the given eMail
     * @param userName
     * @return
     * @author imath
     */
    public UserConnect findByEMail(String eMail) {
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<UserConnect> criteria = cb.createQuery(UserConnect.class);
        Root<UserConnect> userConnect = criteria.from(UserConnect.class);
        Predicate p1 = cb.equal(userConnect.get("eMail"), eMail);      
        criteria.select(userConnect).where(p1);
        List<UserConnect> out = emModel.createQuery(criteria).getResultList();
        if (out == null) return null;
        if (out.size()==0) return null;
        return out.get(0);
    }
    
    /**
     * Returns the list of {@link UserConnect} that collaborate in a project
     * @param UUID_project
     * @return
     * @author imath
     */
    public List<UserConnect> findByProject(String UUID_project) {
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<UserConnect> criteria = cb.createQuery(UserConnect.class);
        Root<UserConnect> userConnect = criteria.from(UserConnect.class);
        Join<UserConnect, Project> userJoin = userConnect.join("projects");
        Predicate p1 = cb.equal(userJoin.get("UUID"), UUID_project);      
        criteria.select(userConnect).where(p1);
        List<UserConnect> out = emModel.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Returns the number of users
     * @return
     */
    public Long countUsers() {
        CriteriaBuilder qb = emModel.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(UserConnect.class)));
        return emModel.createQuery(cq).getSingleResult();
    }
    
    /**
     * Returns the number of collaborators
     * @return
     */
    public Long countUsersCol() {
        CriteriaBuilder qb = emModel.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<UserConnect> userConnect = cq.from(UserConnect.class);
        Join<UserConnect, Project> userJoin = userConnect.join("projects");
        cq.select(qb.count(userJoin));
        Long out = emModel.createQuery(cq).getSingleResult();
        return out;
    }
    
}
