package dk.sdu.mmmi.cbse.corenbmodule.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.commonAstroid.data.Entity;
import dk.sdu.mmmi.cbse.commonAstroid.data.GameData;
import static dk.sdu.mmmi.cbse.commonAstroid.data.GameKeys.SHIFT;
import dk.sdu.mmmi.cbse.commonAstroid.data.World;
import dk.sdu.mmmi.cbse.commonAstroid.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonAstroid.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonAstroid.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonAstroid.util.SPILocator;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;

    private final GameData gameData = new GameData();
    private ShapeRenderer sr;
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

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
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
            for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
               entityProcessorService.process(gameData, world);
            }

            for(IPostEntityProcessingService entityPostProcessingService: getEntityPostProcessingServices()){
                entityPostProcessingService.process(gameData, world);
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
    
    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }
    
    private Collection<? extends IPostEntityProcessingService> getEntityPostProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
