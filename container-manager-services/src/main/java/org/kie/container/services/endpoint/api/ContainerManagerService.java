/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.services.endpoint.api;

import org.kie.container.services.endpoint.exception.BusinessException;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.base.BaseContainerConfiguration;

/**
 *
 * @author salaboy
 */
@Path("containers")
public interface ContainerManagerService {

    @GET
    @Produces("application/json")
    @Path("")
    public List<ContainerInstance> getAllInstances() throws BusinessException;

    @POST
    @Path("")
    @Consumes(value = MediaType.APPLICATION_JSON )
    public String newInstance(@NotNull BaseContainerConfiguration conf) throws BusinessException;

    @DELETE
    @Path("{id}")
    public void removeInstance(@PathParam(value = "id") String id) throws BusinessException;

    @POST
    @Path("{id}/start")
    public void startInstance(@PathParam(value = "id") String id) throws BusinessException;

    @POST
    @Path("{id}/stop")
    public void stopInstance(@PathParam(value = "id") String id) throws BusinessException;

    @POST
    @Path("{id}/restart")
    public void restartInstance(@PathParam(value = "id") String id) throws BusinessException;

    @GET
    @Produces("application/json")
    @Path("providers")
    List<String> getAllInstanceProviderss() throws BusinessException;

}
