package dk.sdu.mmmi.cbse.osgicommon.services;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
    
    void create(GameData gameData, World world, Entity entity);
}
