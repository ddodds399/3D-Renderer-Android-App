package gd2.gd2render.Core;

import android.content.Context;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Message;
import android.os.SystemClock;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import gd2.gd2render.OFFImporting.OFFModel;
import gd2.gd2render.Particles.ParticleSystem;
import gd2.gd2render.Primitives.Quad;
import gd2.gd2render.Utils.AssetsManager;
import gd2.gd2render.Utils.CameraMovement;
import gd2.gd2render.Utils.FPSCounter;
import gd2.gd2render.Utils.FieldOfView;
import gd2.gd2render.Utils.Handler;
import gd2.gd2render.Lighting.Light;
import gd2.gd2render.Primitives.CubeGL2;
import gd2.gd2render.Primitives.TriangleGL2;
import gd2.gd2render.R;
import gd2.gd2render.Utils.TextureBuilder;
import gd2.gd2render.OBJImporting.*;

/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-one-getting-started/
 */
public class RendererGL2 implements GLSurfaceView.Renderer {

    private final int NUM_PARTICLES = 1000;
    private final int PARTICLE_SIZE = 7;
    private final float[] mParticleData = new float[NUM_PARTICLES * PARTICLE_SIZE];
    private FloatBuffer mParticles;
    private float mTime;
    private long mLastTime;
    private Context mContext;
    private int mTextureId, updatedId;
    private int i = 0;
    /**
     * Triangle instance
     */
    private OBJParser parser, p2, p3, p4;
    private TDModel tModel, bModel, dModel, wModel;

    private ParticleSystem particle = new ParticleSystem();

    private AssetsManager assetsManager;

    /**
     * Test Triangle for new renderer.
     */
    protected TriangleGL2 tri;

    /**
     * Quads for use in skybox and decals
     */
    protected Quad quad, sb, decal;

    /**
     * Test Model for new renderer.
     */
    protected OFFModel model;

    /**
     * Field of view angle
     */

    protected FieldOfView fov = new FieldOfView(90);

    /**
     * Test Cube for render.
     */
    protected CubeGL2 cube, skyBox;

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

    private float eyeX = 0.0f;
    private float eyeY = 0.0f;
    private float eyeZ = -0.5f;

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
    public int bumpTexture;
    /**
     * Texture IDs for each of the skybox
     * components
     */
    private int skyBoxFront;
    private int skyBoxLeft;
    private int skyBoxRight;
    private int skyBoxBack;
    private int skyBoxTop;
    private int skyBoxBottom;
    private int bulletDecal;
    private int targetTexture;


    private CameraMovement cam1 = new CameraMovement(1, 1, 1);
    private boolean camRot = false;

    private int decalCount = 0;

    android.os.Handler statsHandler;

