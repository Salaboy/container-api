/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.spi;

/**
 *
 * @author salaboy
 */
public interface Grid {

    void bootstrap();

    WhitePages getWhitePages();

    YellowPages getYellowPages();
}
