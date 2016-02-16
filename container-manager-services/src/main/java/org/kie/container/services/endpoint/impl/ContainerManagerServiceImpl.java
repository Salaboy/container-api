package org.kie.container.services.endpoint.impl;

import com.spotify.docker.client.shaded.javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import org.kie.container.services.endpoint.exception.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.kie.container.docker.provider.DockerInstanceProvider;

import org.kie.container.services.endpoint.api.ContainerManagerService;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.base.BaseContainerConfiguration;
import org.kie.container.spi.model.providers.ContainerInstanceProvider;
import org.kie.container.was.provider.WASInstanceProvider;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ContainerManagerServiceImpl implements ContainerManagerService {

    @Context
    private SecurityContext context;

    @Inject
    @Any
    private Instance<ContainerInstanceProvider> containerInstanceProvider;
    
    private Map<String, ContainerInstanceProvider> containerInstanceProviders = new HashMap<String, ContainerInstanceProvider>();

    private Map<String, List<ContainerInstance>> providerInstances = new HashMap<String, List<ContainerInstance>>();

    public ContainerManagerServiceImpl() {

    }

    @PostConstruct
    public void cacheBeans() {
        System.out.println(">>> After Init");
        for (ContainerInstanceProvider p : containerInstanceProvider) {
            System.out.println(">> New Container Instance Provider Found: " + p);
            containerInstanceProviders.put(p.getProviderName(), p);
        }

    }

    @Override
    public List<ContainerInstance> getAllInstances() throws BusinessException {
//        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
//        if (principal != null && principal.getKeycloakSecurityContext() != null) {
        List<ContainerInstance> cis = new ArrayList<ContainerInstance>();
        for (ContainerInstanceProvider i : containerInstanceProvider) {
            providerInstances.put(i.toString(), i.getAllInstances());
            cis.addAll(i.getAllInstances());
        }

        return cis;
//        } else {
//            throw new BusinessException("You don't have the appropriate permession to access this service");
//        }
    }

    @Override
    public List<String> getAllInstanceProviderss() throws BusinessException {
        cacheBeans();
        List<String> providers = new ArrayList<String>();
        for(ContainerInstanceProvider cip : containerInstanceProviders.values()){
            providers.add(cip.getProviderName());
        }
        return providers;
    }

    @Override
    public String newInstance(BaseContainerConfiguration conf) throws BusinessException {

        String providerString = conf.getProperties().get("provider");
        String name = conf.getProperties().get("name");
        System.out.println(">>>> Name: " + name);
        System.out.println(">>>> Provider: " + providerString);

        ContainerInstanceProvider provider = containerInstanceProviders.get(providerString);
        
        if (provider != null) {
            Container c = provider.create(name, conf);
            ContainerInstance ci;
            try {
                ci = provider.createInstance(c);
                if (providerInstances.get(providerString) == null) {
                    providerInstances.put(providerString, new ArrayList<ContainerInstance>());
                }
                providerInstances.get(providerString).add(ci);
                return ci.getInfo().getId();
            } catch (Exception ex) {
                Logger.getLogger(ContainerManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "error";
    }

    @Override
    public void removeInstance(String id) throws BusinessException {

        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.removeInstance(id);
        }

    }

    @Override
    public void startInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).start();
        }

    }

    @Override
    public void stopInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).stop();
        }

    }

    @Override
    public void restartInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).restart();
        }

    }
    
    
    

    private ContainerInstanceProvider getProviderForContainerInstanceId(String id) {

        // @TODO: Nasty Lookup.. improve this one
        for (String providerString : providerInstances.keySet()) {
            for (ContainerInstance ci : providerInstances.get(providerString)) {
                if (ci.getInfo().getId().equals(id)) {
                    ContainerInstanceProvider provider = null;

                    if (providerString.equals("docker")) {
                        provider = containerInstanceProvider.select(new AnnotationLiteral<DockerInstanceProvider>() {
                        }).get();

                    } else if (providerString.equals("was")) {
                        provider = containerInstanceProvider.select(new AnnotationLiteral<WASInstanceProvider>() {
                        }).get();

                    }
                    if (provider != null) {

                        return provider;
                    }
                }
            }

        }
        return null;
    }

}
