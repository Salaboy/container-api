/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.impl.ignite;

import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import org.kie.grid.impl.base.BaseServiceImpl;

/**
 *
 * @author salaboy
 */
public class IgniteServiceImpl extends BaseServiceImpl implements Service{

    public IgniteServiceImpl() {
    }

    public IgniteServiceImpl(org.kie.grid.spi.Service base) {
        this.id = base.getId();
        this.name = base.getName();
    }

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println(">>>  Cancel Service ...... ");
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
          System.out.println(">>>  Init Service ...... ");
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println(">>>  Execute Service ...... ");
    }


    
}
