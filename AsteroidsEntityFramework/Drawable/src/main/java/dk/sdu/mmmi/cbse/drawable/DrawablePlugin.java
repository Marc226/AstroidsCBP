package dk.sdu.mmmi.cbse.drawable;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DrawablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.SplitEvent;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrawablePlugin implements IGamePluginService {

    private Entity splitObject;
    private boolean active = false;
    private Random random = new Random();
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private final int amount = 2;
    
    public DrawablePlugin() {
        
    }

    @Override
    public void start(GameData gameData, World world) {
        
    }

    private Entity createSplitObject(GameData gameData, World world, Entity entity) {
        MovingPart movingPart = entity.getPart(MovingPart.class);
        PositionPart positionPart = entity.getPart(PositionPart.class);
        
        float deacceleration = movingPart.getDeceleration();
        float acceleration = movingPart.getAcceleration();
        float maxSpeed = movingPart.getMaxSpeed();
        float rotationSpeed = movingPart.getRotationSpeed();
        int life = 1;
        float expiration = 30;
        
        int[] color = new int[]{0, 1, 1, 1};
        
        
        float radians = positionPart.getRadians();
        
        Entity splitObject = new Drawable();
        splitObject.setColor(color);
        splitObject.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        splitObject.add(new PositionPart(positionPart.getX(), positionPart.getY(), radians));
        splitObject.add(new DrawablePart());
        
        return splitObject;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        active = false;
    }
    
    @Override
    public void create(GameData gameData, World world, Entity entity){
        splitObject = createSplitObject(gameData, world, entity);
        world.addEntity(splitObject);
    }
    

    
    

}
