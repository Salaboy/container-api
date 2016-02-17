/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import java.util.Map;

/**
 *
 * @author salaboy
 */
public interface ContainerProviderConfiguration {

    public Map<String, String> getProperties();

    public void setProperties(Map<String, String> props);
}
