/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.events.ShootEvent;

/**
 *
 * @author Marc
 */
public class ShootingPart implements EntityPart{
    
    private boolean shoot = false;
    
    public ShootingPart(){
        
    }
    
    public void shoot(boolean key){
        this.shoot = key;
    }
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if(shoot == true){
            System.out.println("j");
            gameData.addEvent(new ShootEvent(entity));
        }
    }
    
}
