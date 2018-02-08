/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Marc
 */
public class DrawablePart implements EntityPart{

    int type = 0;
    
    /**
     * integer is based on how the entity should be drawn
     * 0 = X,Y draw 1 = sprite (add more)... 
     * 
     * @param type 
     */
    public DrawablePart(int type){
        this.type = type;
    }
    
    public int getType(){
        return type;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
