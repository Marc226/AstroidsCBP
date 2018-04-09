package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.LifePart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.osgicommon.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPlayerPositionService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;


public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;
        int life = 3;
        PositionPart posPart = new PositionPart(x, y, radians);
        
        int[] color = new int[]{1, 1, 1, 1};
        
        Entity playerShip = new Player();
        playerShip.setColor(color);
        playerShip.add(new ShootingPart(1000));
        playerShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        playerShip.add(posPart);
        playerShip.add(new CollisionPart());
        playerShip.add(new LifePart(life, true, 5000));
        IPlayerPositionService playerPosService = getPositionService();
        if(playerPosService != null){
            playerPosService.setPositionPart(posPart);   
        }
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

    @Override
    public void create(GameData gameData, World world, Entity entity) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }
    
    private IPlayerPositionService getPositionService() {
        BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference reference = context.getServiceReference(IPlayerPositionService.class);
        IPlayerPositionService playerPosition = (IPlayerPositionService) context.getService(reference);
        return playerPosition;
    }

}
