/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.services;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
//import org.wildfly.swarm.keycloak.Secured;
import org.kie.grid.services.endpoint.api.KieGridService;
import org.kie.grid.services.endpoint.exception.BusinessException;
import org.kie.grid.services.endpoint.exception.HttpStatusExceptionHandler;
import org.kie.grid.services.endpoint.impl.KieGridServiceImpl;

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
        deployment.addModule("sun.jdk");
        deployment.addPackages(true, "org.kie.grid.services");
        deployment.addResource(KieGridService.class);
        deployment.addResource(KieGridServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);

        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
