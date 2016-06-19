package gd2.gd2render.UserInterface;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import gd2.gd2render.Core.MainActivity;
import gd2.gd2render.Core.RendererGL2;
import gd2.gd2render.Primitives.CubeGL2;
import gd2.gd2render.Primitives.TriangleGL2;
import gd2.gd2render.Utils.FieldOfView;
import gd2.gd2render.Utils.Handler;

/**
 * Created by Andy on 02/12/2015.
 */
public class UserInterfaceRenderer extends RendererGL2 {
    //field of view

    public static FieldOfView fov = new FieldOfView(90);
     //Context
    Context mContext;
    //Handler
    private Handler sHandler;

    //Matrix
    private float[] mProjectionMatrix = new float[16];

    private float[] mViewMatrix = new float[16];

    private float[] mMVPMatrix = new float[16];

    //Where to look
    private float eyeX = 0.0f;
    private float eyeY = 0.0f;
    private float eyeZ = -0.5f;

    //Cubes
    CubeGL2 cubeSmall, cubeMedium, cubeLarge;

    //Static variables
    public static float smallCubeX = 0.0f;
    public static float smallCubeY = 0.0f;
    public static float smallCubeZ = -5f;
    public static float mediumCubeX = 5.0f;
    public static float mediumCubeY = 0.0f;
    public static float mediumCubeZ = -5.0f;
    public static float largeCubeX = -5.0f;
    public static float largeCubeY = 0.0f;
    public static float largeCubeZ = -5.0f;


    /**
     * UserInterfaceRenderer Constructor
     */
    public UserInterfaceRenderer(Context context){
        super(context);
        mContext = context;

        cubeSmall = new CubeGL2(0.5f,0.5f,0.5f);
        cubeMedium = new CubeGL2(1.0f,1.0f,1.0f);
        cubeLarge = new CubeGL2(2.0f,2.0f,2.0f);
        tri = new TriangleGL2();

    }
    /**
     *On creation of the surface, the background is cleared the red, the desired shader is created, culling is enabled and the camera is set up.
     */

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        //Set Background Colour to red
        GLES20.glClearColor(1f, 0f, 0f, 1f);
        sHandler = new Handler("simple");
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // Position the eye behind the origin.
        eyeX = 0.0f;
        eyeY = 0.0f;
        eyeZ = -0.5f;

        // Looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set the up vector.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

         @Override
         public void onSurfaceChanged(GL10 glUnused, int width, int height) {
             //set the OpenGL viewport to the same size as the surface
        GLES20.glViewport(0, 0, width, height);
             //Create a new perspective projection matrix
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 100.0f;
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

    }

    /**
     * This method includes everything that is drawn on frame.
     */

    @Override
    public void onDrawFrame (GL10 glUnused){
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        //set the field of view
        fov.changeFieldOfView169(mProjectionMatrix);

        //if static variable colour value = 0
        if(MainActivity.colour_value==0){
            //Clear background colour to red
            GLES20.glClearColor(1.0f,0.0f,0.0f,1.0f);
            // GLES20.del

        }
        //if static variable colour value = 1
        else if(MainActivity.colour_value ==1){
            //Clear Background colour to green
            GLES20.glClearColor(0.0f,1.0f,0.0f,1.0f);

        }
        //if static variable colour value = 2
        else if(MainActivity.colour_value ==2){
            //Clear Background colour to blue
            GLES20.glClearColor(0.0f,0.0f,1.0f,1.0f);

        }
        //if static variable colour value = 3
        else if(MainActivity.colour_value ==3){
            //Clear background colour to black
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        }
        //if static variable colour value = 4
        else if(MainActivity.colour_value == 4){
            //Clear background colour to grey
            GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);

        }


        //if  static variable small size = true
        if(MainActivity.small_size == true){
            GLES20.glUseProgram(sHandler.getmShaderProgramHandle());
            //tri.resetMMatrix();
            //tri.translate(0.0f, 3.0f, -6.0f);
            //tri.rotate(angleInDegrees, 0.0f, 0.0f, 1.0f);
            //tri.draw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
            //reset Matrix
            cubeSmall.resetMMatrix();
            //move Cube
            cubeSmall.translate(smallCubeX, smallCubeY, smallCubeZ);
            //rotate cube
            cubeSmall.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            //draw cube
            cubeSmall.simpleDraw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
            Log.d("test", Boolean.toString(MainActivity.medium_size));
            if(MainActivity.remove_one ==true){
                MainActivity.small_size = false;
                Log.d("test2", Boolean.toString(MainActivity.medium_size));
                //delete smallcube
                GLES20.glDeleteProgram(sHandler.getmShaderProgramHandle());

            }

        }
        if(MainActivity.medium_size == true){
            GLES20.glUseProgram(sHandler.getmShaderProgramHandle());
            cubeMedium.resetMMatrix();
            //move medium cube
            cubeMedium.translate(mediumCubeX, mediumCubeY, mediumCubeZ);
            //rotate medium cube
            cubeMedium.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            //draw medium cube
            cubeMedium.simpleDraw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
            if(MainActivity.remove_two ==true){
                MainActivity.medium_size = false;
                Log.d("test2", Boolean.toString(MainActivity.medium_size));
                //delete medium cube
                GLES20.glDeleteProgram(sHandler.getmShaderProgramHandle());

            }

        }
        if(MainActivity.large_size == true){
            GLES20.glUseProgram(sHandler.getmShaderProgramHandle());
            cubeLarge.resetMMatrix();
            //move large cube
            cubeLarge.translate(largeCubeX, largeCubeY, largeCubeZ);
            //rotate large cube
            cubeLarge.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            //draw large cube
            cubeLarge.simpleDraw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            if(MainActivity.remove_three ==true){
                MainActivity.large_size = false;
                Log.d("test2", Boolean.toString(MainActivity.medium_size));
                //delete large cube
                GLES20.glDeleteProgram(sHandler.getmShaderProgramHandle());

            }

        }

    }

    public void setFov(float angle){
        fov.setFieldOfView(angle);
    }


}