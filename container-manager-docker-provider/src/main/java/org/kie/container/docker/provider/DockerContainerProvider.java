/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.docker.provider;

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
@DockerProvider
public class DockerContainerProvider extends BaseContainerProvider {

    private Map<String, Container> containers = new HashMap<>();
    private Map<String, DockerContainerProviderInstance> providerMap = new HashMap<>();
    @Inject
    private Instance<DockerContainerProviderInstance> providers;
    
    public DockerContainerProvider() {
        super("docker");
        System.out.println(" >>> New DockerContainerProvider Instance: "+ this.hashCode());
    }

    @Override
    public ContainerProviderInstance newInstanceProvider(String providerInstanceName) {
        DockerContainerProviderInstance provider = providers.get();
        provider.setName(providerInstanceName);
        provider.setProviderName(getProviderName());
        providerMap.put(providerInstanceName, provider);
        return provider;
    }

    @Override
    public ContainerProviderInstance getInstanceProviderByName(String instanceProviderName) {
        return providerMap.get(instanceProviderName);
    }

    @Override
    public List<ContainerProviderInstance> getAllInstanceProviders() {
        return new ArrayList<ContainerProviderInstance>(providerMap.values());
    }


    @Override
    public Container createContainer(String name, ContainerConfiguration conf) {
        Container m = new DockerContainer(name, conf);
        containers.put(name, m);
        return m;
    }

    @Override
    public List<Container> getAllContainers() {
        return new ArrayList<>(containers.values());
    }

}
