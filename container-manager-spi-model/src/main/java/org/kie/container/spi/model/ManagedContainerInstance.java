/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model;

/**
 *
 * @author salaboy
 */
public interface ManagedContainerInstance extends ContainerInstance {

    public void deployApplication();

    public void undeployApplication();

    public void startApplication();

    public void stopApplication();
}
