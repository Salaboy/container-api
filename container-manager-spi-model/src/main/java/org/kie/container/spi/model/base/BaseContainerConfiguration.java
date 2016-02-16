/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.base;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.kie.container.spi.model.ContainerConfiguration;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class BaseContainerConfiguration implements ContainerConfiguration{

    private Map<String, String> properties;
    
    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Map<String, String> props) {
        this.properties = props;
    }

  
    
    
}
