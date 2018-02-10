package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable{
    private final Entity source;
    private final String type;

    public Event(Entity source, String type) {
        this.source = source;
        this.type = type;
    }

    public Entity getSource() {
        return source;
    }
    
    public String getType(){
        return type;
    }
}
