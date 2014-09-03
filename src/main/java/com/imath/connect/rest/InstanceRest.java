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

import com.imath.connect.model.Instance;
import com.imath.connect.model.UserConnect;
import com.imath.connect.security.SecurityManager;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;

@Path(Constants.baseURL)
@RequestScoped
@Stateful
public class InstanceRest {
    @Inject private UserConnectController ucc;
    @Inject private InstanceController ic;
    @Inject private Logger LOG;
    
    @GET
    @Path(Constants.instances + "/{uuid_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstances(@PathParam("uuid_user") String uuid_user, @Context SecurityContext sc) {
        try {
            if (!uuid_user.isEmpty()) {
                UserConnect owner = ucc.getUserConnect(uuid_user);
                SecurityManager.secureBasic(owner.getUserName(), sc);
            }
            List<Instance> instances = null;
            // If uuid_user is empty, we return the public instances
            if (uuid_user.isEmpty()) {
                instances = ic.getInstances(null);
            } else {
                instances = ic.getInstances(uuid_user);
            }
            List<InstanceDTO> listDTO = convertToDTO(instances);
            return Response.status(Response.Status.OK).entity(listDTO).build();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    private List<InstanceDTO> convertToDTO(List<Instance> instances) {
        List<InstanceDTO> instanceDTOs = new ArrayList<InstanceDTO>();
        for(Instance instance:instances) {
            InstanceDTO instanceDTO = new InstanceDTO();
            instanceDTO.convert(instance);
            instanceDTOs.add(instanceDTO);
        }
        return instanceDTOs;
    }
    
    public static class InstanceDTO {
        public String UUID;
        public long cpu;
        public double ram;
        public double stg;
        public String url;
        public void convert(Instance inst) {
            this.UUID = inst.getUUID();
            this.cpu = inst.getCpu();
            this.ram = inst.getRam();
            this.stg = inst.getStg();
            this.url = inst.getUrl();
        }
    }
    
}
