/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.ammo;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Marcg
 */
public class AmmoCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity ammo : world.getEntities(Ammo.class)) {
            
            CollisionPart collisionPart = ammo.getPart(CollisionPart.class);
            PositionPart position = ammo.getPart(PositionPart.class);
            LifePart lifePart = ammo.getPart(LifePart.class);
            
            
            for(Entity secondEntity : world.getEntities()){
                if(secondEntity.containPart(CollisionPart.class) && !ammo.getID().equals(secondEntity.getID()) && !ammo.getSource().getClass().equals(secondEntity.getClass())){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, ammo);
                }
            }
            
            if(position.getX() == gameData.getDisplayWidth() ||  position.getX() == 0 || position.getY() == gameData.getDisplayHeight() || position.getY() == 0){
                lifePart.setLife(0);
            }
            
            lifePart.process(gameData, ammo);
            if(lifePart.getLife() <= 0){
                world.removeEntity(ammo);
            }
        }
    }
    
}
