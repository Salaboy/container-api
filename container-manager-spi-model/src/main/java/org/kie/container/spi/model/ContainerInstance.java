/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.spi.model;

/**
 *
 * @author salaboy
 * 
 *  This class represent a Docker Image running or a WAR deployed into a server
 * 
 */
public interface ContainerInstance {
   
   void start();
   void stop();
   ContainerInstanceInfo getInfo();
}
