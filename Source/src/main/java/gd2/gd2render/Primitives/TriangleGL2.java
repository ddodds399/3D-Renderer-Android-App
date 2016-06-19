package gd2.gd2render.Primitives;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-one-getting-started/
 */

public class TriangleGL2 {

    /**
     * Store the model data in a float buffer.
     */
    private final FloatBuffer mTriangleVertices;
    /**
     * Store the model matrix.
     */
    private float[] mModelMatrix = new float[16];
    /**
     * Store the triangle vertex data.
     */
    private float[] mTriangleVerticesData;

    /**
     * Constructor for triangle, setting up vertices and identity matrix.
     */
    public TriangleGL2() {
        mTriangleVerticesData = new float[]{
                // X, Y, Z,
                // R, G, B, A
                -1.0f, -0.5f, 0.0f,
                1.0f, 0.0f, 0.0f, 1.0f,

                1.0f, -0.5f, 0.0f,
                0.0f, 0.0f, 1.0f, 1.0f,

                0.0f, 0.559016994f*2, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f};

        mTriangleVertices = ByteBuffer.allocateDirect(mTriangleVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleVertices.put(mTriangleVerticesData).position(0);

        Matrix.setIdentityM(mModelMatrix, 0);
    }

    public TriangleGL2(double base, double height) {
        mTriangleVerticesData = new float[]{
                // X, Y, Z,
                // R, G, B, A
                -1.0f, -0.5f, 0.0f,
                1.0f, 0.0f, 0.0f, 1.0f,

                1.0f, -0.5f, 0.0f,
                0.0f, 0.0f, 1.0f, 1.0f,

                0.0f, 0.559016994f*2, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f};

        for(int i=0; i<mTriangleVerticesData.length; i+=7){
            mTriangleVerticesData[i] = mTriangleVerticesData[i]*(float)base;
        }
        for(int i=1; i<mTriangleVerticesData.length; i+=7){
            mTriangleVerticesData[i] = mTriangleVerticesData[i]*(float)height;
        }

        mTriangleVertices = ByteBuffer.allocateDirect(mTriangleVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleVertices.put(mTriangleVerticesData).position(0);

        Matrix.setIdentityM(mModelMatrix, 0);
    }

    //Method for resetting identity matrix after transformations have been applied.
    public void resetMMatrix() {
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Method for rotating triangle
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
     * Method for translating triangle
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }

    //Getters for aspects of triangle.

    public FloatBuffer getTriangleVertices() {
        return mTriangleVertices;
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public float[] getTriangleVertexData() {
        return mTriangleVerticesData;
    }

    /**
     * Draws a triangle from the given vertex data.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvpMat       - ModelViewProjection Matrix from renderer
     * @param viewMat      - View Matrix from renderer
     * @param projMat      - Projection Matrix from render
     */
    public void draw(int posHandle, int colourHandle, int mvpHandle, float[] mvpMat, float[] viewMat, float[] projMat) {
        // Pass in the position information
        mTriangleVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                28, mTriangleVertices);

        GLES20.glEnableVertexAttribArray(posHandle);

        // Pass in the color information
        mTriangleVertices.position(3);
        GLES20.glVertexAttribPointer(colourHandle, 4, GLES20.GL_FLOAT, false,
                28, mTriangleVertices);

        GLES20.glEnableVertexAttribArray(colourHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);

        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    /**
     *Recolours the triangle based on input hex colour codes
     *
     * @param v1 - vertex1's colour as hex colour code
     * @param v2 - vertex2's colour as hex colour code
     * @param v3 - vertex3's colour as hex colour code
     */
    // NOT COMPLETE DO NOT USE
    public void reColourVertices(String v1, String v2, String v3) {

        float r1, g1, b1, a;
        r1 = Integer.valueOf(v1.substring(1, 3), 16) / 255.00f;
        g1 = Integer.valueOf(v1.substring(3, 5), 16) / 255.00f;
        b1 = Integer.valueOf(v1.substring(5, 7), 16) / 255.00f;
        a = 1.00f;
        float r2, g2, b2;
        r2 = Integer.valueOf(v2.substring(1, 3), 16) / 255.00f;
        g2 = Integer.valueOf(v2.substring(3, 5), 16) / 255.00f;
        b2 = Integer.valueOf(v2.substring(5, 7), 16) / 255.00f;

        float r3, g3, b3;
        r3 = Integer.valueOf(v3.substring(1, 3), 16) / 255.00f;
        g3 = Integer.valueOf(v3.substring(3, 5), 16) / 255.00f;
        b3 = Integer.valueOf(v3.substring(5, 7), 16) / 255.00f;


        float[] triangleVerticesUpdated = new float[]{

//                 X, Y, Z,
//                 R, G, B, A
                -0.5f, -0.25f, 0.0f,
                r1, b1, g1, a,

                0.5f, -0.25f, 0.0f,
                r2, b2, g2, a,

                0.0f, 0.559016994f, 0.0f,
                r3, b3, g3, a};

        mTriangleVertices.put(triangleVerticesUpdated).position(0);
    }

}