    /**
     * Initialize the model data and texture builder
     *
     * @param actContext - Application context to generate textures.
     */
    public RendererGL2(final Context actContext) {

        textureBuilder = new TextureBuilder(actContext);
        try {
            model = new OFFModel(actContext.getAssets().open("models/dragon.off"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tri = new TriangleGL2();
        cube = new CubeGL2();
        light = new Light();
        quad = new Quad();

        parser = new OBJParser(actContext);
        p2 = new OBJParser(actContext);
        p3 = new OBJParser(actContext);
        p4 = new OBJParser(actContext);

        //initialising the sides of the skybox
        sb = new Quad(60, 60);

        decal = new Quad(0.07, 0.07);

        GD2Renderer gd2Renderer = (GD2Renderer) actContext;
        assetsManager = gd2Renderer.getAssetsManager();

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
        } else if (shaderMode == 2 || shaderMode == 4 || shaderMode == 10) {
            // Set the background clear color to black.
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            cHandler = new Handler("perPixel");
        } else if (shaderMode == 3 || shaderMode == 5 || shaderMode == 8) {
            // Set the background clear color to grey.
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            cHandler = new Handler("texture");
        }

        if (shaderMode != 6) {
            // Use culling to remove back faces.


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
        if (shaderMode == 7) {
            // Position the eye behind the origin.
            eyeX = 0.0f;
            eyeY = 0.0f;
            eyeZ = 40.0f;

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
        tileTexture = textureBuilder.createTexture(assetsManager.LoadBitmap("tiles"));
        bumpTexture = textureBuilder.createTexture(assetsManager.LoadBitmap("bumpmap"));
        targetTexture = textureBuilder.createTexture(assetsManager.LoadBitmap("target"));

        //initialising skybox textures
        skyBoxFront = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxfront"));
        skyBoxBack = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxback"));
        skyBoxBottom = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxbottom"));
        skyBoxTop = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxtop"));
        skyBoxLeft = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxleft"));
        skyBoxRight = textureBuilder.createTexture(assetsManager.LoadBitmap("skyboxright"));

        bulletDecal = textureBuilder.createTexture(assetsManager.LoadBitmap("bullet"));


        if (shaderMode == 6) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClearDepthf(1.0f);
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            GLES20.glDisable(GLES20.GL_DITHER);

            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);

            // Load particle texture
            mTextureId = textureBuilder.loadTexture(R.drawable.smoke);
            cHandler = new Handler("particle");

            Matrix.setLookAtM(mViewMatrix, 0, 0, -10, 15, 0, 0, 1, 0, 1, 0);
        }
        if (shaderMode == 7) {
            // Set the background clear color to grey.
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            eyeZ = -20.0f;
            cHandler = new Handler("skyBox");

        }
        if (shaderMode == 8) {
            tModel = parser.parseOBJ("models/bear-obj.obj", 0.5);
            bModel = p2.parseOBJ("models/boar-obj.obj", 0.5);
            dModel = p3.parseOBJ("models/deer-obj.obj", 0.5);
            wModel = p4.parseOBJ("models/wolf-obj.obj", 0.5);
        }

        if (shaderMode == 9) {
            // Set the background clear color to grey.
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            cHandler = new Handler("skyBox");

        }
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
        final float far = 200.0f;
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

            quad.resetMMatrix();
            quad.translate(-5.0f, 0.0f, -5.0f);
            quad.draw(sHandler.getmPositionHandle(), sHandler.getmColourHandle(), sHandler.getmMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            tri.resetMMatrix();
            tri.translate(0.0f, 3.0f, -6.0f);
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
            quad.resetMMatrix();
            quad.translate(0.0f, -2.0f, -2.0f);
            quad.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());


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

                Matrix.setLookAtM(mViewMatrix, 0, rotX, 0, rotZ, 0, 0.5f, -5, 0, 1, 0);

            }
            FPSCounter.logFrame();
            Message m = new Message();
            m.what = 1;
            statsHandler = MainActivity.getHandler();
            i++;
            if (i > 59) {
                i = 0;
            }
            if (i == 0) {
                statsHandler.sendMessage(m);
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
        if (shaderMode == 5) {
            //Change Field fo View Method
            fov.changeFieldOfView169(mProjectionMatrix);
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            // Set the active texture unit to texture unit 0.
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            // Bind the texture to this unit.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bumpTexture);
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
        if (shaderMode == 6) {
            //Matrix.setLookAtM(mViewMatrix, 0, 0.0f, -10.0f, 15.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
            GLES20.glUniform1i(cHandler.getmSamplerLocHandle(), 0);
            particle.update20(cHandler.getmCenterPositionLocHandle(), cHandler.getmColorLocHandle());
            particle.draw20(cHandler.getmLifetimeLocHandle(), cHandler.getmEndPositionLocHandle(), cHandler.getmStartPositionLocHandle(), cHandler.getmSamplerLocHandle());

        }
        if (shaderMode == 7) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -5.0f);
            light.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            light.translate(0.0f, 0.0f, 2.0f);
            light.multiplyMV(mViewMatrix);


            /**
             * Initialising and Binding each skybox face to texture
             * locations
             */
            //Front face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxFront);
            //Back face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxBack);
            //Top face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxTop);
            //Bottom face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE4);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxBottom);
            //Right face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE5);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxRight);
            //Left face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE6);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, skyBoxLeft);
            GLES20.glDisable(GLES20.GL_CULL_FACE);

            //draw front face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 1);
            sb.resetMMatrix();
            sb.translate(0.0f, 0.0f, 0.0f);
            sb.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //draw back face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 2);
            sb.resetMMatrix();
            sb.translate(0.0f, 0.0f, 120.0f);
            sb.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            sb.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //draw top face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 3);
            sb.resetMMatrix();
            sb.translate(0.0f, 60.0f, 60.0f);
            sb.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            sb.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //draw bottom face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 4);
            sb.resetMMatrix();
            sb.translate(0.0f, -60.0f, 60.0f);
            sb.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            sb.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            sb.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //draw Right face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 5);
            sb.resetMMatrix();
            sb.translate(60.0f, .0f, 60.0f);
            sb.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            sb.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            sb.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //draw Left face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 6);
            sb.resetMMatrix();
            sb.translate(-60.0f, 0.0f, 60.0f);
            sb.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            sb.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            sb.drawSkyQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            //reenable back face culling
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            // Draw a point to indicate the light.
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
            Matrix.translateM(mViewMatrix, 0, cam1.getX(), cam1.getY(), cam1.getZ());
            if (camRot) {
                Matrix.rotateM(mViewMatrix, 0, 45.0f, cam1.getXR(), cam1.getYR(), cam1.getZR());
                camRot = false;
            }
            cam1.reset();
        }
        if (shaderMode == 8) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            // Bind the texture to this unit.
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tileTexture);
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 0);
            cube.resetMMatrix();
            cube.translate(0.0f, 0.0f, 100.0f);
            cube.rotate(angleInDegrees, 1.0f, 1.0f, 0.0f);
            cube.drawTexturedCube(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());

