package dk.sdu.mmmi.cbse.osgiastroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.LifePart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.SplitAblePart;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class AstroidPlugin implements IGamePluginService {

    private Entity astroid;
    private Random random = new Random();
    private List<Entity> astroidList = Collections.synchronizedList(new ArrayList());
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private boolean active = true;
    private int MeteorIncreaseDifficulty = 0;
    private int MeteorNextIncrease = random.nextInt(15);
    private IPlayerPositionService playerPosition;

    public AstroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        active = true;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (active) {
                    astroid = createAstroid(gameData, world);
                    astroidList.add(astroid);
                    world.addEntity(astroid);
                    try {
                        Thread.sleep(random.nextInt(10000));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AstroidPlugin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    private Entity createAstroid(GameData gameData, World world) {
        increaseDifficulty(gameData);
        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 50 * gameData.getDifficulty();
        float rotationSpeed = 10;
        float radius = 7;
        int life = 1;
        float expiration = 30;

        int[] color = new int[]{0, 1, 0, 1};

        int[] position = getAstroidStartPosition(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        float radians = setAstroidDirection(world, position[0], position[1]);

        Entity astroid = new Astroid();
        astroid.toggleAiAvoid(true);
        astroid.setRadius(radius);
        astroid.setColor(color);
        astroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        astroid.add(new PositionPart(position[0], position[1], radians));
        astroid.add(new CollisionPart());
        astroid.add(new LifePart(life, expiration, 1000));
        astroid.add(new SplitAblePart());

        return astroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        active = false;
        // Remove entities
        for (Entity astroidItem : astroidList) {
            world.removeEntity(astroidItem);
        }
    }

    public void increaseDifficulty(GameData gameData) {
        if (MeteorIncreaseDifficulty >= MeteorNextIncrease) {
            gameData.increaseDifficulty();
            MeteorIncreaseDifficulty = 0;
            MeteorNextIncrease = random.nextInt(15);
        }
        MeteorIncreaseDifficulty++;
    }

    public float setAstroidDirection(World world, float x, float y) {
        float radians = 0;
        if (playerPosition == null) {
            radians = random.nextInt(4);
        } else {
            radians = (float) Math.atan2(playerPosition.getPlayerX() - x, playerPosition.getPlayerX() - y);
        }

        return radians;
    }

    public int[] getAstroidStartPosition(int width, int height) {
        int[] position = new int[2];
        int side = random.nextInt(4);

        switch (side) {
            case 0:
                position[0] = 0;
                position[1] = random.nextInt(height);
                break;
            case 1:
                position[0] = width;
                position[1] = random.nextInt(height);
                break;
            case 2:
                position[0] = random.nextInt(width);
                position[1] = 0;
                break;
            case 3:
                position[0] = random.nextInt(width);
                position[1] = height;
                break;
            default:
                position[0] = 0;
                position[1] = 0;
        }

        return position;
    }

    @Override
    public void create(GameData gameData, World world, Entity entity) {
        astroid = createAstroid(gameData, world);
        astroidList.add(astroid);
        world.addEntity(astroid);
    }
    
    public void setPlayerPosition(IPlayerPositionService position){
        this.playerPosition = position;
    }
    
    public void removePlayerPosition(IPlayerPositionService position){
        this.playerPosition = null;
    }
    
    

}
