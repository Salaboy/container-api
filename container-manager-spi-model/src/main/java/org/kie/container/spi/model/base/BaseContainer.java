/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.base;

import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstanceConfiguration;

/**
 *
 * @author salaboy
 */
public class BaseContainer implements Container{
    private String name;
    private ContainerInstanceConfiguration conf;

    public BaseContainer() {
    }

    public BaseContainer(String name) {
        this.name = name;
    }

    public BaseContainer(String name, ContainerInstanceConfiguration conf) {
        this.name = name;
        this.conf = conf;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ContainerInstanceConfiguration getConfiguration() {
        return conf;
    }
    
    
    
}
