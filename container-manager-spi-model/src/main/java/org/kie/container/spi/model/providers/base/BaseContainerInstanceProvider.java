/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers.base;

import org.kie.container.spi.model.providers.ContainerInstanceProvider;
import org.kie.container.spi.model.providers.ContainerProviderConfiguration;

/**
 *
 * @author salaboy
 */
public abstract class BaseContainerInstanceProvider implements ContainerInstanceProvider {

    protected String name;
    protected String providerName;
    protected ContainerProviderConfiguration config;
    
    public BaseContainerInstanceProvider() {
    }

    public BaseContainerInstanceProvider(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ContainerProviderConfiguration getConfig() {
        return config;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }

    @Override
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    
    
    
    
    

}
