/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import java.util.List;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.ContainerInstance;

/**
 *
 * @author salaboy
 *  This class provides the basic for a Provider to create and destroy instances 
 */
public interface ContainerInstanceProvider {
    public Container create(String name, ContainerConfiguration conf);
    public List<Container> getAll();
    public ContainerInstance createInstance(Container c) throws Exception;
    public List<ContainerInstance> getAllInstances();
    public void removeInstance(String id);
    public ContainerInstance getInstanceById(String id);
    public String getProviderName();
   
}