            tModel.resetMMatrix();
            tModel.translate(7.0f, 0.0f, -15.0f);
            tModel.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            tModel.drawTextured(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            bModel.resetMMatrix();
            bModel.translate(-7.0f, -6.0f, -15.0f);
            bModel.rotate(-angleInDegrees, 0.0f, 1.0f, 0.0f);
            bModel.drawTextured(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            dModel.resetMMatrix();
            dModel.translate(5.0f, -10.0f, -15.0f);
            dModel.rotate(-angleInDegrees, 0.0f, 1.0f, 0.0f);
            dModel.drawTextured(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            wModel.resetMMatrix();
            wModel.translate(-5.0f, 6.0f, -15.0f);
            wModel.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            wModel.drawTextured(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            // Draw a point to indicate the light.
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -5.0f);
            light.multiplyMV(mViewMatrix);
            GLES20.glUseProgram(cHandler.getmPointProgramHandle());
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);
        }


        if (shaderMode == 9) {
            // Tell OpenGL to use this program when rendering.
            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, targetTexture);
            //Front face
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, bulletDecal);
            GLES20.glDisable(GLES20.GL_CULL_FACE);

            //draw front face
            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 0);
            quad.resetMMatrix();
            quad.translate(0.0f, 0.0f, -2.0f);
            quad.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            quad.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());

            GLES20.glUniform1i(cHandler.getmTextureUniformHandle(), 1);

            if (decalCount > 0) {
                if (decalCount >= 1) {
                    decal.resetMMatrix();
                    decal.translate(0.6f, 0.6f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 2) {
                    decal.resetMMatrix();
                    decal.translate(0.48f, 0.62f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 3) {
                    decal.resetMMatrix();
                    decal.translate(0.64f, 0.5f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 4) {
                    decal.resetMMatrix();
                    decal.translate(-0.6f, -0.6f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 5) {
                    decal.resetMMatrix();
                    decal.translate(0.6f, -0.6f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 6) {
                    decal.resetMMatrix();
                    decal.translate(-0.64f, 0.5f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }
                if (decalCount >= 7) {
                    decal.resetMMatrix();
                    decal.translate(0.0f, 0.0f, -1.6f);
                    decal.drawTexturedQuad(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), cHandler.getmTextureCoordinateHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
                }

            }
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }

        if (shaderMode == 10) {
            GLES20.glUseProgram(cHandler.getmPointProgramHandle());
            light.resetMMatrix();
            light.translate(0.0f, 0.0f, -3.0f);
            light.multiplyMV(mViewMatrix);
            // Draw a point to indicate the light.
            light.draw(cHandler.getmPointPositionHandle(), cHandler.getmPointMVPMatrixHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix);

            GLES20.glUseProgram(cHandler.getmShaderProgramHandle());
            cube.resetMMatrix();
            cube.translate(-2.0f, 0.0f, -4.0f);
            cube.rotate(angleInDegrees, 0.0f, 1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();
            cube.translate(2.0f, 0.0f, -4.0f);
            cube.rotate(angleInDegrees, 0.0f, -1.0f, 0.0f);
            cube.draw(cHandler.getmPositionHandle(), cHandler.getmColourHandle(), cHandler.getmNormalHandle(), cHandler.getmMVPMatrixHandle(), cHandler.getmMVMatrixHandle(), cHandler.getmLightPosHandle(), mMVPMatrix, mViewMatrix, mProjectionMatrix, light.getmLightPosInEyeSpace());
            cube.resetMMatrix();

        }
    }


    //Used when demo is chosen in the list view to set the render mode.
    public void setShaderMode(int sMode) {
        shaderMode = sMode;
    }

    public void setRotating(boolean isRotating) {
        rotating = isRotating;
    }

    public void incrDecals() {
        decalCount++;
        if (decalCount > 7) {
            decalCount = 0;
        }
    }

    public CameraMovement getCam() {
        return cam1;
    }

    public void setCamBol() {
        camRot = true;
    }

    public void setFov(float x1) {
        fov.setFieldOfView(x1);
    }


}
