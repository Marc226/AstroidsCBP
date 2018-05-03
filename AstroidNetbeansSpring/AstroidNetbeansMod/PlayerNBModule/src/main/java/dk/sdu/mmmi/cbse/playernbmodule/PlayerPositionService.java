/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playernbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPlayerPositionService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Marcg
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPlayerPositionService.class)}
)

public class PlayerPositionService implements IPlayerPositionService {

    PositionPart posPart = null;
    
    public PlayerPositionService(){
        
    }
    
    @Override
    public float getPlayerX() {
        if(posPart == null){
            return 0;
        }
        return posPart.getX();
    }

    @Override
    public float getPlayerY() {
        if(posPart == null){
            return 0;
        }
        return posPart.getY();
    }

    @Override
    public void setPositionPart(PositionPart part) {
        this.posPart = part;
    }
    
}
