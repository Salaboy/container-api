/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.drools.service.endpoint.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.kie.grid.drools.service.endpoint.exception.BusinessException;

/**
 *
 * @author salaboy
 */
@Path("example")
public interface ExampleService {
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("execute")
    String doSomething(String text) throws BusinessException;
   

}
