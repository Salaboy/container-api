/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model;

import java.util.Map;

/**
 *
 * @author salaboy
 * This class represent all the configuration needed for creating a container instance
 */
public interface ContainerConfiguration {
    public Map<String, String> getProperties();
    public String getProperty(String name);
    public void setProperty(String name, String value);
}
