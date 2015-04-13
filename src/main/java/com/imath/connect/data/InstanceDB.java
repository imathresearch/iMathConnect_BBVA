package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.imath.connect.model.Instance;
import com.imath.connect.util.EntityManagerUtil;

/**
 * The Instance DB repository
 * @author imath
 *
 */

@RequestScoped
public class InstanceDB {
	
	@PersistenceContext(unitName="model")
	@PersistenceUnit(unitName="model")
	private EntityManager emModel = EntityManagerUtil.getEntityManager("model");
    
    /**
     * Returns a {@link Instance} from the given UUID
     * @param UUID The UUID of the {@link Instance}
     * @author imath
     */
    public Instance findById(String UUID) {
        emModel.flush();
        try {
            return emModel.find(Instance.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Return the list of {@link Instance} that belong to a given user
     * @param UUID The UUID of the {@link UserConnect}
     */
    
    public List<Instance> findByOwner(String UUID) {
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<Instance> criteria = cb.createQuery(Instance.class);
        Root<Instance> instance = criteria.from(Instance.class);
        Predicate p1 = cb.equal(instance.get("owner").get("UUID"), UUID);      
        criteria.select(instance).where(p1);
        List<Instance> out = emModel.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Return the list of {@link Instance} that are public (owner is NULL)
     */
    
    public List<Instance> findByPublic() {
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<Instance> criteria = cb.createQuery(Instance.class);
        Root<Instance> instance = criteria.from(Instance.class);
        Predicate p1 = cb.isNull(instance.get("owner").get("UUID"));      
        criteria.select(instance).where(p1);
        List<Instance> out = emModel.createQuery(criteria).getResultList();
        return out;
    }
    
    public Long countInstances() {
        CriteriaBuilder qb = emModel.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Instance.class)));
        return emModel.createQuery(cq).getSingleResult();
    }
}
