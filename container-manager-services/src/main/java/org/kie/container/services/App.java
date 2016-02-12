/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.services;





import org.kie.container.services.endpoint.exception.BusinessException;
import org.kie.container.services.endpoint.exception.HttpStatusExceptionHandler;
import org.kie.container.services.endpoint.impl.ContainerManagerServiceImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
//import org.wildfly.swarm.keycloak.Secured;
import org.kie.container.services.endpoint.api.ContainerManagerService;

/**
 *
 * @author salaboy
 */
public class App {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
//        deployment.as(Secured.class);
        deployment.setContextRoot("/api");
        deployment.addPackage("org.drools.runtime.agent.model");
        deployment.addResource(ContainerManagerService.class);
        deployment.addResource(ContainerManagerServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);
        
        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
