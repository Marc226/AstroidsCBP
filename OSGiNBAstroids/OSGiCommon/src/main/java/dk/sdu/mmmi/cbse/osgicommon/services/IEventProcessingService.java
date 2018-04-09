/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgicommon.services;

import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;

/**
 *
 * @author Marc
 */
public interface IEventProcessingService {
    void process(GameData data, World world, String type);
}
