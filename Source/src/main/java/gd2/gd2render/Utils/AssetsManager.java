package gd2.gd2render.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class AssetsManager {

    Map <String, byte[]> assets;
    AssetManager androidAssetManager;

    /**
     * Creates a new asset manager which is a hashmap containing a name and a byte array
     *
     * @param assetManager this is the android asset manager which is then build upon to provide more functionality
     */
    public AssetsManager(AssetManager assetManager) {
        assets = new HashMap<String, byte[]>();
        androidAssetManager = assetManager;
    }

    /**
     * To create an asset you pass in the location of the file and the name you want to store against the file
     * This method then breaks it into a byte array and adds it to the hashmap
     *
     * @param locationOfFile this is the location of whatever file you want in the assets folder
     * @param storedName this is the name to use when storing the asset in the asset manager
     */
    public void StoreAsset(String locationOfFile, String storedName) {
        InputStream inputStream = null;
        byte[] byteArray = null;

        try {
            inputStream = androidAssetManager.open(locationOfFile);

            byteArray = new byte[inputStream.available()];

            inputStream.read(byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        assets.put(storedName, byteArray);
    }

    /**
     * This is used to list all the assets that are available
     *
     * @return a list of all the assets currently stored in memory
     */
    public String ListAssets() {
        String assetString = "";
        int i = 0;

        for(String key: assets.keySet()) {
            assetString += "Asset " + i + " " + key;
        }

        return assetString;
    }

    /**
     * This method is to generate a bitmap from a byte array
     * You pass in the image name that is contained in the hashmap and you get a bitmap returned
     *
     * @param imageName the name of the image stored in the asset manager
     * @return the bitmap image created from the byte array
     */
    public Bitmap LoadBitmap(String imageName) {
        Bitmap bitmap = null;

        try {
            byte[] imageByteArray = assets.get(imageName);

            bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        }
        catch (Exception e) {
            Log.d("debug", "There is no bitmap in the asset manager with that name");
            Log.d("debug", e.toString());
        }

        return bitmap;
    }

    /**
     * Creates a string from the byte array stored in the hashmap
     *
     * @param textName the name of the text file stored in the asset manager
     * @return a string of the text that was stored in the byte array
     */
    public String LoadText(String textName) {
        String text = null;

        try {

            byte[] textByteArray = assets.get(textName);

            text = new String(textByteArray);
        }
        catch (Exception e) {
            Log.d("debug", "There is no text in the asset manager with that name");
            Log.d("debug", e.toString());
        }

        return text;
    }


}
