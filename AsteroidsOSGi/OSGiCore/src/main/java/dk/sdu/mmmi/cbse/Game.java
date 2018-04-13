package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.core.managers.PluginTracker;
import dk.sdu.mmmi.cbse.osgicommon.data.Entity;
import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class Game implements ApplicationListener {

    private BundleContext context;
    private PluginTracker pluginTracker;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    public Game(){
        init();
    }

    private void init() {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;
        context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();
        
        pluginTracker = new PluginTracker(context, gameData, world);
        pluginTracker.startPluginTracker();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        update();
        draw();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }
        
//        IEntityProcessingService process;
//        if(processReference() != null){
//            for(ServiceReference<IEntityProcessingService> reference : processReference()){
//                process = (IEntityProcessingService) context.getService(reference);
//                process.process(gameData, world);
//            }
//        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
        
//        IPostEntityProcessingService postProcess;
//        if(processReference() != null){
//            for(ServiceReference<IPostEntityProcessingService> reference : postProcessReference()){
//                postProcess = (IPostEntityProcessingService) context.getService(reference);
//                postProcess.process(gameData, world);
//            }
//        }
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
        pluginTracker.stopPluginTracker();
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }


    
//    public Collection<ServiceReference<IEntityProcessingService>> processReference() {
//        Collection<ServiceReference<IEntityProcessingService>> collection = null;
//        try {
//            collection = this.context.getServiceReferences(IEntityProcessingService.class, null);
//        } catch (InvalidSyntaxException ex) {
//            System.out.println("Service not availlable!");
//        }
//        return collection;
//    }
//    
//    public Collection<ServiceReference<IPostEntityProcessingService>> postProcessReference() {
//        Collection<ServiceReference<IPostEntityProcessingService>> collection = null;
//        try {
//            collection = this.context.getServiceReferences(IPostEntityProcessingService.class, null);
//        } catch (InvalidSyntaxException ex) {
//            System.out.println("Service not availlable!");
//        }
//        return collection;
//    }

}
