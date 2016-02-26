/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.was.provider;

import javax.enterprise.context.ApplicationScoped;
import org.kie.container.spi.model.providers.base.BaseContainerProvider;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
@WASProvider
public class WASContainerProvider extends BaseContainerProvider {

    public WASContainerProvider() {
        super("was", "8.5");
        System.out.println(" >>> New WASContainerProvider Instance: " + this.hashCode());
    }

}
