/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.impl.base;

import org.kie.grid.spi.Participant;

/**
 *
 * @author salaboy
 */
public class BaseParticipantImpl implements Participant{
    protected String id;
    protected String name;

    public BaseParticipantImpl() {
    }

    public BaseParticipantImpl(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    
  
    
}
