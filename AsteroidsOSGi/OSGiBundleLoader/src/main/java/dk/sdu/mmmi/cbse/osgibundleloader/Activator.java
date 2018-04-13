package dk.sdu.mmmi.cbse.osgibundleloader;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.wiring.FrameworkWiring;

public class Activator implements BundleActivator {

    private boolean active = false;
    private BundleLoader bundleLoader;
    private BundleContext context;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Map<String, Bundle> loadedBundles = new ConcurrentHashMap();
    
    private String bundlePath = "/Users/Marcg/AsteroidsOSGi"; // change to your own folder and add jar files;

    public void start(BundleContext context) throws Exception {
        this.context = context;
        bundleLoader = new BundleLoader(bundlePath);
        startLoader();
    }

    public void stop(BundleContext context) throws Exception {
        stopLoader();
    }

    private void startLoader() {

        active = true;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (active) {
                    try {
                        uninstallBundles();
                        for (File file : bundleLoader.getFiles()) {
                            if (!loadedBundles.containsKey(file.getAbsolutePath())) {
                                install(file);
                            }
                        }

                        for (Bundle bundle : loadedBundles.values()) {
                            if (bundle.getState() != 0x00000020) {
                                System.out.println("Starting Bundle: " + bundle.getSymbolicName());
                                startBundle(bundle);
                            }
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("BundleLoader Thread failed!");
                    }
                }

            }
        });
    }

    private void stopLoader() {
        active = false;
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("executor failed");
            System.out.println(ex);
        }
    }

    private void install(File file) {
        try {
            Bundle bundle = context.installBundle("file:" + file.getAbsoluteFile().toString());
            loadedBundles.put(file.getAbsolutePath(), bundle);
        } catch (BundleException ex) {
            System.out.println("Failed to load: " + file.getAbsolutePath());
        }
    }

    private void remove(Bundle bundle) {
        System.out.println("Removing: " + bundle.getSymbolicName());
        try {
            bundle.stop();
            bundle.uninstall();
            update();
        } catch (BundleException ex) {
            System.out.println("Could not uninstall bundle");
        }
    }

    private void uninstallBundles() {
        for (String file : loadedBundles.keySet()) {
            if (!bundleExist(file)) {
                remove(loadedBundles.get(file));
                loadedBundles.remove(file);
            }
        }
    }

    private boolean bundleExist(String file) {
        for (File loadedFile : bundleLoader.getFiles()) {
            if (file.equals(loadedFile.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    private void update() {
        Bundle systemBundle = context.getBundle(0);
        FrameworkWiring framework = (FrameworkWiring) systemBundle.adapt(FrameworkWiring.class);
        framework.refreshBundles(null, new FrameworkListener[]{});
    }

    private void startBundle(Bundle bundle) {
        System.out.println("Starting: " + bundle.getSymbolicName());
        try {
            bundle.start();
            update();
        } catch (BundleException ex) {
            System.out.println("Bundle couldn't start:");
            System.out.println(ex);
        }
    }
}
