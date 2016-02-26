/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import org.kie.container.spi.model.ContainerInstanceConfiguration;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.providers.info.ContainerProviderInstanceInfo;

/**
 *
 * @author salaboy
 */
public interface ContainerProviderInstance {

    public String getName();

    public ContainerInstanceConfiguration getConfig();
    
    public ContainerInstanceInfo getContainerInstanceInfo();
    
    public ContainerProviderInstanceInfo getContainerProviderInstanceInfo();

    public String getProviderName();

    public void setProviderName(String providerName);

    public ContainerInstanceInfo create() throws Exception;

    public void start();

    public void stop();

    public void restart();
}
