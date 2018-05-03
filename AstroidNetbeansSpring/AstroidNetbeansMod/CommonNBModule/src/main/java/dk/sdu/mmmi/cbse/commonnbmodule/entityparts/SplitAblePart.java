/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonnbmodule.entityparts;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.events.SplitEvent;

/**
 *
 * @author Marc
 */
public class SplitAblePart implements EntityPart{

    public SplitAblePart(){
        
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
            gameData.addEvent(new SplitEvent(entity));
    }
    
}
