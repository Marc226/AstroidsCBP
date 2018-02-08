package dk.sdu.mmmi.cbse.astroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.Random;

/**
 *
 * @author jcs
 */
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
        int[] color = new int[]{0, 1, 0, 1};

        entity.addShapeXpoint((float) (x + Math.cos(radians) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians) * 8));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 2) * 4));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 2) * 4));

        entity.addShapeXpoint((float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians + 2) * 3));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 2) * 3));

        entity.addShapeXpoint((float) (x + Math.cos(radians + 3.1415f) * 5));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 3.1415f) * 5));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians + 5) * 6));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 5) * 6));

        entity.addShapeXpoint((float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8));
        
        entity.addShapeXpoint((float) (x + Math.cos(radians - 5) * 6));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 5) * 6));

        entity.setColor(color);
    }
    
    public void SetEnemyDirection(MovingPart part){
        Random random = new Random();
        int Direction = random.nextInt(2);
        
        switch(Direction){
            case 0:
                part.setRight(false);
                part.setLeft(true);
                break;
            case 1:
                part.setLeft(false);
                part.setRight(true);
                break;
            default:
                part.setRight(false);
                part.setLeft(false);
        }
    }

}
