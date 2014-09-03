package com.imath.connect.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.imath.connect.model.StandardConfiguration;
import com.imath.connect.service.StandardConfigurationController;
import com.imath.connect.util.Constants;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class StandardConfigurationRest {
    
    @Inject StandardConfigurationController scc; 
    @Inject private Logger LOG;
    
    @GET
    @Path(Constants.configurations)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfigurations(@Context SecurityContext sc) {
        try {
            List<StandardConfiguration> listSC = scc.getConfigurations();
            List<StandardConfigurationDTO> listDTO = new ArrayList<StandardConfigurationDTO>();
            if (listSC!=null) {
                for(StandardConfiguration elem:listSC) {
                    StandardConfigurationDTO elemDTO = new StandardConfigurationDTO();
                    elemDTO.convert(elem);
                    listDTO.add(elemDTO);
                }
            }
            return Response.status(Response.Status.OK).entity(listDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    public static class StandardConfigurationDTO {
        public String UUID;
        public long cpu;
        public double ram;
        public double stg;
        
        public void convert(StandardConfiguration sc) {
            this.UUID = sc.getUUID();
            this.cpu = sc.getCpu();
            this.ram = sc.getRam();
            this.stg = sc.getStg();
        }
    }
}
