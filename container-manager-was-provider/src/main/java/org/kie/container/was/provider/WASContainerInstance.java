/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.base.BaseContainerInstanceInfo;
import org.kie.container.spi.model.providers.ContainerProviderInstance;

/**
 *
 * @author salaboy
 */
public class WASContainerInstance implements ContainerInstance {

    private WASContainerProviderInstance instanceProvider;

    private ContainerInstanceInfo info = new BaseContainerInstanceInfo();

    public WASContainerInstance() {
        System.out.println(">>> New WASContainerInstance Instance... " + this.hashCode());
    }

    @Override
    public void setProvider(ContainerProviderInstance provider) {
        if (provider instanceof WASContainerProviderInstance) {
            instanceProvider = (WASContainerProviderInstance) provider;
        } else {
            throw new IllegalStateException(">>> The provider was not a WAS ContainerInstanceProvider!");
        }
    }

    @Override
    public void start() {
        instanceProvider.getWASClient().startApp(info.getId());

    }

    @Override
    public void stop() {

        instanceProvider.getWASClient().stopApp(info.getId());

    }

    @Override
    public ContainerInstanceInfo getInfo() {
        return info;
    }

    @Override
    public void restart() {

        instanceProvider.getWASClient().restartApp(info.getId());

    }

}
