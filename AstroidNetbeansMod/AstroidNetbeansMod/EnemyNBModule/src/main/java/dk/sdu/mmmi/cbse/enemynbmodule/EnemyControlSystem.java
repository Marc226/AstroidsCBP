package dk.sdu.mmmi.cbse.enemynbmodule;


import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.EnemyAIPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author jcs
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)}
)
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
            shootingPart.shoot(true);
            
            
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            shootingPart.process(gameData, enemy);
            aiPart.process(gameData, enemy);
            
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
        aiPart.setX(part.getX());
        aiPart.setY(part.getY());
    }

}
