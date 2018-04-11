package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import static dk.sdu.mmmi.cbse.osgicommon.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.osgicommon.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.osgicommon.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.osgicommon.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;



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
