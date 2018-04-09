package dk.sdu.mmmi.cbse.osgiammo;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.LifePart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.events.Event;
import dk.sdu.mmmi.cbse.osgicommon.events.ShootEvent;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmmoPlugin implements IGamePluginService {

    private Entity ammo;
    private boolean active = false;
    private Random random = new Random();
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public AmmoPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        active = true;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (active) {
                    for (Event event : gameData.getEvents(ShootEvent.class)) {

                        ammo = createAmmo(gameData, event.getSource());
                        world.addEntity(ammo);

                        gameData.removeEvent(event);
                    }
                }
            }
        });
    }

    private Entity createAmmo(GameData gameData, Entity entity) {
        MovingPart movingPart = entity.getPart(MovingPart.class);
        PositionPart position = entity.getPart(PositionPart.class);

        float deacceleration = movingPart.getDeceleration();
        float acceleration = 400;
        float maxSpeed = 400;
        int life = 1;
        float expiration = 30;

        Entity splitObject = new Ammo(entity);
        splitObject.setColor(entity.getColor());
        splitObject.add(new MovingPart(deacceleration, acceleration, maxSpeed, 0));
        splitObject.add(new PositionPart(position.getX(), position.getY(), position.getRadians()));
        splitObject.add(new CollisionPart());
        splitObject.add(new LifePart(life, expiration, 1000));

        return splitObject;
    }

    @Override
    public void stop(GameData gameData, World world) {
        active = false;
        // Remove entities
    }

    @Override
    public void create(GameData gameData, World world, Entity entity) {
        ammo = createAmmo(gameData, entity);
        world.addEntity(ammo);
    }

}
