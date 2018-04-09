package dk.sdu.mmmi.cbse.osgicore;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import static dk.sdu.mmmi.cbse.osgicommon.data.GameKeys.SHIFT;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.osgicore.managers.GameInputProcessor;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osgi.framework.Bundle;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class Game implements ApplicationListener {


    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();
        
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }
    


    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        if(gameData.getKeys().isDown(SHIFT) == true){
            pause();
        } else {   
            // Update
        }
        
        IEntityProcessingService process;
        if(processReference() != null){
            for(ServiceReference<IEntityProcessingService> reference : processReference()){
                process = (IEntityProcessingService) context.getService(reference);
                process.process(gameData, world);
            }
        }
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
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
    
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    
    public Collection<ServiceReference<IEntityProcessingService>> processReference() {
        Collection<ServiceReference<IEntityProcessingService>> collection = null;
        try {
            collection = this.context.getServiceReferences(IEntityProcessingService.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Service not availlable!");
        }
        return collection;
    }
}
