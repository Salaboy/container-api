/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.services.endpoint.api;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.kie.grid.services.endpoint.exception.BusinessException;
import org.kie.grid.services.endpoint.info.WhitePagesEntryInfo;
import org.kie.grid.services.endpoint.info.YellowPagesEntryInfo;
import org.kie.grid.spi.Participant;
import org.kie.grid.spi.Service;

/**
 *
 * @author salaboy
 */
@Path("grid")
public interface KieGridService {

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("pages/yellow")
    List<YellowPagesEntryInfo> getYellowPagesEntries() throws BusinessException;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("pages/white")
    List<WhitePagesEntryInfo> getWhitePagesEntries() throws BusinessException;

    
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("participants")
    Participant newParticipant(@NotNull Participant p) throws BusinessException;
    
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("services")
    Service newService(Service s) throws BusinessException; 

}
