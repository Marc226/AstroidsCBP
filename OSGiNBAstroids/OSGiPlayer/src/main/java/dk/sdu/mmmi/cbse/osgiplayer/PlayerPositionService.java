/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.services.IPlayerPositionService;


public class PlayerPositionService implements IPlayerPositionService {

    PositionPart posPart = null;
    
    public PlayerPositionService(){
        
    }
    
    @Override
    public float getPlayerX() {
        if(posPart == null){
            return 0;
        }
        return posPart.getX();
    }

    @Override
    public float getPlayerY() {
        if(posPart == null){
            return 0;
        }
        return posPart.getY();
    }

    @Override
    public void setPositionPart(PositionPart part) {
        this.posPart = part;
    }
    
}
