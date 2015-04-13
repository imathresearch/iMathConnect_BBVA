package com.imath.connect.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerUtil {
  private static EntityManagerFactory entityManagerFactory;
 

  public static EntityManager getEntityManager(String namePersistenceUnit) {
	  try {
	      entityManagerFactory = Persistence.createEntityManagerFactory(namePersistenceUnit);

	    } catch (Throwable ex) {
	      System.err.println("Initial SessionFactory creation failed." + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	  return entityManagerFactory.createEntityManager();

  }
}

