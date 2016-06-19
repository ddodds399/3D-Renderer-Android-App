package gd2.gd2render.Particles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class Particles {
    //Vectors for position and velocity
    public Vector3 position = new Vector3();

    public Vector3 velocity = new Vector3();
    //Bitmap of image to use for particle
    public Bitmap bitmap;
    //Lifespan of particle
    public float lifeSpan;
    //Brightness of particle
    public float brightness;

    public int textureName;
    //resource id to access particle bitmap
    public int resourceId;
    //array of size for binding texture
    private int[] textures = new int[1];

    /**
     * Constructer for particle class
     * @param x1 - x position of particle
     * @param y1 - y position of particle
     * @param z1 - z position of particle
     * @param resource
     */
    public Particles(float x1, float y1, float z1, int resource) {
        position.x = x1;
        position.y = y1;
        position.z = z1;

        resourceId = resource;



    }

    /**
     * Method to bind texture
     * @param gl
     * @param context
     * @return texture name that was binded
     */
    public int loadGLTexture(GL10 gl, Context context) {
        if (context != null && gl != null) {
            //get image resource from input stream
            InputStream is = context.getResources().openRawResource(resourceId);

                bitmap = BitmapFactory.decodeStream(is);
                try {
                    //close input stream
                    is.close();

                } catch (IOException e) {
                }
            }

            gl.glGenTextures(1, textures, 0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);


            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        //set texture name to the only element in the textures array
        textureName = textures[0];

        return textureName;
    }



}
