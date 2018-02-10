/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.events.Event;

/**
 *
 * @author Marc
 */
public class SplitAblePart implements EntityPart{
    Boolean dead = false;

    public SplitAblePart(){
        
    }
    
    public void toggleDead(){
        dead = true;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if(dead == true && gameData.getSplitAble() == true){
            gameData.addEvent(new Event(entity, "split"));
        }
    }
    
}
