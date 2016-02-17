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
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.drools.was.util.core.WASContainerManager;
import org.drools.was.util.core.listeners.BaseNotificationListener;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.providers.ContainerProviderConfiguration;
import org.kie.container.spi.model.providers.base.BaseContainerInstanceProvider;

/**
 *
 * @author salaboy
 */
public class WASContainerInstanceProvider extends BaseContainerInstanceProvider {

    private Map<String, ContainerInstance> containerInstances = new HashMap<>();

    private WASContainerManager containerClient;

    @Inject
    private Instance<WASContainerInstance> instance;
    

    public WASContainerInstanceProvider() {
        super("WAS Client Provider");
        System.out.println(">>> WAS Client Provider Created... " + this.hashCode());

    }

    @Override
    public void configure(ContainerProviderConfiguration config) {
        this.config = config;
        String host = config.getProperties().get("host");
        String port = config.getProperties().get("port");
        containerClient = new WASContainerManager(host, port);
        containerClient.connect();
    }

    
    @Override
    public ContainerInstance createInstance(Container c) {
        ContainerInstance ci = instance.get();
        ci.setProvider(this);
        String appName = c.getConfiguration().getProperties().get("name");
        ci.getInfo().setName(appName);
        String earPath = c.getConfiguration().getProperties().get("earPath");
        String target = c.getConfiguration().getProperties().get("target");
        containerClient.addNotificationListener(new BaseNotificationListener("Install: " + appName, AppNotification.INSTALL));
        containerClient.deployApp(earPath, appName, target);

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
        containerClient.undeployApp(containerId);
    }

    public WASContainerManager getWASClient() {
        return containerClient;
    }

    @Override
    public ContainerInstance getInstanceById(String id) {
        return containerInstances.get(id);
    }
}
