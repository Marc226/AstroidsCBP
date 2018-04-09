/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playernbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IGamePluginService;
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
