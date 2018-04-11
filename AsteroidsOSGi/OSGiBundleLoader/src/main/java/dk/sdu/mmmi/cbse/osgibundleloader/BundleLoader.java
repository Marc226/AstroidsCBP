/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgibundleloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Marcg
 */
public class BundleLoader {
    private CopyOnWriteArrayList<File> fileLs = new CopyOnWriteArrayList<>();
    private String path;
    
    public BundleLoader(String path){
        this.path = path;
        checkForBundles();
    }
    
    private void checkForBundles() {
        File folder = new File(path);
        fileLs.clear();
        File[] fileList = folder.listFiles();
        for(int i = 0; i < fileList.length; i++){
            File file = fileList[i];
            if(file.getAbsolutePath().endsWith(".jar")){
                fileLs.add(file);
            }
        }
    }
    
    public CopyOnWriteArrayList<File> getFiles(){
        checkForBundles();
        return fileLs;
    }
    
    
    
}
