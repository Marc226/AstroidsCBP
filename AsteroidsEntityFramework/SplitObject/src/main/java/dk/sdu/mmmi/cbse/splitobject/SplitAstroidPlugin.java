package dk.sdu.mmmi.cbse.splitobject;

import dk.sdu.mmmi.cbse.commonAstroid.data.Entity;
import dk.sdu.mmmi.cbse.commonAstroid.data.GameData;
import dk.sdu.mmmi.cbse.commonAstroid.data.World;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonAstroid.events.Event;
import dk.sdu.mmmi.cbse.commonAstroid.events.SplitEvent;
import dk.sdu.mmmi.cbse.commonAstroid.services.IGamePluginService;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class SplitAstroidPlugin implements IGamePluginService {

    private Entity splitObject;
    private boolean active = false;
    private Random random = new Random();
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private final int amount = 2;
    
    public SplitAstroidPlugin() {
        
    }

    @Override
    public void start(GameData gameData, World world) {
        active = true;
        executor.execute(()->{
            while(active){
                for(Event event : gameData.getEvents(SplitEvent.class)){
                    for(int i = 1; i <= amount; i++){
                        splitObject = createSplitObject(gameData, world, event.getSource());
                        world.addEntity(splitObject);
                    }
                    gameData.removeEvent(event);
                }
            }
        });
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
        
        
        float radians = setAstroidDirection(gameData, positionPart.getX(), positionPart.getY());
        
        Entity splitObject = new SplitAstroid();
        splitObject.setColor(color);
        splitObject.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        splitObject.add(new PositionPart(positionPart.getX(), positionPart.getY(), radians));
        splitObject.add(new CollisionPart());
        splitObject.add(new LifePart(life, expiration, 1000));
        
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
    
    public float setAstroidDirection(GameData data, float x, float y){
        float radians = 0;
        radians = (float)Math.atan2(random.nextInt(data.getDisplayWidth()) - x, random.nextInt(data.getDisplayHeight()) - y);
            
        return radians;
    }
    
    

}
