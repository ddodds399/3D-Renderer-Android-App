package gd2.gd2render.Particles;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import gd2.gd2render.Core.SurfaceRenderer;
import gd2.gd2render.Primitives.Cube;
import gd2.gd2render.Primitives.CubeGL2;
import gd2.gd2render.R;
import gd2.gd2render.Utils.Handler;

public class ParticlesRenderer extends SurfaceRenderer {

    public Context mContext;
    public ParticleSystem mParticleSystem;
    public Cube c1;
    public CubeGL2 c2;
    private Handler sHandler, cHandler;
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    /**
     * The constructor for the particle renderer class
     * @param context - the context of the surface that is to be used
     */
    public ParticlesRenderer(Context context){
        mParticleSystem = new ParticleSystem();
                mContext = context;

       // c1 = new Cube();
        //c2 = new CubeGL2();

    }

    /**
     * what to do when the surface is created
     * @param gl - the opengl GL10 object to be used
     * @param config - used to set frame buffer configurations
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        if (mParticleSystem.particleType == 0) {
            mParticleSystem.mTexture = mParticleSystem.mParticles[0].loadGLTexture(gl, mContext);
        } else if (mParticleSystem.particleType  == 1){
            mParticleSystem.mTexture = mParticleSystem.m1Particles[0].loadGLTexture(gl,mContext);
        }

            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            gl.glClearDepthf(1.0f);
            gl.glDisable(gl.GL_DEPTH_TEST);
            gl.glDisable(gl.GL_DITHER);
            gl.glDisable(gl.GL_LIGHTING);
            gl.glEnable(gl.GL_BLEND);
            gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE);
        }


    /**
     * method that determines what to do if the render surface changes
     * @param gl - the GL10 object to be used
     * @param width - the width of the render surface
     * @param height - the height of the render surface
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height){
        {
            if(height == 0)
                height = 1;
            gl.glViewport(0, 0, width, height);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, 30.0f, (float) width / (float) height, 1.0f, 100.0f);
        }

    }

    /**
     * method that draws to the render surface
     * @param gl - the GL10 object to be used
     */
    @Override
    public void onDrawFrame(GL10 gl){

            gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            GLU.gluLookAt(gl,
                    0.0f, -10.0f, 15.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 1.0f, 0.0f);
            mParticleSystem.update();

            mParticleSystem.draw(gl);

    }

    /**
     * method used to set the particle type to be rendered
     * @param a - 0 = smoke, 1 = bubble
     */
    public void setParticleType(int a){
        mParticleSystem.particleType = a;

    }

}
