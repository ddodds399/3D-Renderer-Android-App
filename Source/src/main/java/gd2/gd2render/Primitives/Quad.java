package gd2.gd2render.Primitives;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Quad {

    /**
     * Store the model data in a float buffer.
     */
    private final FloatBuffer mQuadVertices;

    /**
     * Store the normal data in a float buffer.
     */
    private final FloatBuffer mQuadNormals;

    /**
     * Store the texture data in a float buffer.
     */
    private final FloatBuffer mQuadTex;

    /**
     * Store the index data in a float buffer.
     */
    private final ShortBuffer mQuadIndices;

    /**
     * Store the model matrix.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the quad vertex data.
     */
    private float[] mQuadVerticesData;

    /**
     * Store the quad texture vertex data.
     */
    private float[] mQuadTexData;

    /**
     * Store the quad index data.
     */
    private short[] mQuadIndexData;
    /**
     * Store the quad normal data.
     */
    private float[] mQuadNormalData;

    /**
     * Constructor for Quad, setting up vertices, normals, texture co-ordinates and indices.
     */
    public Quad() {
        mQuadVerticesData = new float[]{
                // X, Y, Z,
                // Left bottom triangle
                -1.0f, 1.0f, 0f,
                -1.0f, -1.0f, 0f,
                1.0f, -1.0f, 0f,
                // Right top triangle
                1.0f, -1.0f, 0f,
                1.0f, 1.0f, 0f,
                -1.0f, 1.0f, 0f
        };

        mQuadNormalData = new float[]{
                // Front face
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f
        };

        mQuadTexData = new float[] {
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        mQuadIndexData = new short[]{0, 1, 2, 0, 2, 3};

        mQuadVertices = ByteBuffer.allocateDirect(mQuadVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadVertices.put(mQuadVerticesData).position(0);
        mQuadIndices = ByteBuffer.allocateDirect(mQuadIndexData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mQuadIndices.put(mQuadIndexData).position(0);
        mQuadTex = ByteBuffer.allocateDirect(mQuadTexData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadTex.put(mQuadTexData).position(0);
        mQuadNormals = ByteBuffer.allocateDirect(mQuadNormalData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadNormals.put(mQuadNormalData).position(0);

        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Constructor for Quad of a size defined by the user.
     */
    public Quad(double width, double height) {
        mQuadVerticesData = new float[]{
                // X, Y, Z,
                // Left bottom triangle
                -1.0f, 1.0f, 0f,
                -1.0f, -1.0f, 0f,
                1.0f, -1.0f, 0f,
                // Right top triangle
                1.0f, -1.0f, 0f,
                1.0f, 1.0f, 0f,
                -1.0f, 1.0f, 0f
        };

        for(int i=0; i<mQuadVerticesData.length; i+=3){
            mQuadVerticesData[i] = mQuadVerticesData[i]*(float)width;
        }
        for(int i=1; i<mQuadVerticesData.length; i+=3){
            mQuadVerticesData[i] = mQuadVerticesData[i]*(float)height;
        }

        mQuadNormalData = new float[]{
                // Front face
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f
        };

        mQuadTexData = new float[] {
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        mQuadIndexData = new short[] {0, 1, 2, 0, 2, 3 };

        mQuadVertices = ByteBuffer.allocateDirect(mQuadVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadVertices.put(mQuadVerticesData).position(0);
        mQuadIndices = ByteBuffer.allocateDirect(mQuadIndexData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mQuadIndices.put(mQuadIndexData).position(0);
        mQuadTex = ByteBuffer.allocateDirect(mQuadTexData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadTex.put(mQuadTexData).position(0);
        mQuadNormals = ByteBuffer.allocateDirect(mQuadNormalData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mQuadNormals.put(mQuadNormalData).position(0);

        Matrix.setIdentityM(mModelMatrix, 0);
    }

    //Method for resetting identity matrix after transformations have been applied.
    public void resetMMatrix() {
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Method for rotating Quad
     *
     * @param angle - angle of rotation
     * @param x     - enter 1.0f to rotate around 'x' axis
     * @param y     - enter 1.0f to rotate around 'y' axis
     * @param z     - enter 1.0f to rotate around 'z' axis
     */
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
    }
    /*
        Method for rotating the texture coordinates of the quad
     * @param angle - angle of rotation
     * @param x     - enter 1.0f to rotate around 'x' axis
     * @param y     - enter 1.0f to rotate around 'y' axis
     * @param z     - enter 1.0f to rotate around 'z' axis
     */

    /**
     * Method for translating Quad
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }

    //Getters for aspects of quad.

    public FloatBuffer getQuadVertices() {
        return mQuadVertices;
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public float[] getQuadVertexData() {
        return mQuadVerticesData;
    }

    /**
     * Draws a quad from the given vertex data.
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
        mQuadVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mQuadVertices);

        GLES20.glEnableVertexAttribArray(posHandle);


        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);

        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

    /**
     * Method for drawing a textured quad, but one that doesn't take into
     * account lighting information.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param normalHandle - Model Normal Data information
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvHandle     - ModelViewProjection Matrix from renderer
     * @param lightHandle  - Model Lighting information
     * @param texHandle    - Model Texture information
     * @param mvpMat       - Model view projection Matrix
     * @param viewMat      - View Matrix from renderer
     * @param projMat      - Projection matrix
     * @param lightEye     - Light position information
     */
    public void drawTexturedQuad(int posHandle, int colourHandle, int normalHandle, int mvpHandle, int mvHandle, int lightHandle, int texHandle, float[] mvpMat, float[] viewMat, float[] projMat, float[] lightEye) {
        // Pass in the position information
        mQuadVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mQuadVertices);
        GLES20.glEnableVertexAttribArray(posHandle);
        mQuadNormals.position(0);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                0, mQuadNormals);
        GLES20.glEnableVertexAttribArray(normalHandle);
        // Pass in the texture coordinate information
        mQuadTex.position(0);
        GLES20.glVertexAttribPointer(texHandle, 2, GLES20.GL_FLOAT, false,
                0, mQuadTex);
        GLES20.glEnableVertexAttribArray(texHandle);
        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);
        // Pass in the modelview matrix.
        GLES20.glUniformMatrix4fv(mvHandle, 1, false, mvpMat, 0);
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);
        // Pass in the combined matrix.
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
        // Pass in the light position in eye space.
        GLES20.glUniform3f(lightHandle, lightEye[0], lightEye[1], lightEye[2]);
        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

    /**
     * Method for drawing a textured quad, but one that doesn't take into
     * account lighting information.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param normalHandle - Model Normal Data information
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvHandle     - ModelViewProjection Matrix from renderer
     * @param texHandle    - Model Texture information
     * @param mvpMat       - Model view projection Matrix
     * @param viewMat      - View Matrix from renderer
     * @param projMat      - Projection matrix
     */
    public void drawSkyQuad(int posHandle, int colourHandle, int normalHandle, int mvpHandle, int mvHandle, int texHandle, float[] mvpMat, float[] viewMat, float[] projMat) {
        // Pass in the position information
        mQuadVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mQuadVertices);
        GLES20.glEnableVertexAttribArray(posHandle);
        mQuadNormals.position(0);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                0, mQuadNormals);
        GLES20.glEnableVertexAttribArray(normalHandle);
        // Pass in the texture coordinate information
        mQuadTex.position(0);
        GLES20.glVertexAttribPointer(texHandle, 2, GLES20.GL_FLOAT, false,
                0, mQuadTex);
        GLES20.glEnableVertexAttribArray(texHandle);
        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);
        // Pass in the modelview matrix.
        GLES20.glUniformMatrix4fv(mvHandle, 1, false, mvpMat, 0);
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);
        // Pass in the combined matrix.
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
        // Pass in the light position in eye space.
        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
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

        mQuadVertices.put(triangleVerticesUpdated).position(0);
    }

}
