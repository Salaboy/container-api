/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.base.BaseContainer;


/**
 *
 * @author salaboy
 */
public class WASContainer extends BaseContainer {

    public WASContainer(String name, ContainerConfiguration conf) {
        super(name, conf);
        System.out.println(" >>> New WASContainer Instance: "+ this.hashCode());
    }
    
    
}
