/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.impl.ignite;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.services.ServiceDescriptor;
import org.kie.grid.spi.Service;
import org.kie.grid.spi.YellowPages;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class IgniteYellowPagesImpl implements YellowPages {
    
    @Inject
    private IgniteGridImpl instance;
    
    private IgniteServices svcs;

    public IgniteYellowPagesImpl() {
        System.out.println(">>> New Instance IgniteYellowPagesImpl "+this.hashCode());
    }
    
    @PostConstruct
    public void init(){
        svcs = instance.getIgnite().services();
    }

    @Override
    public Service get(String name) {
        return svcs.service(name);
    }
    
    @Override
    public Collection<Service> getAll(String name) {
        return svcs.services(name);
    }

    @Override
    public void register(String name, Service s) {
        svcs.deployNodeSingleton(name, (org.apache.ignite.services.Service)s);
    }

    @Override
    public Map<String, String> getAll() {
        Iterator<ServiceDescriptor> iterator = svcs.serviceDescriptors().iterator();
        Map<String, String> services = new HashMap<String, String>();
        while(iterator.hasNext()){
            // populate kie services
            ServiceDescriptor next = iterator.next();
            services.put(next.name(), next.serviceClass().toString());
        }
        return services;
    }
    
    
    
    
    
}
