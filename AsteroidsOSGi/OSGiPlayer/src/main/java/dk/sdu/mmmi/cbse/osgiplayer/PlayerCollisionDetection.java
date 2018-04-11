/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.LifePart;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;

/**
 *
 * @author Marcg
 */

public class PlayerCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            
            for(Entity secondEntity : world.getEntities()){
                if(secondEntity.containPart(CollisionPart.class) && !secondEntity.getClass().equals(Player.class) && !secondEntity.getSource().equals(player)){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, player);
                }
            }
            
            lifePart.process(gameData, player);
            if(lifePart.getLife() <= 0){
                world.removeEntity(player);
            }
        }
    }
    
}
