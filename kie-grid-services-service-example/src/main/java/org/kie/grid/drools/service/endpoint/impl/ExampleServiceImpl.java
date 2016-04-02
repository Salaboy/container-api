package org.kie.grid.drools.service.endpoint.impl;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.kie.kie.grid.services.example.base.MyService;
import org.kie.grid.drools.service.endpoint.exception.BusinessException;
import org.kie.kie.grid.services.example.base.MyServiceImpl;
import org.kie.grid.impl.base.BaseServiceImpl;
import org.kie.grid.spi.Grid;
import org.kie.grid.drools.service.endpoint.api.ExampleService;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ExampleServiceImpl extends BaseServiceImpl implements ExampleService {

    @Context
    private SecurityContext context;

    @Inject
    private Grid grid;
    
    
    private MyService service;

    public ExampleServiceImpl() {
        
    }

    public void bootstrap(@Observes @Initialized(ApplicationScoped.class) Object init) throws  BusinessException{
        grid.bootstrap();
        service = new MyServiceImpl();
        service.setName("Example Service");
        service.setId(UUID.randomUUID().toString());
        grid.getWhitePages().register(service.getId(), service);
        grid.getYellowPages().register("exampleService", service);
        
    }

    @Override
    public String doSomething(String text) throws BusinessException {
        return service.doSomething(text); 
    }

   

    
    

}
