package gd2.gd2render.Core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import gd2.gd2render.OBJImporting.OBJParser;
import gd2.gd2render.OBJImporting.TDModel;
import gd2.gd2render.Utils.CameraMovement;
import gd2.gd2render.Primitives.Line;
import gd2.gd2render.Primitives.Cube;
import gd2.gd2render.Primitives.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SurfaceRenderer implements GLSurfaceView.Renderer {

    private int colour = 0;
    private int vPrimitive = 0;
    protected Triangle tri;
    protected Cube cube;
    protected Line line;
    private CameraMovement cM = new CameraMovement(2, 3, 0, 0, -1, 1);
    private CameraMovement cM1 = new CameraMovement(0, 0, -5, 0, 0, 0);
    private CameraMovement cM2 = new CameraMovement(-3, -4, 0, 2, 10, 0);
    private OBJParser parser;
    protected TDModel tModel;
    public float rotX = 0f, rotZ = -0.5f, count = 90;
    protected boolean rotating = false;

    /**
     * Surface Renderer Constructor
     */
    public SurfaceRenderer() {
        tri = new Triangle();
        cube = new Cube();
        line = new Line();
    }

    /**
     * Surface Renderer Constructor
     */
    public SurfaceRenderer(Context context) {
        tri = new Triangle();
        cube = new Cube();
        line = new Line();
        parser = new OBJParser(context);
        tModel=parser.parseOBJ("models/bear-obj.obj",0.5);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClearDepthf(1f);

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
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // Switching background colour based on touch input
        switch (colour) {
            case 0:

                if (vPrimitive == 0) {
                    gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                }
                if (vPrimitive > 0) {
                    gl.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
                }

                //tri.reColour(gl, 0.0f, 1.0f, 0.0f, 1.0f);
                //tri.rotate(0f,0.0f,0.0f,1.0f);
                break;
            case 1:
                if (vPrimitive == 0) {
                    gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
                }
                if (vPrimitive > 0) {
                    gl.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
                }
                //tri.reColour(gl, 0.0f, 0.0f, 1.0f, 1.0f);
                //tri.rotate(120f,0.0f,0.0f,1.0f);
                break;
            case 2:
                if (vPrimitive == 0) {
                    gl.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
                }
                if (vPrimitive > 0) {
                    gl.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
                }
                //tri.reColour(gl, 1.0f, 0.0f, 0.0f, 1.0f);
                //tri.rotate(240f,0.0f,0.0f,1.0f);
                break;
            //case 0: GLES31.glClearColor(1.0f, 0.0f, 0.0f, 1.0f); break;
            //case 1: GLES31.glClearColor(0.0f, 1.0f, 0.0f, 1.0f); break;
            //case 2: GLES31.glClearColor(0.0f, 0.0f, 1.0f, 1.0f); break;

        }
        //GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        if (vPrimitive == 0) {
            GLU.gluLookAt(gl, cM.getX(), cM.getY(), cM.getZ(), cM.getEyeX(), cM.getEyeY(), cM.getEyeZ(), 0, 0, 1);//tells the camera where to look
            line.draw(gl);

        } else if (vPrimitive == 1) {
            GLU.gluLookAt(gl, cM1.getX(), cM1.getY(), cM1.getZ(), cM1.getEyeX(), cM1.getEyeY(), cM1.getEyeZ(), 0, 2, 0);
            tri.draw(gl);//draws the triangle
        } else if (vPrimitive == 2) {
            GLU.gluLookAt(gl, cM2.getX(), cM2.getY(), cM2.getZ(), cM2.getEyeX(), cM2.getEyeY(), cM2.getEyeZ(), 0, 0, 1);
            cube.draw(gl);//draws the cube
        } else if (vPrimitive == 3) {
            GLU.gluLookAt(gl, cM1.getX(), cM1.getY(), cM1.getZ(), cM1.getEyeX(), cM1.getEyeY(), cM1.getEyeZ(), 0, 2, 0);
            tModel.draw(gl);

        }
    }

    // Increments the background colour based on touch input
    public void changeColour() {
        colour++;
        if (colour == 3) {
            colour = 0;
        }
    }

    /**
     * Set the value of vPrimitive
     *
     * @param value
     */
    public void setvPrimitive(int value) {
        vPrimitive = value;
    }

    /**
     * Increments the value of vPrimitive,
     * changing the shape
     */
    public void changeShape() {
        if (vPrimitive == 0) {
            vPrimitive += 1;
        } else if (vPrimitive == 1) {
            vPrimitive += 1;
        } else if (vPrimitive == 2) {
            vPrimitive += 1;
            if (vPrimitive == 3) {
                vPrimitive = 0;
            }
        }
    }

    /**
     * Return the value of colour
     *
     * @return
     */
    public int getColour() {
        return colour;
    }

    /**
     * Return the value of vPrimitive
     * @return
     */
    public int getvPrimitive() {
        return vPrimitive;
    }

    /**
     * Set the value of colour
     * @param c
     */
    public void setColour(int c) {
        colour = c;
    }

    public CameraMovement getLineCamera() {
        return cM;
    }

    public CameraMovement getTriCamera() {
        return cM1;
    }

    public CameraMovement getCubeCamera() {
        return cM2;
    }

    public void setRotating(boolean isRotating) {
        rotating = isRotating;
    }


}
