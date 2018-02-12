package dk.sdu.mmmi.cbse.splitobject;

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
public class SplitObjectControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity splitObject : world.getEntities(SplitObejct.class)) {
            PositionPart positionPart = splitObject.getPart(PositionPart.class);
            MovingPart movingPart = splitObject.getPart(MovingPart.class);

            movingPart.setUp(true);
            
            
            movingPart.process(gameData, splitObject);
            positionPart.process(gameData, splitObject);

            updateShape(splitObject);
        }
    }

    private void updateShape(Entity entity) {
        entity.clearShape();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        entity.addShapeXpoint((float) (x + Math.cos(radians) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians) * 7));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 1) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 1) * 7));

        entity.addShapeXpoint((float) (x + Math.cos(radians - 2) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 2) * 7));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 3) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 3) * 7));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 4) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 4) * 7));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 5) * 7));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 5) * 7));
        
    }
    
    

}