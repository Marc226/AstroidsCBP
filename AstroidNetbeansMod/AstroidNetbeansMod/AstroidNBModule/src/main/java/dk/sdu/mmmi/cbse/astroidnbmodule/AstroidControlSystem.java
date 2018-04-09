package dk.sdu.mmmi.cbse.astroidnbmodule;

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
public class AstroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity astroid : world.getEntities(Astroid.class)) {
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
