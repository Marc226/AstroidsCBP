/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgienemy;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.LifePart;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;
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
