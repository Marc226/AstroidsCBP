/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.events.ShootEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc
 */
public class ShootingPart implements EntityPart{
    
    private boolean cooldown = false;
    private boolean shoot = false;
    private int cooldownSeconds;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    
    public ShootingPart(int cooldown){
        this.cooldownSeconds = cooldown;
    }
    
    public void shoot(boolean key){
        this.shoot = key;
    }
    
    public void changeCooldown(int cooldown){
        this.cooldownSeconds = cooldown;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if(shoot == true){
            if(cooldown == false){
               gameData.addEvent(new ShootEvent(entity)); 
               cooldown = true;
               executor.execute(()->{
                   try {
                       Thread.sleep(cooldownSeconds);
                       cooldown = false;
                   } catch (InterruptedException ex) {
                       Logger.getLogger(ShootingPart.class.getName()).log(Level.SEVERE, null, ex);
                   }
               });
            }
        }
    }
    
}
