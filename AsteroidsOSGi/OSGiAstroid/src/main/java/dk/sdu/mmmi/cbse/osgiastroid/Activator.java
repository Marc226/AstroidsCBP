package dk.sdu.mmmi.cbse.osgiastroid;

import dk.sdu.mmmi.cbse.osgicommon.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgicommon.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private IGamePluginService plugin = new AstroidPlugin();
    private IEntityProcessingService process = new AstroidControlSystem();
    private IPostEntityProcessingService post = new AstroidCollisionDetection();
    
    public void start(BundleContext context) throws Exception {
        
        //for testing
        //context.registerService(IGamePluginService.class.getName(), plugin, null);
        //context.registerService(IEntityProcessingService.class.getName(), process, null);
        //context.registerService(IPostEntityProcessingService.class.getName(), post, null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
