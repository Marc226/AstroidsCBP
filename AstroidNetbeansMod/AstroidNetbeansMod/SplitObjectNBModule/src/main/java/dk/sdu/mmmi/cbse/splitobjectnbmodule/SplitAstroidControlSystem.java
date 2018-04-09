package dk.sdu.mmmi.cbse.splitobjectnbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;



/**
 *
 * @author jcs
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)}
)
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
