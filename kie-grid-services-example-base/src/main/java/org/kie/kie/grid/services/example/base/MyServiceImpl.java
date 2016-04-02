/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.kie.grid.services.example.base;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;


/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class MyServiceImpl implements MyService, Service {

    private String id;
    private String name;

    public MyServiceImpl() {
    }

    
    @Override
    public String doSomething(String text) {
        System.out.println("Doing something with text: " + text);
        return "ok " + text;
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

    @Override
    public List<org.kie.grid.spi.Service> getServices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
