/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.spi;

import java.util.Map;

/**
 *
 * @author salaboy
 */
public interface WhitePages {
    public Participant get(String name);
    public void register(String name, Participant p);
    public Map<String, Participant> getAll();
}
