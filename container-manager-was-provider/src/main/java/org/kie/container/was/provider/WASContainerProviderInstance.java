/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import com.ibm.websphere.management.application.AppNotification;
import org.drools.was.util.core.WASContainerManager;
import org.drools.was.util.core.listeners.BaseNotificationListener;
import org.kie.container.spi.model.ContainerInstanceConfiguration;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.providers.base.BaseContainerProviderInstance;
import org.kie.container.spi.model.providers.info.ContainerInstanceInfoImpl;
import org.kie.container.spi.model.providers.info.ContainerProviderInstanceInfo;

/**
 *
 * @author salaboy
 */
public class WASContainerProviderInstance extends BaseContainerProviderInstance {

    private WASContainerManager containerClient;


    public WASContainerProviderInstance(ContainerProviderInstanceInfo cpi, ContainerInstanceConfiguration config) {
        super("WAS Client Provider", "was");
        System.out.println(">>> New WASContainerProviderInstance Instance... " + this.hashCode());
        
        this.config = config;
        this.containerProviderInstanceInfo = cpi;
        String host = cpi.getConfig().getProperties().get("host");
        String port = cpi.getConfig().getProperties().get("port");
        
        containerClient = new WASContainerManager(host, port);
        containerClient.connect();

    }

    @Override
    public ContainerInstanceInfo create() {

        String appName = config.getProperties().get("name");
        String earPath = config.getProperties().get("earPath");
        String target = config.getProperties().get("target");
        containerClient.addNotificationListener(new BaseNotificationListener("Install: " + appName, AppNotification.INSTALL));
        containerClient.deployApp(earPath, appName, target);

        containerInstanceInfo = new ContainerInstanceInfoImpl(appName, appName, config);

        return containerInstanceInfo;
    }

    @Override
    public void start() {
        containerClient.startApp(containerInstanceInfo.getId());
    }

    @Override
    public void stop() {
        containerClient.stopApp(containerInstanceInfo.getId());
    }

    @Override
    public void restart() {
        containerClient.restartApp(containerInstanceInfo.getId());
    }

}
