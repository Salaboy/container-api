/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers.info;

import org.kie.container.spi.model.ContainerInstanceConfiguration;
import org.kie.container.spi.model.ContainerInstanceInfo;

/**
 *
 * @author salaboy
 */
public class ContainerInstanceInfoImpl implements ContainerInstanceInfo {

    private String id;
    private String name;
    private ContainerInstanceConfiguration config;

    public ContainerInstanceInfoImpl() {
    }

    public ContainerInstanceInfoImpl(String id, String name, ContainerInstanceConfiguration config) {
        this.id = id;
        this.name = name;
        this.config = config;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ContainerInstanceConfiguration getConfig() {
        return config;
    }

}
