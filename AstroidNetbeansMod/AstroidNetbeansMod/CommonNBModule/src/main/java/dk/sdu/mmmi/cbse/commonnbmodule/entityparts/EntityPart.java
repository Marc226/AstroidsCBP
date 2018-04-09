/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonnbmodule.entityparts;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {
    void process(GameData gameData, Entity entity);
}
