 package dk.sdu.mmmi.cbse.osgiammo;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;

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
