/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.providers.base.BaseContainerProvider;
import org.kie.container.spi.model.providers.ContainerProviderInstance;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
@WASProvider
public class WASContainerProvider extends BaseContainerProvider {

    private Map<String, Container> containers = new HashMap<>();
    
    @Inject
    private Instance<WASContainerProviderInstance> providers;
    private Map<String, WASContainerProviderInstance> providerMap = new HashMap<>();

    public WASContainerProvider() {
        super("was");
        System.out.println(" >>> New WASContainerProvider Instance: "+ this.hashCode());
    }


    @Override
    public Container createContainer(String name, ContainerConfiguration conf) {
        Container m = new WASContainer(name, conf);
        containers.put(name, m);
        return m;
    }

    @Override
    public List<Container> getAllContainers() {
        return new ArrayList<>(containers.values());
    }

    @Override
    public ContainerProviderInstance newInstanceProvider(String instanceProviderName) {
        WASContainerProviderInstance instanceProvider = providers.get();
        instanceProvider.setName(instanceProviderName);
        instanceProvider.setProviderName(this.getProviderName());
        providerMap.put(instanceProviderName, instanceProvider);
        return instanceProvider;
    }

    @Override
    public ContainerProviderInstance getInstanceProviderByName(String instanceProviderName) {
        return providerMap.get(instanceProviderName);
    }

    @Override
    public List<ContainerProviderInstance> getAllInstanceProviders() {
        return new ArrayList<ContainerProviderInstance>(providerMap.values());
    }

   

}
