package com.imath.connect.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.StandardConfiguration;


/**
 * @author imath
 *
 * The controller class for {@link StandardConfiguration}
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StandardConfigurationController extends AbstractController {

    /**
     * Returns the list of {@link StandardConfiguration}
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<StandardConfiguration> getConfigurations() {
        return db.getStandardConfigurationDB().findAll();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public StandardConfiguration getConfiguration(String UUID) throws EntityNotFoundException {
        StandardConfiguration sc = this.db.getStandardConfigurationDB().findById(UUID);
        if (sc == null) {
            throw new EntityNotFoundException();  
        }
        return sc;
    }
    
}
