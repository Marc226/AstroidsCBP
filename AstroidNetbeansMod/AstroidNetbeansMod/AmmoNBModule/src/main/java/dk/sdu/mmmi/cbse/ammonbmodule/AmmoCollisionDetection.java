/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.ammonbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;



/**
 *
 * @author Marcg
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)}
)
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
