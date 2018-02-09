/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Marcg
 */
public class AstroidCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity astroid : world.getEntities(Astroid.class)) {
            CollisionPart collisionPart = astroid.getPart(CollisionPart.class);
            for(Entity secondEntity : world.getEntities())
                if(secondEntity.containPart(CollisionPart.class) && !secondEntity.equals(Astroid.class)){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, astroid);
                }
        }
    }
    
}
