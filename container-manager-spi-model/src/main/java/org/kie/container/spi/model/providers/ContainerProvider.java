/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import java.util.List;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;

/**
 *
 * @author salaboy 
 * This class provides the definition for a ContainerProvider. The provider will be in
 *  charge of allowing us to create new ContainerInstanceProviders
 */
public interface ContainerProvider {
    
    public Container createContainer(String name, ContainerConfiguration conf);

    public List<Container> getAllContainers();

    public String getProviderName();
    
    public ContainerProviderInstance newInstanceProvider(String instanceProviderName);
    
    public ContainerProviderInstance getInstanceProviderByName(String instanceProviderName);
    
    public List<ContainerProviderInstance> getAllInstanceProviders();

}
