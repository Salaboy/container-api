/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.base;

import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;

/**
 *
 * @author salaboy
 */
public class BaseContainer implements Container{
    private String name;
    private ContainerConfiguration conf;

    public BaseContainer() {
    }

    public BaseContainer(String name) {
        this.name = name;
    }

    public BaseContainer(String name, ContainerConfiguration conf) {
        this.name = name;
        this.conf = conf;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ContainerConfiguration getConfiguration() {
        return conf;
    }
    
    
    
}
