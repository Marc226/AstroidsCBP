/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgicommon.services;

import dk.sdu.mmmi.cbse.osgicommon.entityparts.PositionPart;

/**
 *
 * @author Marcg
 */
public interface IPlayerPositionService {
    public void setPositionPart(PositionPart part);
    public float getPlayerX();
    public float getPlayerY();
    
}
