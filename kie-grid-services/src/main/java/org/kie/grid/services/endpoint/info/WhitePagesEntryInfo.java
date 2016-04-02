/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.grid.services.endpoint.info;

import javax.xml.bind.annotation.XmlRootElement;
import org.kie.grid.spi.Participant;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class WhitePagesEntryInfo {

    private String name;
    private Participant participant;

    public WhitePagesEntryInfo() {
    }

    public WhitePagesEntryInfo(String name, Participant participant) {
        this.name = name;
        this.participant = participant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

}
