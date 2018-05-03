package dk.sdu.mmmi.cbse.springenemy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPostEntityProcessingService;



/**
 *
 * @author Marcg
 */

public class EnemyCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            CollisionPart collisionPart = enemy.getPart(CollisionPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            
            for(Entity secondEntity : world.getEntities()){
                if(secondEntity.containPart(CollisionPart.class) && !secondEntity.getClass().equals(Enemy.class) && !secondEntity.getSource().equals(enemy)){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, enemy);
                }
            }
            
            lifePart.process(gameData, enemy);
            if(lifePart.getLife() <= 0){
                world.removeEntity(enemy);
            }
        }
        
    }
    
}
