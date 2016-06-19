package gd2.gd2render.OFFImporting;

import android.opengl.GLSurfaceView;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Dean on 02/12/2015.
 */
public class ModelRenderer implements GLSurfaceView.Renderer {

    private OFFModel mModel;

    /**
     * Constructor to created a render surface that specifically caters to an OpenGLES 1.0 version of the model.
     * @param in - Inputstream containing .OFF file.
     */
    public ModelRenderer(InputStream in){
            mModel = new OFFModel(in);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);//sets matrix mode
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, .5f, 1, 25);//helps define viewing area
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        mModel.drawGL10(gl);
    }
}
