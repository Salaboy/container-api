/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.drools.service;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.kie.kie.grid.services.example.base.MyService;
import org.kie.grid.drools.service.endpoint.exception.BusinessException;
import org.kie.grid.drools.service.endpoint.exception.HttpStatusExceptionHandler;
import org.kie.grid.drools.service.endpoint.impl.ExampleServiceImpl;
import org.kie.kie.grid.services.example.base.MyServiceImpl;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.kie.grid.drools.service.endpoint.api.ExampleService;
//import org.wildfly.swarm.keycloak.Secured;

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
        deployment.addPackages(true, "org.kie.grid.drools.service");
        deployment.addPackages(true, "org.drools.compiler");
        deployment.addResource(ExampleService.class);
        deployment.addResource(ExampleServiceImpl.class);
        deployment.addClass(MyService.class);
        deployment.addClass(MyServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);

        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
