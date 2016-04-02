/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.spi;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public interface YellowPages {

    public Service get(String name);

    public void register(String name, Service s);

    public Map<String, String> getAll();

    Collection<Service> getAll(String name);
}
