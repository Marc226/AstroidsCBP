package dk.sdu.mmmi.cbse.astroid;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.playersystem.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AstroidPlugin implements IGamePluginService {

    private Entity astroid;
    private Random random = new Random();
    private List<Entity> astroidList = Collections.synchronizedList(new ArrayList());
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private boolean active = true;
    private int MeteorIncreaseDifficulty = 0;
    private int MeteorNextIncrease = random.nextInt(15);
    
    public AstroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        active = true;
        executor.submit(()->{
            while(active){
                astroid = createAstroid(gameData, world);
                astroidList.add(astroid);
                world.addEntity(astroid);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AstroidPlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }

    private Entity createAstroid(GameData gameData, World world) {
        increaseDifficulty(gameData);
        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 50*gameData.getDifficulty();
        float rotationSpeed = 10;
        float x = random.nextInt(gameData.getDisplayHeight());
        float y = random.nextInt(gameData.getDisplayWidth());
        float radians = setAstroidDirection(world, x, y);
        
        Entity astroid = new Astroid();
        astroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        astroid.add(new PositionPart(x, y, radians));
        
        return astroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        active = false;
        // Remove entities
        for(Entity astroidItem: astroidList){
            world.removeEntity(astroidItem);
        }
    }
    
    public void increaseDifficulty(GameData gameData){
        if(MeteorIncreaseDifficulty >= MeteorNextIncrease){
            gameData.increaseDifficulty();
            MeteorIncreaseDifficulty = 0;
            MeteorNextIncrease = random.nextInt(15);
        }
        MeteorIncreaseDifficulty++;
    }
    
    public float setAstroidDirection(World world, float x, float y){
        float radians = 0;
        for(Entity entity : world.getEntities(Player.class)){
                PositionPart Player = entity.getPart(PositionPart.class);
                radians = (float)Math.atan2(x - Player.getX(), y - Player.getY());
                System.out.println(radians*180/Math.PI);
            }
        return radians;
    }

}
