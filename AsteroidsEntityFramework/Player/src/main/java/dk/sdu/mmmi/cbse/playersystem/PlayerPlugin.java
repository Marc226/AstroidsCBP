package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.commonAstroid.data.Entity;
import dk.sdu.mmmi.cbse.commonAstroid.data.GameData;
import dk.sdu.mmmi.cbse.commonAstroid.data.World;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.CollisionPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonAstroid.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.commonAstroid.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonAstroid.services.IPlayerPositionService;
import dk.sdu.mmmi.cbse.commonAstroid.util.SPILocator;
import java.util.Collection;



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
        playerPosService.setPositionPart(posPart);
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
        Collection<? extends IPlayerPositionService> list = SPILocator.locateAll(IPlayerPositionService.class);
        for(IPlayerPositionService pos : list){
            position = pos;
        }
        return position;
    }

}
