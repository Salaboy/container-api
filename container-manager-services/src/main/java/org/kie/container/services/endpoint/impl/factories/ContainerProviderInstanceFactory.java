/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.services.endpoint.impl.factories;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kie.container.docker.provider.DockerContainerProviderInstance;
import org.kie.container.spi.model.ContainerInstanceConfiguration;
import org.kie.container.spi.model.providers.ContainerProviderInstance;
import org.kie.container.spi.model.providers.info.ContainerProviderInfo;
import org.kie.container.spi.model.providers.info.ContainerProviderInstanceInfo;
import org.kie.container.was.provider.WASContainerProviderInstance;

/**
 *
 * @author salaboy
 */
public class ContainerProviderInstanceFactory {

    /*
     * @TODO: refactor this to be smarter
    */
    public static ContainerProviderInstance newContainerProviderInstance(ContainerProviderInstanceInfo cpi, ContainerInstanceConfiguration cic) {
        String providerName = cpi.getProviderName();
        if(providerName.equals("docker")){
            DockerContainerProviderInstance dockerContainerProviderInstance = new DockerContainerProviderInstance(cpi, cic);
            try {
                dockerContainerProviderInstance.create();
            } catch (DockerCertificateException ex) {
                Logger.getLogger(ContainerProviderInstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DockerException ex) {
                Logger.getLogger(ContainerProviderInstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ContainerProviderInstanceFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dockerContainerProviderInstance;
        }else if(providerName.equals("was")){
            WASContainerProviderInstance wasContainerProviderInstance = new WASContainerProviderInstance(cpi, cic);
            wasContainerProviderInstance.create();
            return wasContainerProviderInstance;
        }
        return null;
    }
    
}
