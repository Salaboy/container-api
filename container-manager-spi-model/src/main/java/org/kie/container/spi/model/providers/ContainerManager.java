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
 */
public interface ContainerManager {
    public Container createContainer(String name, ContainerConfiguration conf);
    List<Container> getAllContainers();
    ContainerInstance createContainerInstance(Container c) throws Exception;
    List<ContainerInstance> getAllContainerInstances();
}
