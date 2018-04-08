package dk.sdu.mmmi.cbse.commonAstroid.services;

import dk.sdu.mmmi.cbse.commonAstroid.data.GameData;
import dk.sdu.mmmi.cbse.commonAstroid.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
