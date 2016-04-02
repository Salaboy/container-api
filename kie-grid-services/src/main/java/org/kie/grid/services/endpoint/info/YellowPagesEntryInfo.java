/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.services.endpoint.info;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class YellowPagesEntryInfo {

    private String key;
    private String service;

    public YellowPagesEntryInfo() {
    }

    public YellowPagesEntryInfo(String key, String service) {
        this.key = key;
        this.service = service;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
