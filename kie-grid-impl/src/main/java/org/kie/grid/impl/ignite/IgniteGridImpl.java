/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.impl.ignite;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteState;
import org.apache.ignite.Ignition;
import org.kie.grid.spi.Grid;
import org.kie.grid.spi.WhitePages;
import org.kie.grid.spi.YellowPages;

/**
 *
 * @author salaboy
 */

@ApplicationScoped
public class IgniteGridImpl implements Grid {

    private Ignite ignite;

    @Inject
    private WhitePages whitePages;
    
    @Inject
    private YellowPages yellowPages;
    
    public IgniteGridImpl() {
        System.out.println(">>> New Instance IgniteGridImpl "+this.hashCode());
    }
    
    
    @Override
    public void bootstrap() {
        IgniteState state = Ignition.state();
        if (!state.equals(IgniteState.STARTED)) {
            ignite = Ignition.start("config/example-ignite.xml");
        }
        
    }

    @Override
    public WhitePages getWhitePages() {
        return whitePages;
    }

    @Override
    public YellowPages getYellowPages() {
        return yellowPages;
    }

    public Ignite getIgnite() {
        return ignite;
    }
    
    
    
    
    

}
