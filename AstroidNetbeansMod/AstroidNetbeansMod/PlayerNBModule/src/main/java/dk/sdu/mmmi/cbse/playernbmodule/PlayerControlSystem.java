package dk.sdu.mmmi.cbse.playernbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import static dk.sdu.mmmi.cbse.commonnbmodule.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.commonnbmodule.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.commonnbmodule.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.commonnbmodule.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;



/**
 *
 * @author jcs
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)}
)

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            shootingPart.shoot(gameData.getKeys().isDown(SPACE));
            
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);

            updateShape(player);
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

}
