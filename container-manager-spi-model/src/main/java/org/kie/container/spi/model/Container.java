package org.kie.container.spi.model;

/**
 *
 * @author salaboy
 * 
 * This class represents a Container definition. 
 * For Docker it represents a docker image name and configuration
 * For a WAR application it represents the application itself and its information
 */
public interface Container {

    public String getName();

    public ContainerConfiguration getConfiguration();
    
}
