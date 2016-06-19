package gd2.gd2render.Core;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import gd2.gd2render.Utils.CameraMovement;
import gd2.gd2render.Utils.Handler;
import gd2.gd2render.Lighting.Light;
import gd2.gd2render.Primitives.CubeGL2;
import gd2.gd2render.Primitives.TriangleGL2;
import gd2.gd2render.R;
import gd2.gd2render.Utils.TextureBuilder;

/**
 * Created by Dean on 01/11/2015.
 */
/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-one-getting-started/
 */
public class RendererGL2 implements GLSurfaceView.Renderer {

    /**
     * Test Triangle for new renderer.
     */
    protected TriangleGL2 tri;

    /**
     * Test Cube for render.
     */
    protected CubeGL2 cube;

    /**
     * Test light for render.
     */
    private Light light;

    /**
     * Handler for shader types.
     */
    private Handler sHandler, cHandler;

    /**
     * Store the projection matrix.
     */
    private float[] mProjectionMatrix = new float[16];

    /**
     * Store the view matrix.
     */
    private float[] mViewMatrix = new float[16];

    /**
     * Allocate storage for the final combined matrix.
     */
    private float[] mMVPMatrix = new float[16];

    /**
     * Store mode of shader
     */
    private int shaderMode = 0;

    public float rotX = 0f, rotZ = -0.5f, count = 90;
    private boolean rotating;

    /**
     * Texture builder for texture creation
     */
    private TextureBuilder textureBuilder;

    /**
     * Handle for tile texture
     */
    private int tileTexture;

    private CameraMovement cam1 = new CameraMovement(1, 1, 1);
    private boolean camRot = false;

    /**
     * Initialize the model data and texture builder
     *
     * @param actContext - Application context to generate textures.
     */
    public RendererGL2(final Context actContext) {

        textureBuilder = new TextureBuilder(actContext);
        tri = new TriangleGL2();
        cube = new CubeGL2();
        light = new Light();
    }

    //On creation of the surface the desired shader is created, culling is enabled and the camera is set up.
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        if (shaderMode == 0) {
            // Set the background clear color to gray.
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
            sHandler = new Handler("simple");
        } else if (shaderMode == 1) {
            // Set the background clear color to black.
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            cHandler = new Handler("perVertex");
        } else if (shaderMode == 2 || shaderMode == 4) {
            // Set the background clear color to black.
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            cHandler = new Handler("perPixel");
        } else if (shaderMode == 3) {
            // Set the background clear color to grey.
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            cHandler = new Handler("texture");
        }


        // Use culling to remove back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = -0.5f;

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

        //Create handle for new texture
        tileTexture = textureBuilder.createTexture(R.drawable.bumpmap);

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    //This method includes everything that is drawn on frame.
    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);


        if (shaderMode == 0) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(sHandler.getmShaderProgramHandle());

            cube.resetMMatrix();
            cube.translate(0.0f, 0.0f, -5.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.simpleDraw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            tri.resetMMatrix();
            tri.translate(0.0f, -1.0f, -2.0f);
            tri.rotate(40.0f, 1.0f, 0.0f, 0.0f);
            tri.draw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            tri.resetMMatrix();
            tri.translate(0.0f, 1.5f, -3.0f);
            tri.rotate(angleInDegrees, 0.0f, 0.0f, 1.0f);
            tri.draw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
        }

        if (shaderMode == 1 || shaderMode == 2) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -5.0f);
            light.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            light.translate(0.0f, 0.0f, 2.0f);
            light.multiplyMV(mViewMatrix);
            cube.resetMMatrix();
            cube.translate(-4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(0.0f, 0.0f, -5.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());

            // Draw a point to indicate the light.
            GLES20.glUseProgram(cHandler.getmPointProgramHandle());
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            if (rotating || count == 90) {
                if (count >= 360) {
                    count = 0;
                } else {
                    count++;
                }
                rotX = (float) (0 + 7 * Math.cos(2 * Math.PI * count / 360));
                rotZ = (float) (-5 + 7 * Math.sin(2 * Math.PI * count / 360));

                Matrix.setLookAtM(mViewMatrix, 0, rotX, 0, rotZ, 0, 0.5f, -5, 0, 1, 0);

                //Matrix.setLookAtM(mViewMatrix, 0, cameraEX1, cameraEY1, cameraEZ1, cameraX1, cameraY1, cameraZ1, 0, 1, 0);
            }
        }
        if (shaderMode == 3) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            // Set the active texture unit to texture unit 0.
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            // Bind the texture to this unit.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tileTexture);
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 0);
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -5.0f);
            light.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            light.translate(0.0f, 0.0f, 2.0f);
            light.multiplyMV(mViewMatrix);
            cube.resetMMatrix();
            cube.translate(-4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            cube.drawTexturedCube(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.drawTexturedCube(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(0.0f, 0.0f, -5.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.drawTexturedCube(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());

            // Draw a point to indicate the light.
            GLES20.glUseProgram(cHandler.getmPointProgramHandle());
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            if (rotating || count == 90) {
                if (count >= 360) {
                    count = 0;
                } else {
                    count++;
                }
                rotX = (float) (0 + 7 * Math.cos(2 * Math.PI * count / 360));
                rotZ = (float) (-5 + 7 * Math.sin(2 * Math.PI * count / 360));

                Matrix.setLookAtM(mViewMatrix, 0, rotX, 0, rotZ, 0, 0.5f, -5, 0, 1, 0);

            }
        }
        if (shaderMode == 4) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -5.0f);
            light.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            light.translate(0.0f, 0.0f, 2.0f);
            light.multiplyMV(mViewMatrix);
            cube.resetMMatrix();
            cube.translate(-4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(4.0f, 0.0f, -7.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(0.0f, 0.0f, -5.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());

            // Draw a point to indicate the light.
            GLES20.glUseProgram(cHandler.getmPointProgramHandle());
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            Matrix.translateM(mViewMatrix, 0, cam1.getX(), cam1.getY(), cam1.getZ());

            if (camRot) {
                Matrix.rotateM(mViewMatrix, 0, 45.0f, cam1.getXR(), cam1.getYR(), cam1.getZR());
                camRot = false;
            }

            cam1.reset();


        }
    }

    //Used when demo is chosen in the list view to set the render mode.
    public void setShaderMode(int sMode) {
        shaderMode = sMode;
    }

    public void setRotating(boolean isRotating) {
        rotating = isRotating;
    }


    public CameraMovement getCam() {
        return cam1;
    }

    public void setCamBol() {
        camRot = true;
    }


}
