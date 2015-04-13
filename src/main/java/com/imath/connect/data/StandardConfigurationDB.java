package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.imath.connect.model.StandardConfiguration;
import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class StandardConfigurationDB {
    /**
     * The Instance DB repository
     * @author imath
     *
     */
    
    @PersistenceContext(unitName="model")
    @PersistenceUnit(unitName="model")
    private EntityManager emModel = EntityManagerUtil.getEntityManager("model");
    
    /**
     * Returns a {@link StandardConfiguration} from the given UUID
     * @param UUID The UUID of the {@link StandardConfiguration}
     * @author imath
     */
    public StandardConfiguration findById(String UUID) {
        emModel.flush();
        try {
            return emModel.find(StandardConfiguration.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Return the list of {@link StandardConfiguration} 
     */
    
    public List<StandardConfiguration> findAll() {
        CriteriaBuilder cb = emModel.getCriteriaBuilder();
        CriteriaQuery<StandardConfiguration> criteria = cb.createQuery(StandardConfiguration.class);
        Root<StandardConfiguration> standardConfiguration = criteria.from(StandardConfiguration.class);
        criteria.select(standardConfiguration);
        List<StandardConfiguration> out = emModel.createQuery(criteria).getResultList();
        return out;
    }
}
