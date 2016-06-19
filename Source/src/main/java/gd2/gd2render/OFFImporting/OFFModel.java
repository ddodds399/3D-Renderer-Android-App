package gd2.gd2render.OFFImporting;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Dean on 01/12/2015.
 */
public class OFFModel {

    /**
     * Store the model data in a float buffer.
     */
    private FloatBuffer mModelVertices;

    /**
     * Store the index data in a float buffer.
     */
    private ShortBuffer mModelIndices;

    /**
     * Store the vertex and index data from .OFF file.
     */
    private OFFObject mObject;

    /**
     * Store the model matrix.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the model's vertex data.
     */
    private float[] mModelVerticesData;

    /**
     * Store the model's index data.
     */
    private short[] mModelIndexData;

    /**
     * Constructor for setting vertices and indices of desired model.
     * @param in - Inputsteam containing .OFF file that is to be loaded in
     */
    public OFFModel (InputStream in){
        final IOFFLoader mLoader = new OFFLoader();
        try {
           mObject = mLoader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mModelVerticesData = new float[mObject.getaVertices().size()];
        for(int i=0;i<mObject.getaVertices().size(); i++){
            mModelVerticesData[i]=mObject.getaVertices().get(i)*6;
        }

        mModelIndexData = new short[mObject.getIndices().size()];
        for(int i=0;i<mObject.getIndices().size(); i++){
            mModelIndexData[i]=mObject.getIndices().get(i);
        }


        mModelVertices = ByteBuffer.allocateDirect(mModelVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mModelVertices.put(mModelVerticesData).position(0);
        mModelIndices = ByteBuffer.allocateDirect(mModelIndexData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mModelIndices.put(mModelIndexData).position(0);
        Matrix.setIdentityM(mModelMatrix, 0);

    }

    //Method for resetting identity matrix after transformations have been applied.
    public void resetMMatrix() {
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Method for rotating model
     *
     * @param angle - angle of rotation
     * @param x     - enter 1.0f to rotate around 'x' axis
     * @param y     - enter 1.0f to rotate around 'y' axis
     * @param z     - enter 1.0f to rotate around 'z' axis
     */
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
    }

    /**
     * Method for translating model
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }

    /**
     * Method used to draw the model in OpenGLES Version 1.0
     */
    public void drawGL10(GL10 gl)
    {
//        gl.glTranslatef(0.4f, -0.5f, 4.5f);
//        gl.glRotatef(90, 0, 0, 1);
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mModelVertices);
        gl.glDrawElements(GL10.GL_TRIANGLES, mObject.getVertices().size(), GL10.GL_UNSIGNED_SHORT, mModelIndices);
        //gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    /**
     * Draws a model from the given vertex data in OpenGLES Version 2.0 and above.
     *
     * @param posHandle    - Model position information.
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvpMat       - ModelViewProjection Matrix from renderer
     * @param viewMat      - View Matrix from renderer
     * @param projMat      - Projection Matrix from render
     */
    public void draw(int posHandle, int mvpHandle, float[] mvpMat, float[] viewMat, float[] projMat){
        final int[] vbo = new int[1];
        final int[] ibo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glGenBuffers(1, ibo, 0);

        if (vbo[0]>0 && ibo[0] >0){
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mModelVertices.capacity() * 4,
                    mModelVertices, GLES20.GL_STATIC_DRAW);

            GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                    0, 0);
            GLES20.glEnableVertexAttribArray(posHandle);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, mModelIndices.capacity()
                    * 2, mModelIndices, GLES20.GL_STATIC_DRAW);

            Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);

            // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
            Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);

            GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);

            GLES20.glDrawElements(GLES20.GL_TRIANGLES, mObject.getIndices().size(), GLES20.GL_UNSIGNED_SHORT, 0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);


        }

    }



}
