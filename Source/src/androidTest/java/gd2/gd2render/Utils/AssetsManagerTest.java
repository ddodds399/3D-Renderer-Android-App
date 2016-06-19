package gd2.gd2render.Utils;

import android.content.Intent;
import android.content.res.AssetManager;
import android.test.mock.MockApplication;

import junit.framework.TestCase;

import gd2.gd2render.Core.GD2Renderer;
import gd2.gd2render.Core.MainActivity;

/**
 * Created by Dean on 25/11/2015.
 */
public class AssetsManagerTest extends TestCase {

    AssetsManager myAssetsManager;
    AssetManager nativeAssets;

    public void setUp() throws Exception {
        super.setUp();
        myAssetsManager = new AssetsManager(nativeAssets);
    }

    public void tearDown() throws Exception {
        myAssetsManager = null;
    }

    public void testStoreAsset() throws Exception {

    }

    public void testListAssets() throws Exception {

    }

    public void testLoadBitmap() throws Exception {

    }

    public void testLoadText() throws Exception {

    }
}