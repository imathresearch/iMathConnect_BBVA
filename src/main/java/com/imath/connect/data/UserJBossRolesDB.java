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

import com.imath.connect.util.EntityManagerUtil;

@RequestScoped
public class UserJBossRolesDB {
	
	 
	 @PersistenceContext(unitName="authentication")
	 @PersistenceUnit(unitName="authentication")
     private EntityManager emAuth = EntityManagerUtil.getEntityManager("authentication");
	 
	 public List<UserJBossRolesDB> findByUserName(String userName) {
	        CriteriaBuilder cb = emAuth.getCriteriaBuilder();
	        CriteriaQuery<UserJBossRolesDB> criteria = cb.createQuery(UserJBossRolesDB.class);
	        Root<UserJBossRolesDB> UserJBossRolesDB = criteria.from(UserJBossRolesDB.class);
	        Predicate p1 = cb.equal(UserJBossRolesDB.get("userName"), userName);      
	        criteria.select(UserJBossRolesDB).where(p1);
	        List<UserJBossRolesDB> out = emAuth.createQuery(criteria).getResultList();
	        if (out == null) return null;
	        if (out.size()==0) return null;
	        return out;
	  }

}
