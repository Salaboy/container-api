/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.manager.cluster.store.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.cache.Cache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.kie.container.manager.cluster.store.ContainerRegistry;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.providers.info.ContainerProviderInfo;
import org.kie.container.spi.model.providers.info.ContainerProviderInstanceInfo;

/**
 *
 * @author salaboy
 */
public class IgniteContainerRegistryImpl implements ContainerRegistry {

    private Ignite ignite = Ignition.start("config/example-ignite.xml");
    private IgniteCache<String, ContainerProviderInfo> containerProviders;
    private IgniteCache<String, ContainerProviderInstanceInfo> containerProviderInstances;
    private IgniteCache<String, List<ContainerProviderInstanceInfo>> containerProviderInstancesByProvider;
    private IgniteCache<String, List<ContainerInstanceInfo>> containerInstancesByProvider;

    public IgniteContainerRegistryImpl() {

    }

    public void init() {
        IgniteState state = Ignition.state();
        if (!state.equals(IgniteState.STARTED)) {
            ignite = Ignition.start("config/example-ignite.xml");
        }
        containerProviders = ignite.getOrCreateCache("containerProvidersRegistry");
        containerProviderInstances = ignite.getOrCreateCache("containerProviderInstancesRegistry");
        containerProviderInstancesByProvider = ignite.getOrCreateCache("containerProviderInstancesByProviderRegistry");
        containerInstancesByProvider = ignite.getOrCreateCache("containerInstancesByProviderRegistry");
    }

    @Override
    public void registerContainerProvider(String providerName, ContainerProviderInfo p) {
        System.out.println("REgistering: " + providerName + " - Instance : " + p.getProviderName());
        containerProviders.putIfAbsent(providerName, p);
    }

    @Override
    public List<ContainerProviderInfo> getContainerProviders() {
        Iterator<Cache.Entry<String, ContainerProviderInfo>> iterator = containerProviders.iterator();
        List<ContainerProviderInfo> providers = new ArrayList<ContainerProviderInfo>();
        while (iterator.hasNext()) {
            providers.add(iterator.next().getValue());
        }
        return providers;
    }

    @Override
    public ContainerProviderInfo getContainerProviderByName(String provider) {
        return containerProviders.get(provider);
    }

    @Override
    public List<ContainerProviderInstanceInfo> getContainerProviderInstances() {
        Iterator<Cache.Entry<String, ContainerProviderInstanceInfo>> iterator = containerProviderInstances.iterator();
        List<ContainerProviderInstanceInfo> providerInstances = new ArrayList<ContainerProviderInstanceInfo>();
        while (iterator.hasNext()) {
            providerInstances.add(iterator.next().getValue());
        }
        return providerInstances;
    }

    @Override
    public void registerContainerProviderInstance(String name, ContainerProviderInstanceInfo newInstanceProvider) {
        containerProviderInstances.put(name, newInstanceProvider);
    }

    @Override
    public void registerContainerProviderInstanceByProvider(String provider, ContainerProviderInstanceInfo newInstanceProvider) {
        containerProviderInstancesByProvider.get(provider).add(newInstanceProvider);
    }

    @Override
    public ContainerProviderInstanceInfo getContainerProviderInstanceByName(String instanceName) {
        return containerProviderInstances.get(instanceName);
    }

    @Override
    public void removeContainerProviderInstance(String instanceName) {
        containerProviderInstances.remove(instanceName);
    }

    @Override
    public ContainerProviderInfo getContainerProvider(String providerString) {
        return containerProviders.get(providerString);
    }

    @Override
    public void registerContainerInstanceByProvider(String name, ContainerInstanceInfo ci) {
        if (containerInstancesByProvider.get(name) == null) {
            containerInstancesByProvider.put(name, new ArrayList<ContainerInstanceInfo>());
        }
        List<ContainerInstanceInfo> containerInstancesInfos = containerInstancesByProvider.get(name);
        containerInstancesInfos.add(ci);
        containerInstancesByProvider.put(name, containerInstancesInfos);

    }

    @Override
    public List<ContainerInstanceInfo> getAllContainerInstances() {
        Iterator<Cache.Entry<String, List<ContainerInstanceInfo>>> iterator = containerInstancesByProvider.iterator();
        List<ContainerInstanceInfo> containerInstances = new ArrayList<>();
        while (iterator.hasNext()) {
            containerInstances.addAll(iterator.next().getValue());
        }
        return containerInstances;
    }

    @Override
    public List<ContainerInstanceInfo> getContainerInstanceByProvider(String providerString) {
        return containerInstancesByProvider.get(providerString);
    }

    @Override
    public List<String> getContainerInstancesProviderNames() {
        Iterator<Cache.Entry<String, List<ContainerInstanceInfo>>> iterator = containerInstancesByProvider.iterator();
        List<String> providerNames = new ArrayList<String>();
        while (iterator.hasNext()) {
            providerNames.add(iterator.next().getKey());
        }
        return providerNames;
    }

    @Override
    public List<ContainerProviderInstanceInfo> getContainerProviderInstanceByProvider(String providerString) {
        return containerProviderInstancesByProvider.get(providerString);
    }

    @Override
    public void registerAllContainerInstanceByProvider(String provider, List<ContainerInstanceInfo> allInstances) {
        if (containerInstancesByProvider.get(provider) == null) {
            containerInstancesByProvider.put(provider, new ArrayList<ContainerInstanceInfo>());
        }
        List<ContainerInstanceInfo> containerInstanceInfos = containerInstancesByProvider.get(provider);
        containerInstanceInfos.addAll(allInstances);
        containerInstancesByProvider.put(provider, containerInstanceInfos);
    }

    @Override
    public ContainerInstanceInfo getContainerInstanceById(String id) {
        Iterator<Cache.Entry<String, List<ContainerInstanceInfo>>> iterator = containerInstancesByProvider.iterator();
        while (iterator.hasNext()) {
            for (ContainerInstanceInfo cii : iterator.next().getValue()) {
                if (cii.getId().equals(id)) {
                    return cii;
                }
            }
        }
        return null;
    }

    @Override
    public void removeContainerInstance(String id) {
        Iterator<Cache.Entry<String, List<ContainerInstanceInfo>>> iterator = containerInstancesByProvider.iterator();
        while (iterator.hasNext()) {
            Cache.Entry<String, List<ContainerInstanceInfo>> next = iterator.next();
            for (ContainerInstanceInfo cii : next.getValue()) {
                if (cii.getId().equals(id)) {
                    next.getValue().remove(cii);
                }
            }
        }

    }

}
