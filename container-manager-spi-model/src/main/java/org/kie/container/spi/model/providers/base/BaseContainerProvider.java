/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers.base;

import org.kie.container.spi.model.providers.ContainerProvider;

/**
 *
 * @author salaboy
 */
public abstract class BaseContainerProvider implements ContainerProvider{
    
    private final String providerName;

    public BaseContainerProvider(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }
    
    
    
    
    
}
