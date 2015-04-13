package com.imath.connect.service;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.imath.connect.data.MainDB;
import com.imath.connect.util.EntityManagerUtil;

public class AbstractController {
    
        @Inject protected Logger LOG;
        @Inject protected MainDB db;
        protected EntityManager emModel = EntityManagerUtil.getEntityManager("model");
        protected EntityManager emAuth = EntityManagerUtil.getEntityManager("authentication");

        
        // For testing purposes only, to simulate injection
        public void setMainDB(MainDB db) {
            this.db = db;
        }
        
        // For testing purposes only, to simulate injection    
        public void setLog(Logger LOG) {
            this.LOG = LOG;
        }
}