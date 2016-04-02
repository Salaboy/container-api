package org.kie.grid.spi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author salaboy
 */
public interface Participant {

    void setId(String id);
    
    String getId();

    String getName();
    
    void setName(String name);
}
