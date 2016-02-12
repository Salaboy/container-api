/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.docker.provider;

import com.spotify.docker.client.DockerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.ContainerInstanceInfo;
import org.kie.container.spi.model.base.BaseContainerInstanceInfo;



/**
 *
 * @author salaboy
 */
public class DockerContainerInstance implements ContainerInstance{
    @Inject
    private DockerContainerInstanceProvider instanceProvider;
    
    private ContainerInstanceInfo info = new BaseContainerInstanceInfo();
    public DockerContainerInstance() {
        
    }

    @Override
    public void start() {
        try {
            instanceProvider.getDocker().startContainer(info.getId());
        } catch (DockerException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        try {
            instanceProvider.getDocker().stopContainer(info.getId(), 0);
        } catch (DockerException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ContainerInstanceInfo getInfo() {
        return info;
    }

    @Override
    public void restart() {
        try {
            instanceProvider.getDocker().restartContainer(info.getId());
        } catch (DockerException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
