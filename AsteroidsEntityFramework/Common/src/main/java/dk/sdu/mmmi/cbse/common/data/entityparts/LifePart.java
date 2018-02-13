/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {

    private int life;
    private boolean isHit = false;
    private float expiration = 0;
    private boolean invulnerable = true;
    private int invulnerableTime = 2000;
    private boolean player = false;
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public LifePart(int life, float expiration, int invulnerableTime) {
        this.life = life;
        this.expiration = expiration;
        this.invulnerableTime = invulnerableTime;
    }
    
    public LifePart(int life, boolean player){
        this.player = player;
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }  
    
    public void reduceExpiration(float delta){
        this.expiration -= delta;
    }

    
    @Override
    public void process(GameData gameData, Entity entity) {
        reduceExpiration(gameData.getDelta());
        if(invulnerable != true){
            if (expiration <= 0 && player == false){
                System.out.println(entity.getID() + " expired");
                life = 0;
            }

            if(isHit == true){
                life --;
                invulnerable = true;
            }
        } else {
            executor.execute(()->{
                try {
                    Thread.sleep(invulnerableTime);
                    isHit = false;
                    invulnerable = false;
                } catch (InterruptedException ex) {
                    Logger.getLogger(LifePart.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
        }
        
    }
    
}
