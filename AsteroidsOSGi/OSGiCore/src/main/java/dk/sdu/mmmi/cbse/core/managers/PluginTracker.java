/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.core.managers;


import dk.sdu.mmmi.cbse.osgicommon.data.GameData;
import dk.sdu.mmmi.cbse.osgicommon.data.World;
import dk.sdu.mmmi.cbse.osgicommon.services.IGamePluginService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Marcg
 */
public class PluginTracker {
    boolean active;
    BundleContext context;
    GameData gameData;
    World world;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    CopyOnWriteArrayList<IGamePluginService> pluginList = new CopyOnWriteArrayList();
    
    /**
     *  used for tracking new plugins being loaded
     * @param context
     * @param data
     * @param world
     * @param assetManager
     */
    public PluginTracker(BundleContext context, GameData data, World world){
        this.context = context;
        this.gameData = data;
        this.world = world;
    }
    
    //starts any new plugin that have been loaded
    private void loadPlugins(){
        IGamePluginService plugin;
        for(ServiceReference<IGamePluginService> reference : pluginReference()){
            plugin = (IGamePluginService) context.getService(reference);
            if(!pluginList.contains(plugin)){
                plugin.start(gameData, world);//adds the new loaded bundle to gameData for imageloading
                pluginList.add(plugin);
            }
        }
    }
    
    private void removePlugins(){
        for(IGamePluginService plug : pluginList){
            Boolean active = false;
            IGamePluginService plugin;
            for(ServiceReference<IGamePluginService> reference : pluginReference()){
                plugin = (IGamePluginService) context.getService(reference);
                if(plug.equals(plugin)){
                    active = true;
                    break;
                }
            }
            if(active == false){
                plug.stop(gameData, world);
                System.out.println("Plugin removed");
                pluginList.remove(plug);
            }
        }
    }
    
    //returns a service reference for each plugin available in the program
    private Collection<ServiceReference<IGamePluginService>> pluginReference() {
        Collection<ServiceReference<IGamePluginService>> collection = null;
        try {
            collection = this.context.getServiceReferences(IGamePluginService.class, null);
        } catch (InvalidSyntaxException ex) {
            System.out.println("Service not availlable!");
            active = false; //stop thread if service is unavailable
        }
        return collection;
    }
    
    /**
     * start a thread that keeps checking for new plugins every 5 seconds
     */
    public void startPluginTracker(){
        active = true;
        executor.execute(()->{
            while(active){
               try {
                    loadPlugins();
                    removePlugins();
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    System.out.println("Thread failed");
                    System.out.println(ex);
                } 
            }
        });
    }
    
    /**
     * stop any thread looking for plugins and ungets any pluginService.
     */
    public void stopPluginTracker(){
        active = false;
        context.ungetService(context.getServiceReference(IGamePluginService.class.getName()));
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
