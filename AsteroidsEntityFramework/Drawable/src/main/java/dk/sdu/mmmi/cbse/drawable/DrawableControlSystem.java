package dk.sdu.mmmi.cbse.drawable;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DrawablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.awt.geom.Area;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author jcs
 */
public class DrawableControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity splitObject : world.getEntities(Drawable.class)) {
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
        DrawablePart draw = entity.getPart(DrawablePart.class);
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        
        Area area = draw.getArea();
        Rectangle rect = area.
        for(){
            
        }
        
        entity.addShapeXpoint();

        
        
    }
    
    

}
