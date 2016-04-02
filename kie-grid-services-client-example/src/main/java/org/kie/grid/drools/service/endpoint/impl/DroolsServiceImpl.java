package org.kie.grid.drools.service.endpoint.impl;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.kie.grid.drools.service.endpoint.api.DroolsService;
import org.kie.grid.drools.service.endpoint.exception.BusinessException;
import org.kie.grid.drools.service.endpoint.impl.info.CommandInfo;
import org.kie.grid.drools.service.endpoint.impl.info.ResultInfo;
import org.kie.grid.impl.base.BaseParticipantImpl;
import org.kie.grid.impl.base.BaseServiceImpl;
import org.kie.grid.spi.Grid;
import org.kie.grid.spi.Participant;
import org.kie.grid.spi.Service;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class DroolsServiceImpl implements DroolsService {

    @Context
    private SecurityContext context;

    @Inject
    private Grid grid;
;

    public DroolsServiceImpl() {
        
    }

    public void bootstrap(@Observes @Initialized(ApplicationScoped.class) Object init) throws  BusinessException{
        grid.bootstrap();
        Participant p = new BaseParticipantImpl();
        p.setId(UUID.randomUUID().toString());
        p.setName("Drools Service Agent");
        grid.getWhitePages().register("droolsServiceAgent", p);
        Service s = new BaseServiceImpl();
        s.setId(UUID.randomUUID().toString());
        s.setName("Drools Service");
        grid.getYellowPages().register("droolsService", s);
        
    }

    @Override
    public ResultInfo execute(CommandInfo c) {
        
        System.out.println(" Executing C: "+c + " in "+this.hashCode());
        return new ResultInfo();
    }

    
    

}
