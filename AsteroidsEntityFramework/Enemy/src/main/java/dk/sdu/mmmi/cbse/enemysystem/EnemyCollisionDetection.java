/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

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
                if(secondEntity.containPart(CollisionPart.class) && !secondEntity.getClass().equals(Enemy.class)){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, enemy);
                }
            }
            
            lifePart.process(gameData, enemy);
            System.out.println(lifePart.getLife());
            if(lifePart.getLife() <= 0){
                world.removeEntity(enemy);
            }
        }
        
    }
    
}
