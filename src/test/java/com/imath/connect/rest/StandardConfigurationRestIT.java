package com.imath.connect.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.imath.connect.model.StandardConfiguration;
import com.imath.connect.rest.StandardConfigurationRest.StandardConfigurationDTO;
import com.imath.connect.service.StandardConfigurationController;

@RunWith(Arquillian.class)
public class StandardConfigurationRestIT extends AbstractIT {
    @Inject StandardConfigurationController scc;
    @Inject StandardConfigurationRest scrEndPoint;
    
    @Before
    public void setUp() throws Exception {}
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void getConfigurationsIT() throws Exception {
        //1.- Case base: no configurations, empty list is returned
        Response rest = scrEndPoint.getConfigurations(null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<StandardConfigurationDTO> listDTO = (List<StandardConfigurationDTO>) rest.getEntity();
        assertEquals(0,listDTO.size());
        
        //2.- Normal case: we add some configurations and we retrieve them correctly
        long cpu1 = 2;
        double ram1= 4.5;
        double stg1 = 56.9;
        long cpu2 = 5;
        double ram2= 6.9;
        double stg2 = 776.8;
        
        StandardConfiguration sc1 = scc.newConfiguration(cpu1, ram1, stg1);
        StandardConfiguration sc2 = scc.newConfiguration(cpu2, ram2, stg2);
        rest = scrEndPoint.getConfigurations(null);
        assertEquals(Response.Status.OK.getStatusCode(), rest.getStatus());
        @SuppressWarnings("unchecked")
        List<StandardConfigurationDTO> listDTO2 = (List<StandardConfigurationDTO>) rest.getEntity();
        assertEquals(2,listDTO2.size());
        for(StandardConfigurationDTO eDTO: listDTO2) {
            StandardConfiguration theOne = null;
            if (eDTO.UUID.equals(sc1.getUUID())) {
                theOne = sc1;
            } else if((eDTO.UUID.equals(sc2.getUUID()))) {
                theOne = sc2;
            }
            assertEquals(theOne.getCpu(), eDTO.cpu);
            assertEquals(theOne.getRam(), eDTO.ram, 0.001);
            assertEquals(theOne.getStg(), eDTO.stg, 0.001);
        }
    }
}
