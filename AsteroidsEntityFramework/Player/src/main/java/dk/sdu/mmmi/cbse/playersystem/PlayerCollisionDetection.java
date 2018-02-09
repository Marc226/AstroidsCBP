/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Marcg
 */
public class PlayerCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            CollisionPart collisionPart = player.getPart(CollisionPart.class);
            for(Entity secondEntity : world.getEntities())
                if(secondEntity.containPart(CollisionPart.class)){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, player);
                }
        }
    }
    
}
