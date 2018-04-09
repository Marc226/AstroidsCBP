package dk.sdu.mmmi.cbse.commonnbmodule.services;

import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;



public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
