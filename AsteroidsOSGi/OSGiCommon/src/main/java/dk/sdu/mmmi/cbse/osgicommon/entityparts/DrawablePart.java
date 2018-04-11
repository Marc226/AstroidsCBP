/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgicommon.entityparts;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import java.awt.geom.Area;

/**
 *
 * @author Marc
 */
public class DrawablePart implements EntityPart{

    Area area  = new Area();
    
    /**
     * integer is based on how the entity should be drawn
     * 0 = X,Y draw 1 = sprite (add more)... 
     * 
     * @param type 
     */
    public DrawablePart(){
    }
    
    public void setArea(Area area){
        this.area = area;
    }
    
    public Area getArea(){
        return this.area;
    }
    

    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
