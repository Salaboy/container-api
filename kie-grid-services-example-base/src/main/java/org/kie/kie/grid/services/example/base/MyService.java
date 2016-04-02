/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.kie.grid.services.example.base;

import org.kie.grid.spi.Service;

/**
 *
 * @author salaboy
 */
public interface MyService extends Service {
    String doSomething(String text);
}
