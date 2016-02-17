/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import java.util.List;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstance;

/**
 *
 * @author salaboy
 */
public interface ContainerInstanceProvider {

    public ContainerInstance createInstance(Container c) throws Exception;

    public void configure(ContainerProviderConfiguration conf);

    public List<ContainerInstance> getAllInstances();

    public void removeInstance(String id);

    public ContainerInstance getInstanceById(String id);

    public String getName();

    public ContainerProviderConfiguration getConfig();

    public String getProviderName();

    public void setProviderName(String providerName);
}
