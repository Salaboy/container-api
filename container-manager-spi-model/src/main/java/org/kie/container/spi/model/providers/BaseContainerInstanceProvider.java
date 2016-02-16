/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

/**
 *
 * @author salaboy
 */
public abstract class BaseContainerInstanceProvider implements ContainerInstanceProvider{
    
    private String providerName;

    public BaseContainerInstanceProvider(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }
    
    
    
    
    
}
