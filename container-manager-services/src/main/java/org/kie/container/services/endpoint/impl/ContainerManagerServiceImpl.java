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
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.kie.container.services.endpoint.api.ContainerManagerService;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.base.BaseContainerConfiguration;
import org.kie.container.spi.model.providers.ContainerProvider;
import org.kie.container.services.info.ContainerInstanceProviderInfo;
import org.kie.container.spi.model.providers.ContainerInstanceProvider;
import org.kie.container.spi.model.providers.base.BaseContainerProviderConfiguration;

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
    private Instance<ContainerProvider> containerInstanceProvider;

    private Map<String, ContainerProvider> containerProviders = new HashMap<String, ContainerProvider>();

    private Map<String, ContainerInstanceProvider> containerInstanceProviders = new HashMap<String, ContainerInstanceProvider>();

    private Map<String, List<ContainerInstanceProvider>> containerInstanceProvidersByProvider = new HashMap<String, List<ContainerInstanceProvider>>();

    private Map<String, List<ContainerInstance>> instances = new HashMap<String, List<ContainerInstance>>();

    public ContainerManagerServiceImpl() {

    }

    @PostConstruct
    public void cacheBeans() {
        System.out.println(">>> After Init");
        for (ContainerProvider p : containerInstanceProvider) {
            System.out.println(">> New Container Instance Provider Found: " + p);
            containerProviders.put(p.getProviderName(), p);
        }

    }

    @Override
    public List<String> getAllContainerProviders() throws BusinessException {
        cacheBeans();
        List<String> providers = new ArrayList<String>();
        for (ContainerProvider cip : containerProviders.values()) {
            providers.add(cip.getProviderName());
        }
        return providers;
    }

    @Override
    public List<ContainerInstanceProviderInfo> getAllContainerProvidersInstancesInfo() throws BusinessException {
        List<ContainerInstanceProviderInfo> cipInfos = new ArrayList<ContainerInstanceProviderInfo>();
        for (ContainerInstanceProvider cip : containerInstanceProviders.values()) {
            cipInfos.add(new ContainerInstanceProviderInfo(cip.getName(), cip.getProviderName(), cip.getConfig().getProperties()));
        }
        return cipInfos;
    }

    @Override
    public void registerContainerProviderInstance(BaseContainerProviderConfiguration conf) throws BusinessException {
        String name = conf.getProperties().get("name");
        String provider = conf.getProperties().get("provider");
        ContainerProvider containerProvider = containerProviders.get(provider);
        ContainerInstanceProvider newInstanceProvider = containerProvider.newInstanceProvider(name);
        containerInstanceProviders.put(name, newInstanceProvider);
        if (containerInstanceProvidersByProvider.get(provider) == null) {
            containerInstanceProvidersByProvider.put(provider, new ArrayList<ContainerInstanceProvider>());
        }
        containerInstanceProvidersByProvider.get(provider).add(newInstanceProvider);
        newInstanceProvider.configure(conf);
    }

    @Override
    public void unregisterContainerProviderInstance(String instanceName) throws BusinessException {
        ContainerInstanceProvider instanceProvider = containerInstanceProviders.get(instanceName);
        //@TODO: clean up the instanceProvider (maybe disconect). 
        containerInstanceProviders.remove(instanceName);
    }

    @Override
    public String newContainerInstance(BaseContainerConfiguration conf) throws BusinessException {

        String providerString = conf.getProperties().get("provider");
        String name = conf.getProperties().get("name");
        System.out.println(">>>> Name: " + name);
        System.out.println(">>>> Provider: " + providerString);

        ContainerProvider provider = containerProviders.get(providerString);

        if (provider != null) {

            Container c = provider.create(name, conf);

            ContainerInstance ci;
            try {
                ContainerInstanceProvider cip = containerInstanceProvidersByProvider.get(providerString).get(0);
                ci = cip.createInstance(c);
                if (instances.get(cip.getName()) == null) {
                    instances.put(cip.getName(), new ArrayList<ContainerInstance>());
                }
                instances.get(cip.getName()).add(ci);
                return ci.getInfo().getId();
            } catch (Exception ex) {
                Logger.getLogger(ContainerManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "error";
    }

    @Override
    public List<ContainerInstance> getAllContainerInstances() throws BusinessException {
//        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
//        if (principal != null && principal.getKeycloakSecurityContext() != null) {
        List<ContainerInstance> cis = new ArrayList<ContainerInstance>();
        for (String provider : containerInstanceProvidersByProvider.keySet()) {
            for (ContainerInstanceProvider cip : containerInstanceProvidersByProvider.get(provider)) {
                instances.put(provider, cip.getAllInstances());
                cis.addAll(cip.getAllInstances());
            }

        }

        return cis;
//        } else {
//            throw new BusinessException("You don't have the appropriate permession to access this service");
//        }
    }

    @Override
    public void removeContainerInstance(String id) throws BusinessException {

        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.removeInstance(id);
        }

    }

    @Override
    public void startContainerInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).start();
        }

    }

    @Override
    public void stopContainerInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).stop();
        }

    }

    @Override
    public void restartContainerInstance(String id) throws BusinessException {
        ContainerInstanceProvider provider = getProviderForContainerInstanceId(id);
        if (provider != null) {
            provider.getInstanceById(id).restart();
        }

    }

    private ContainerInstanceProvider getProviderForContainerInstanceId(String id) {

        // @TODO: Nasty Lookup.. improve this one
        for (String providerString : instances.keySet()) {
            for (ContainerInstance ci : instances.get(providerString)) {
                if (ci.getInfo().getId().equals(id)) {
                    
                    return containerInstanceProviders.get(ci.getInfo().getName());
                    
                }
            }

        }
        return null;
    }

}
