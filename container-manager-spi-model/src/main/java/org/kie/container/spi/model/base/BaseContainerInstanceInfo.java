/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.base;

import org.kie.container.spi.model.ContainerInstanceInfo;

/**
 *
 * @author salaboy
 */
public class BaseContainerInstanceInfo implements ContainerInstanceInfo {
    private String id;
    private String name;
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
