/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgicommon.entityparts;

import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import java.awt.Polygon;
import java.awt.geom.Area;

/**
 *
 * @author Marcg
 */
public class CollisionPart implements EntityPart{

    private boolean collided = false;
    
    private Entity EntityTwo;
    
    public CollisionPart(){
        
    }
    

    
    public void setEntityTwo(Entity entity){
        EntityTwo = entity;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        Polygon polyOne = new Polygon();
        Polygon polyTwo = new Polygon();
        int counter = 0;
        for(Float x : entity.getShapeX()){
            polyOne.addPoint(x.intValue(), entity.getShapeY().get(counter).intValue());
            counter++;
        }
        
        counter = 0;
        for(Float x : EntityTwo.getShapeX()){
            polyTwo.addPoint(x.intValue(), EntityTwo.getShapeY().get(counter).intValue());
            counter++;
        }
        
        if(isWithin(polyOne, polyTwo) == true){
            LifePart life;
            if(entity.containPart(LifePart.class)){
                life = entity.getPart(LifePart.class);
                life.setIsHit(true);
            }
            if (EntityTwo.containPart(LifePart.class)){
                life = EntityTwo.getPart(LifePart.class);
                life.setIsHit(true);
            }
        }
        
    }
    
    public boolean isWithin(Polygon polyOne, Polygon polyTwo){
        boolean collision = false;
        Area areaOne = new Area(polyOne);
        areaOne.intersect(new Area(polyTwo));
        if(!areaOne.isEmpty()){
            collision = true;
        }
        return collision;
    }
    
}
