/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.drools.service.endpoint.api;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.kie.grid.drools.service.endpoint.exception.BusinessException;
import org.kie.grid.drools.service.endpoint.impl.info.CommandInfo;
import org.kie.grid.drools.service.endpoint.impl.info.ResultInfo;

/**
 *
 * @author salaboy
 */
@Path("drools")
public interface DroolsService {
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("execute")
    ResultInfo execute(@NotNull CommandInfo c) throws BusinessException;
   

}
