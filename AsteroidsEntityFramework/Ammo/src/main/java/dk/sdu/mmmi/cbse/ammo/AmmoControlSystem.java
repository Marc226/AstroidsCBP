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

        for (Entity astroid : world.getEntities(Ammo.class)) {
            PositionPart positionPart = astroid.getPart(PositionPart.class);
            MovingPart movingPart = astroid.getPart(MovingPart.class);

            movingPart.setUp(true);
            
            
            movingPart.process(gameData, astroid);
            positionPart.process(gameData, astroid);

            updateShape(astroid);
        }
    }

    private void updateShape(Entity entity) {
        entity.clearShape();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        entity.addShapeXpoint((float) (x + Math.cos(radians) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians) * 15));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 1) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 1) * 15));

        entity.addShapeXpoint((float) (x + Math.cos(radians - 2) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 2) * 15));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 3) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 3) * 15));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 4) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 4) * 15));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 5) * 15));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 5) * 15));
        
    }
    
    

}
