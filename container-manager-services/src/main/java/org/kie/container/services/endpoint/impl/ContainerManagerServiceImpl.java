package org.kie.container.services.endpoint.impl;

import org.kie.container.services.endpoint.exception.BusinessException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.kie.container.services.endpoint.api.ContainerManagerService;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.base.BaseContainerConfiguration;
import org.kie.container.spi.model.providers.ContainerInstanceProvider;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ContainerManagerServiceImpl implements ContainerManagerService {


    @Context
    SecurityContext context;
    
    @Inject
    private ContainerInstanceProvider containerInstanceProvider;

    public ContainerManagerServiceImpl() {
    }

   

    @Override
    public List<ContainerInstance> getAllInstances() throws BusinessException {
//        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
//        if (principal != null && principal.getKeycloakSecurityContext() != null) {
            return containerInstanceProvider.getAllInstances();
//        } else {
//            throw new BusinessException("You don't have the appropriate permession to access this service");
//        }
    }

    @Override
    public String newInstance(String name) throws BusinessException {
        ContainerConfiguration conf = new BaseContainerConfiguration();
        conf.setProperty("name", name);
        Container c = containerInstanceProvider.create("test", conf);
        ContainerInstance ci;
        try {
            ci = containerInstanceProvider.createInstance(c);
            return ci.getInfo().getId();
        } catch (Exception ex) {
            Logger.getLogger(ContainerManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }
    
    @Override
    public void removeInstance(String id) throws BusinessException {
        try {
             containerInstanceProvider.removeInstance(id);
        } catch (Exception ex) {
            Logger.getLogger(ContainerManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    @Override
    public void startInstance(String id) throws BusinessException {
        containerInstanceProvider.getInstanceById(id).start();
    }

    @Override
    public void stopInstance(String id) throws BusinessException {
        containerInstanceProvider.getInstanceById(id).stop();
    }

    @Override
    public void restartInstance(String id) throws BusinessException {
        containerInstanceProvider.getInstanceById(id).restart();
    }

  
    

   
    
    

}
