/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model.providers;

import java.util.List;

/**
 *
 * @author salaboy
 * This class allows u s to get hold of all the registered providers in our system
 */
public interface ContainerInstanceProviderManager {
    public List<ContainerInstanceProviderManager> getProviders();
    public void registerProvider();
}
