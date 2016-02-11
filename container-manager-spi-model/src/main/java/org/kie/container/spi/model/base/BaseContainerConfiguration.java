/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.base;

import java.util.HashMap;
import java.util.Map;
import org.kie.container.spi.model.ContainerConfiguration;

/**
 *
 * @author salaboy
 */
public class BaseContainerConfiguration implements ContainerConfiguration{
    private Map<String, String> properties;
    
    @Override
    public Map<String, String> getProperties() {
        if( properties == null){
            properties = new HashMap<>();
        }
        return properties;
    }

    @Override
    public String getProperty(String name) {
        if( properties == null){
            properties = new HashMap<>();
        }
        return properties.get(name);
    }

    @Override
    public void setProperty(String name, String value) {
        if( properties == null){
            properties = new HashMap<>();
        }
        properties.put(name, value);
    }
    
    
}
