/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.splitobjectnbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


/**
 *
 * @author Marcg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = dk.sdu.mmmi.cbse.commonnbmodule.services.IPostEntityProcessingService.class)}
)
public class SplitAstroidCollisionDetection implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Entity splitObject : world.getEntities(SplitAstroid.class)) {
            
            CollisionPart collisionPart = splitObject.getPart(CollisionPart.class);
            LifePart lifePart = splitObject.getPart(LifePart.class);
            
            for(Entity secondEntity : world.getEntities()){
                if(secondEntity.containPart(CollisionPart.class) && !splitObject.getID().equals(secondEntity.getID())){
                    collisionPart.setEntityTwo(secondEntity);
                    collisionPart.process(gameData, splitObject);
                }
            }
            
            lifePart.process(gameData, splitObject);
            if(lifePart.getLife() <= 0){
                world.removeEntity(splitObject);
            }
        }
    }
    
}
