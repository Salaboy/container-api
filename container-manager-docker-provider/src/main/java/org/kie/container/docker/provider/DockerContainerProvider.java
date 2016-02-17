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
import org.kie.container.spi.model.providers.ContainerInstanceProvider;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
@DockerProvider
public class DockerContainerProvider extends BaseContainerProvider {

    private Map<String, Container> containers = new HashMap<>();
    private Map<String, DockerContainerInstanceProvider> providerMap = new HashMap<>();
    @Inject
    private Instance<DockerContainerInstanceProvider> providers;
    
    public DockerContainerProvider() {
        super("docker");

    }

    @Override
    public ContainerInstanceProvider newInstanceProvider(String providerInstanceName) {
        DockerContainerInstanceProvider provider = providers.get();
        provider.setName(providerInstanceName);
        providerMap.put(providerInstanceName, provider);
        return provider;
    }

    @Override
    public ContainerInstanceProvider getInstanceProviderByName(String instanceProviderName) {
        return providerMap.get(instanceProviderName);
    }

    @Override
    public List<ContainerInstanceProvider> getAllInstanceProviders() {
        return new ArrayList<ContainerInstanceProvider>(providerMap.values());
    }


    @Override
    public Container create(String name, ContainerConfiguration conf) {
        Container m = new DockerContainer(name, conf);
        containers.put(name, m);
        return m;
    }

    @Override
    public List<Container> getAll() {
        return new ArrayList<>(containers.values());
    }

}
