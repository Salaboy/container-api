/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import javax.inject.Inject;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.base.BaseContainerInstanceInfo;

/**
 *
 * @author salaboy
 */
public class WASContainerInstance implements ContainerInstance {

    private WASContainerInstanceProvider instanceProvider;

    private ContainerInstanceInfo info = new BaseContainerInstanceInfo();

    @Inject
    public WASContainerInstance(@WASInstanceProvider WASContainerInstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;

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
