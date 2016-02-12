/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.docker.provider;

import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.base.BaseContainer;


/**
 *
 * @author salaboy
 */
public class DockerContainer extends BaseContainer {

    public DockerContainer(String name, ContainerConfiguration conf) {
        super(name, conf);
    }
    
    
}
