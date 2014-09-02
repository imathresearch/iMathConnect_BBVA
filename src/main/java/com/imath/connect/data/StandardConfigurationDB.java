package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.imath.connect.model.StandardConfiguration;

@RequestScoped
public class StandardConfigurationDB {
    /**
     * The Instance DB repository
     * @author imath
     *
     */
    @Inject
    private EntityManager em;
    
    /**
     * Returns a {@link StandardConfiguration} from the given UUID
     * @param UUID The UUID of the {@link StandardConfiguration}
     * @author imath
     */
    public StandardConfiguration findById(String UUID) {
        em.flush();
        try {
            return em.find(StandardConfiguration.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Return the list of {@link StandardConfiguration} 
     */
    
    public List<StandardConfiguration> findByOwner(String UUID) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StandardConfiguration> criteria = cb.createQuery(StandardConfiguration.class);
        Root<StandardConfiguration> standardConfiguration = criteria.from(StandardConfiguration.class);
        criteria.select(standardConfiguration);
        List<StandardConfiguration> out = em.createQuery(criteria).getResultList();
        return out;
    }
}
