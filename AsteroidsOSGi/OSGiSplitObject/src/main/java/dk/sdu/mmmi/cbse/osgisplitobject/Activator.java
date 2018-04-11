package dk.sdu.mmmi.cbse.osgisplitobject;

import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private IGamePluginService plugin = new SplitAstroidPlugin();
    private IEntityProcessingService process = new SplitAstroidControlSystem();
    private IPostEntityProcessingService post = new SplitAstroidCollisionDetection();
    
    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class.getName(), plugin, null);
        context.registerService(IEntityProcessingService.class.getName(), process, null);
        context.registerService(IPostEntityProcessingService.class.getName(), post, null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
