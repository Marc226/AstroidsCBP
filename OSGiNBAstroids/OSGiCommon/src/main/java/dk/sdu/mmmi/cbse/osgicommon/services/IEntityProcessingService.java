package dk.sdu.mmmi.cbse.osgicommon.services;

import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;



public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
