/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author Marc
 */
public class SplitEvent extends Event{
    
    public SplitEvent(Entity source) {
        super(source);
    }
    
}
