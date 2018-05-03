package dk.sdu.mmmi.cbse.playernbmodule;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonnbmodule.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonnbmodule.services.IPlayerPositionService;
import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)}
)

public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    private final Lookup lookup = Lookup.getDefault();

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
        IPlayerPositionService position = null;
        Collection<? extends IPlayerPositionService> list = lookup.lookupAll(IPlayerPositionService.class);
        for(IPlayerPositionService pos : list){
            position = pos;
        }
        return position;
    }

}
