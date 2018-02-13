package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.astroid.Astroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EnemyAIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.splitobject.SplitAstroid;
import java.util.Random;

/**
 *
 * @author jcs
 */
public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            ShootingPart shootingPart = enemy.getPart(ShootingPart.class);
            EnemyAIPart aiPart = enemy.getPart(EnemyAIPart.class);
            
            
            SetEnemyDirection(positionPart, movingPart, aiPart, gameData);
            movingPart.setUp(true);
            
            
            
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            shootingPart.process(gameData, enemy);
            aiPart.process(gameData, enemy);
            
            //System.out.println(aiPart.getRadians());
            
            positionPart.setRadians(aiPart.getRadians());

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        entity.clearShape();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        entity.addShapeXpoint((float) (x + Math.cos(radians) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians) * 8));

        entity.addShapeXpoint((float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8));

        entity.addShapeXpoint((float) (x + Math.cos(radians + 3.1415f) * 5));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 3.1415f) * 5));

        entity.addShapeXpoint((float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8));
        entity.addShapeYpoint((float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8));

    }
    
    public void SetEnemyDirection(PositionPart part, MovingPart move, EnemyAIPart aiPart, GameData data){
        aiPart.clearAvoidList();
        aiPart.setX(part.getX());
        aiPart.setY(part.getY());
        aiPart.avoidEntity(Astroid.class);
        aiPart.avoidEntity(SplitAstroid.class);
        
//        Random random = new Random();
//        int Direction = random.nextInt(2);
//        
//        
//        
//        switch(Direction){
//            case 0:
//                move.setRight(false);
//                move.setLeft(true);
//                break;
//            case 1:
//                move.setLeft(false);
//                move.setRight(true);
//                break;
//            default:
//                move.setRight(false);
//                move.setLeft(false);
//        }
    }

}
