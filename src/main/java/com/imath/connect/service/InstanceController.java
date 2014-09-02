package com.imath.connect.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;

import com.imath.connect.model.Instance;
import com.imath.connect.model.UserConnect;

/**
 * @author imath
 *
 * The controller class for {@link Instance}
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class InstanceController extends AbstractController {

    /**
     * Creates a new {@link Instance} in the DB and returns it.
     * @param cpu   The contracted number of CPUs
     * @param ram   The contracted RAM 
     * @param stg   The contracted storage
     * @param url   The url connection to iMath Cloud
     * @param user  The owner of the instance
     * @return      The newly created instance
     * @throws Exception when persistence is not possible
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Instance newInstance(long cpu, double ram, double stg, String url, UserConnect user) throws Exception {
        Instance instance = new Instance();
        instance.setCpu(cpu);
        instance.setCreationDate(new Date());
        instance.setRam(ram);
        instance.setStg(stg);
        instance.setUrl(url);
        instance.setOwner(user);
        db.makePersistent(instance);
        return instance;
    }

    /**
     * Returns the list of {@link Instance} own by the given user id. 
     * If UUID is null, it returns the public instances.
     * @param UUID  The Id of the owner
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Instance> getInstances(String UUID) {
        if (UUID == null) {
            return db.getInstanceDB().findByPublic();    
        } else {
            return db.getInstanceDB().findByOwner(UUID);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Instance getInstance(String UUID) throws EntityNotFoundException {
        Instance instance = this.db.getInstanceDB().findById(UUID);
        if (instance == null) {
            throw new EntityNotFoundException();  
        }
        return instance;
    }
}
