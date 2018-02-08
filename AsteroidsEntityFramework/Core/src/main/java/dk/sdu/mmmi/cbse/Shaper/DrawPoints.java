/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.Shaper;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import java.util.List;

/**
 *
 * @author Marc
 */
public class DrawPoints implements Draw{

    @Override
    public void draw(Entity entity, ShapeRenderer sr) {
            int[] color = entity.getColor();
            sr.setColor(color[0], color[1], color[2], color[3]);

            sr.begin(ShapeRenderer.ShapeType.Line);

            List<Float> shapex = entity.getShapeX();
            List<Float> shapey = entity.getShapeY();

            for (int i = 0, j = shapex.size() - 1;
                    i < shapex.size();
                    j = i++) {

                sr.line(shapex.get(i), shapey.get(i), shapex.get(j), shapey.get(j));
            }

            sr.end();
    }
    
    
}
