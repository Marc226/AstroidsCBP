/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroid;

import dk.sdu.mmmi.cbse.commonAstroid.data.Entity;
import dk.sdu.mmmi.cbse.commonAstroid.data.GameData;
import dk.sdu.mmmi.cbse.commonAstroid.data.World;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.SplitAblePart;
import dk.sdu.mmmi.cbse.commonAstroid.services.IPostEntityProcessingService;

/**
 *
 * @author Marcg
 */
public class AstroidCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity astroid : world.getEntities(Astroid.class)) {
            
            CollisionPart collisionPart = astroid.getPart(CollisionPart.class);
            LifePart lifePart = astroid.getPart(LifePart.class);
            SplitAblePart splitter = astroid.getPart(SplitAblePart.class);
            
            for(Entity secondEntity : world.getEntities()){
                if(secondEntity.containPart(CollisionPart.class) && !astroid.getID().equals(secondEntity.getID())){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, astroid);
                }
            }
            
            lifePart.process(gameData, astroid);
            if(lifePart.getLife() <= 0){
                splitter.process(gameData, astroid);
                
                world.removeEntity(astroid);
            }
        }
    }
    
}
