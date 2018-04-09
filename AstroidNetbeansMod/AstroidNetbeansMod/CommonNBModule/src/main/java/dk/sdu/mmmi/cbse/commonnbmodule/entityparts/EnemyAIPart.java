/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonnbmodule.entityparts;

import dk.sdu.mmmi.cbse.commonnbmodule.data.Entity;
import dk.sdu.mmmi.cbse.commonnbmodule.data.GameData;
import dk.sdu.mmmi.cbse.commonnbmodule.data.World;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Marc
 */
public class EnemyAIPart implements EntityPart{

    private float x;
    private float y;
    private float radians;
    private float rotationSpeed;
    private World world;
    private List<Entity> AstroidList = Collections.synchronizedList(new ArrayList());
    private List<Entity> DangerList = Collections.synchronizedList(new ArrayList());
    
    public EnemyAIPart(World world, float rotationSpeed, float x, float y, float radians){
        this.world = world;
        this.x = x;
        this.y = y;
        this.radians = radians;
        this.rotationSpeed = rotationSpeed;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public float getX(){
        return this.x;
    }
    
    public void setY(float y){
        this.y = y;
    }
    
    public float getY(){
        return this.y;
    }
    
    public void setRadians(float rad){
        this.radians = radians;
    }
    
    public float getRadians(){
        return this.radians;
    }
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        generateAstroidList();
        int detectionZone = 200;
        
        Polygon poly1 = new Polygon();
        poly1.addPoint((int)(x + Math.cos(radians)), (int) (y + Math.sin(radians)));
        poly1.addPoint((int) (x + Math.cos(radians) * detectionZone), (int) (y + Math.sin(radians) * detectionZone));
        poly1.addPoint((int) (x + Math.cos(radians + 2 * 3.1415f / 5) * detectionZone), (int) (y + Math.sin(radians + 2 * 3.1415f / 5) * detectionZone));
        
        
        Polygon poly2 = new Polygon();
        poly2.addPoint((int)(x + Math.cos(radians)), (int) (y + Math.sin(radians)));
        poly2.addPoint((int) (x + Math.cos(radians) * detectionZone), (int) (y + Math.sin(radians) * detectionZone));
        poly2.addPoint((int) (x + Math.cos(radians - 2 * 3.1415f / 5) * detectionZone), (int) (y + Math.sin(radians - 2 * 3.1415f / 5) * detectionZone));
        
        for(Entity astroid : AstroidList){
            int counter = 0;
            Polygon poly3 = new Polygon();
            for(Float x : astroid.getShapeX()){
                poly3.addPoint(x.intValue(), astroid.getShapeY().get(counter).intValue());
                counter++;
            }
            
            //System.out.println(area.isEmpty() + " : " + areaRight.isEmpty() + " : " + areaLeft.isEmpty());
            if(isWithin(poly1, poly3) == true || isWithin(poly2, poly3) == true){
                DangerList.add(astroid);
            }
        }
        if(!DangerList.isEmpty()){
 
            Entity closeEntity = null;
            float lowestDist = 0;
            for(Entity astroid : DangerList){
                if(distanceTo(astroid) <= lowestDist || lowestDist == 0){
                    lowestDist = distanceTo(astroid);
                    closeEntity = astroid;
                }
            }
            
            
            int counter = 0;
            Polygon poly4 = new Polygon();
            for(Float x : closeEntity.getShapeX()){
                poly4.addPoint(x.intValue(), closeEntity.getShapeY().get(counter).intValue());
                counter++;
            }
            
            if(isWithin(poly1, poly4)){
                radians -= rotationSpeed * gameData.getDelta();
            }
            
            if(isWithin(poly2, poly4)){
                radians += rotationSpeed * gameData.getDelta();
            }
            DangerList.clear();
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
    
    public float distanceTo(Entity entityTwo){
        return (float)Math.sqrt((entityTwo.getShapeX().get(0)-x)*(entityTwo.getShapeX().get(0)-x) + (entityTwo.getShapeY().get(0)-y)*(entityTwo.getShapeY().get(0)-y));
    }
    
    private void generateAstroidList(){
        AstroidList.clear();
        for(Entity ent : world.getEntities()){
            if(ent.isAvoidable() == true){
                AstroidList.add(ent);
            }
        }
    }
    
}
