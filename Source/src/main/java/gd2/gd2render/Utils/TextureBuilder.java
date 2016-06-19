package gd2.gd2render.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLUtils;


/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-four-introducing-basic-texturing/
 */

public class TextureBuilder {


    private static Context context;

    //only call createTexture from within the GL context otherwise glGenTextures will
    //return 0 and throw an exception

    public TextureBuilder(final Context nContext) {
        context = nContext;
    }


    /**
     * Texture creation method that supports generated mipmaps
     * @param texId
     * @param mipmapGen
     * @return
     */
    public static int createTexture(final int texId, final int mipmapGen) {
        //creating a handle for reference
        final int[] texHandle = new int[1];
        int mipMapLevel = GLES11.GL_FALSE;
        GLES20.glGenTextures(1, texHandle, 0);
        /*
        Checking if the texture was generated incorrectly, if so the program will throw an
        exception. Otherwise it will read in the bitmap and bind it.
         */
        if (texHandle[0] != 0) {
            if (mipmapGen == 1) {
                mipMapLevel = GLES11.GL_TRUE;
            }

            final BitmapFactory.Options op = new BitmapFactory.Options();
            final Bitmap bmap = BitmapFactory.decodeResource(context.getResources(), texId, op);
            //binding
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
            //filtering for when the texture is being displayed on a particularly far or close surface
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            //generation of mipmap images
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES11.GL_GENERATE_MIPMAP, mipMapLevel);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmap, 0);
            bmap.recycle();
        }
        if (texHandle[0] == 0) {
            throw new RuntimeException("Texture Failed to Create, No GL Context!");
        }
        return texHandle[0];
    }

    /**
     * Simpler texture creation method with no mipmapping
     * @param texId
     * @return
     */
    public static int createTexture(final int texId) {
        //creating a handle for reference
        final int[] texHandle = new int[1];
        GLES20.glGenTextures(1, texHandle, 0);
        /*
        Checking if the texture was generated incorrectly, if so the program will throw an
        exception. Otherwise it will read in the bitmap and bind it.
         */
        if (texHandle[0] != 0) {
            final BitmapFactory.Options op = new BitmapFactory.Options();
            op.inScaled = false;
            final Bitmap bmap = BitmapFactory.decodeResource(context.getResources(), texId, op);
            //binding
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
            //filtering for when the texture is being displayed on a distant surface
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmap, 0);
            bmap.recycle();
        }
        if (texHandle[0] == 0) {
            throw new RuntimeException("Texture Failed to Create, No GL Context!");
        }
        return texHandle[0];
    }

    /**
     * Texture creation using bitmap loaded from assetmanager
     * @param bitmap - image file loaded from assetsmanager
     * @return
     */
    public static int createTexture(Bitmap bitmap) {
        //creating a handle for reference
        final int[] texHandle = new int[1];
        GLES20.glGenTextures(1, texHandle, 0);
        /*
        Checking if the texture was generated incorrectly, if so the program will throw an
        exception. Otherwise it will read in the bitmap and bind it.
         */
        if (texHandle[0] != 0) {
            //binding
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
            //filtering for when the texture is being displayed on a distant surface
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }
        if (texHandle[0] == 0) {
            throw new RuntimeException("Texture Failed to Create, No GL Context!");
        }
        return texHandle[0];
    }


}
