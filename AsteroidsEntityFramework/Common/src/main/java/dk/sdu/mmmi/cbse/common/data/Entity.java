package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private ArrayList<Float> shapeX = new ArrayList<>();
    private ArrayList<Float> shapeY = new ArrayList<>();
    private float radius;
    private int[] color = new int[4];
    private Map<Class, EntityPart> parts;
    
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }
    
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    
    public void setColor(int[] color){
        this.color = color;
    }
    
    public int[] getColor(){
        return this.color;
    }
    
    public void setRadius(float r){
        this.radius = r;
    }
    
    public float getRadius(){
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public ArrayList<Float> getShapeX() {
        return shapeX;
    }

    public void addShapeXpoint(float x) {
        shapeX.add(x);
    }

    public ArrayList<Float> getShapeY() {
        return shapeY;
    }

    public void addShapeYpoint(float y) {
        shapeY.add(y);
    }
    
    public void clearShape(){
        shapeX.clear();
        shapeY.clear();
    }
}
