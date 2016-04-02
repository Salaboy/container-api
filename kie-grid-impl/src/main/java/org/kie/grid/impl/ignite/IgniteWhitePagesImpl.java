/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.impl.ignite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.ignite.IgniteCache;
import org.kie.grid.spi.Participant;
import org.kie.grid.spi.WhitePages;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class IgniteWhitePagesImpl implements WhitePages {
    
    @Inject
    private IgniteGridImpl instance;
    
    private IgniteCache<String, Participant> whitePages;
    
    public IgniteWhitePagesImpl() {
        System.out.println(">>> New Instance IgniteWhitePagesImpl "+this.hashCode());
    }
    
    @PostConstruct
    public void init() {
        whitePages = instance.getIgnite().getOrCreateCache("whitePages");
    }
    
    @Override
    public Participant get(String name) {
        return whitePages.get(name);
    }
    
    @Override
    public Map<String, Participant> getAll() {
        Iterator<Cache.Entry<String, Participant>> iterator = whitePages.iterator();
        Map<String, Participant> entries = new HashMap<String, Participant>();
        while (iterator.hasNext()) {
            Cache.Entry<String, Participant> next = iterator.next();
            entries.put(next.getKey(), next.getValue());
        }
        return entries;
    }
    
    @Override
    public void register(String name, Participant p) {
        whitePages.put(name, p);
    }
    
}
