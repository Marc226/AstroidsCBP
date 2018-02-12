/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.awt.Polygon;
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
    private List<Entity> AstroidList = Collections.synchronizedList(new ArrayList<>());
    
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
        AstroidList.clear();
        AstroidList.addAll(world.getEntities(Astroid.class));
        rotationSpeed += gameData.getDifficulty();
        Polygon poly = new Polygon();
        poly.addPoint(x, y);
    }
    
    public Area 
    
}
