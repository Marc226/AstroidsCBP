package dk.sdu.mmmi.cbse.osgisplitobject;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;

public class SplitAstroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity splitObject : world.getEntities(SplitAstroid.class)) {
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
