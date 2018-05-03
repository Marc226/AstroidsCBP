package dk.sdu.mmmi.cbse.enemynbmodule;



import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.EnemyAIPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)}
)
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private Random random = new Random();

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        enemy = createEnemyShip(gameData, world);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData, World world) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 200*gameData.getDifficulty();
        float rotationSpeed = 10;
        float x = random.nextInt(gameData.getDisplayWidth());
        float y = random.nextInt(gameData.getDisplayHeight());
        float radians = 3.1415f / 2;
        int life = 10;
        
        int[] color = new int[]{1, 0, 0, 1};
        
        Entity enemyShip = new Enemy();
        enemyShip.setColor(color);
        enemyShip.add(new ShootingPart(5000));
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new CollisionPart());
        enemyShip.add(new LifePart(life, true, 3000));
        enemyShip.add(new EnemyAIPart(world, rotationSpeed, x, y, radians));
        
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

    @Override
    public void create(GameData gameData, World world, Entity entity) {
        enemy = createEnemyShip(gameData, world);
        world.addEntity(enemy);
    }

}
