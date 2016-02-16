/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import com.ibm.websphere.management.application.AppNotification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.drools.was.util.core.WASContainerManager;
import org.drools.was.util.core.listeners.BaseNotificationListener;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.providers.BaseContainerInstanceProvider;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
@WASInstanceProvider
public class WASContainerInstanceProvider extends BaseContainerInstanceProvider {

    private Map<String, Container> containers = new HashMap<>();
    private Map<String, ContainerInstance> containerInstances = new HashMap<>();

    WASContainerManager container = new WASContainerManager("10.211.55.3", "8880");
    
    @Inject
    private Instance<WASContainerInstance> instance;

    public WASContainerInstanceProvider() {
        super("was");
        System.out.println(">>> WAS Provider Created... "+this.hashCode());
        
        container.connect();
        
    }

    public WASContainerManager getWASClient() {
        return container;
    }

    @Override
    public ContainerInstance getInstanceById(String id) {
        return containerInstances.get(id);
    }

    @Override
    public Container create(String name, ContainerConfiguration conf) {
        Container m = new WASContainer(name, conf);
        containers.put(name, m);
        return m;
    }

    @Override
    public List<Container> getAll() {
        return new ArrayList<>(containers.values());
    }

    @Override
    public ContainerInstance createInstance(Container c) {
        ContainerInstance ci = instance.get();
        String appName = c.getConfiguration().getProperties().get("name");
        ci.getInfo().setName(appName);
        String earPath = c.getConfiguration().getProperties().get("earPath");
        String target = c.getConfiguration().getProperties().get("target");
        container.addNotificationListener(new BaseNotificationListener( "Install: " + appName, AppNotification.INSTALL));
        container.deployApp(earPath, appName, target);

        ci.getInfo().setId(appName); //use the app name as ID :(
        containerInstances.put(appName, ci);

        return ci;
    }

    @Override
    public List<ContainerInstance> getAllInstances() {
        return new ArrayList<>(containerInstances.values());
    }

    @Override
    public void removeInstance(String containerId) {
        container.undeployApp(containerId);
    }

}
