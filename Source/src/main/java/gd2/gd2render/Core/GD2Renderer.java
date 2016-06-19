package gd2.gd2render.Core;

import android.app.Application;
import android.content.res.AssetManager;
import gd2.gd2render.Utils.AssetsManager;

public class GD2Renderer extends Application{

   AssetsManager assetsManager;

    /**
     * This is used to get a handle on our asset manager globally.
     *
     * @return - Custom Assets Manager
     */
    public AssetsManager getAssetsManager(){
       return assetsManager;
   }

    /**
     * This method is called once and is used to set our manager to allow storage of assets.
     *
     * @param assetManager - Native AssetManager from Android OS
     */
   public void setAssetsManager(AssetManager assetManager){
       assetsManager = new AssetsManager(assetManager);
   }

}
