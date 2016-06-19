package gd2.gd2render.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;


/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-four-introducing-basic-texturing/
 */

public class TextureBuilder {


    private static Context sContext;

    //
    //

    public TextureBuilder(final Context nContext) {
        sContext = nContext;
    }


    /**
     * Texture creation method that supports generated mipmaps.
     * only call createTexture from within the GL context otherwise glGenTextures will
     * return 0
     * @param texId - the integer drawable resource ID
     * @param mipmapGen - 1- will generate mip maps 0- won't generate mip maps
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
            final Bitmap bmap = BitmapFactory.decodeResource(sContext.getResources(), texId, op);
            op.inScaled = false;
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
     *
     * @param texId - the integer drawable resource ID
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
            final Bitmap bmap = BitmapFactory.decodeResource(sContext.getResources(), texId, op);
            //binding
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texHandle[0]);
            //filtering for when the texture is being displayed on a distant surface
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
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
     *
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

    /**
     * Method for loading textures into Texture2D from bitmaps
     *
     * @param texId - the drawable resource ID
     * @return
     */
    public int loadTexture(int texId) {
        final BitmapFactory.Options op = new BitmapFactory.Options();
        op.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(sContext.getResources(), texId, op);
        int[] textureId = new int[1];
        byte[] buffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 3];

        for (int y = 0; y < bitmap.getHeight(); y++)
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int pixel = bitmap.getPixel(x, y);
                buffer[(y * bitmap.getWidth() + x) * 3 + 0] = (byte) ((pixel >> 16) & 0xFF);
                buffer[(y * bitmap.getWidth() + x) * 3 + 1] = (byte) ((pixel >> 8) & 0xFF);
                buffer[(y * bitmap.getWidth() + x) * 3 + 2] = (byte) ((pixel >> 0) & 0xFF);
            }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 3);
        byteBuffer.put(buffer).position(0);

        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, bitmap.getWidth(), bitmap.getHeight(), 0,
                GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, byteBuffer);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        return textureId[0];
    }

    /**
     * This method creates and returns a cubemap for use in environment mapping,
     * reflections etc.
     *
     * @param frontFace - the image for the front of the cubemap
     * @param topFace   - the image for the top of the cubemap
     * @param leftFace  - the image for the left of the cubemap
     * @param rightFace - the image for the right of the cubemap
     * @param backFace  - the image for the back of the cubemap
     * @param downFace  - the image for the bottom of the cubemap
     * @return
     */
    public int[] createCubeMap(int frontFace, int topFace, int leftFace, int rightFace, int backFace, int downFace) {
        int[] cubeMapID = new int[1];
        GLES20.glGenTextures(1, cubeMapID, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        final BitmapFactory.Options op = new BitmapFactory.Options();
        op.inScaled = false;
        Bitmap bmap1 = BitmapFactory.decodeResource(sContext.getResources(), frontFace, op);
        Bitmap bmap2 = BitmapFactory.decodeResource(sContext.getResources(), topFace, op);
        Bitmap bmap3 = BitmapFactory.decodeResource(sContext.getResources(), leftFace, op);
        Bitmap bmap4 = BitmapFactory.decodeResource(sContext.getResources(), rightFace, op);
        Bitmap bmap5 = BitmapFactory.decodeResource(sContext.getResources(), backFace, op);
        Bitmap bmap6 = BitmapFactory.decodeResource(sContext.getResources(), downFace, op);
        Bitmap[] cubeMap = new Bitmap[]{
                bmap1, bmap2, bmap3,
                bmap4, bmap5, bmap6
        };
        for (int i = 0; i < 6; i++) {
            GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, cubeMap[i], 0);
        }
        //filtering for when the texture is being displayed on a distant surface
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, 0);
        return cubeMapID;
    }

}
