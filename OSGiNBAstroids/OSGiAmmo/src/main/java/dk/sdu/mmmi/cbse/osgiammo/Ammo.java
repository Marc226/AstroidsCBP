/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgiammo;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;

/**
 *
 * @author Marcg
 */
public class Ammo extends Entity{
    
    private final Entity source;
    
    public Ammo(Entity source){
        this.source = source;
    }
    
    @Override
    public Entity getSource(){
        return source;
    }
}
