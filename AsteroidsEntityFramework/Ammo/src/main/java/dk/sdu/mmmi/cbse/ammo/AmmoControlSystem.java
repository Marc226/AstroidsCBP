package dk.sdu.mmmi.cbse.ammo;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author jcs
 */
public class AmmoControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity ammo : world.getEntities(Ammo.class)) {
            PositionPart positionPart = ammo.getPart(PositionPart.class);
            MovingPart movingPart = ammo.getPart(MovingPart.class);

            movingPart.setUp(true);
            
            
            movingPart.process(gameData, ammo);
            positionPart.process(gameData, ammo);

            updateShape(ammo);
        }
    }

    private void updateShape(Entity entity) {
        entity.clearShape();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        entity.addShapeXpoint((float) (x + Math.cos(radians) * 4));
        entity.addShapeYpoint((float) (y + Math.sin(radians) * 4));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 1)));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 1)));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians)));
        entity.addShapeYpoint((float) (y + Math.sin(radians)));
        
        
    }
    
    

}
