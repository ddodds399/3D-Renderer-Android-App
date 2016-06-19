package gd2.gd2render.Primitives;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Dean on 02/11/2015.
 */
/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-two-ambient-and-diffuse-lighting/
 */
public class CubeGL2 {

    /**
     * Store the model matrix.
     */
    private float[] mModelMatrix = new float[16];
    /**
     * Store the cube vertex, colour, normal and texture coordinate data.
     */
    private float[] cubeVertexData, cubeColourData, cubeNormalData, cubeTextureCoordinateData;
    /**
     * Store model data in a float buffer.
     */
    private final FloatBuffer mCubeVertices;
    /**
     * Store colour data in a float buffer.
     */
    private final FloatBuffer mCubeColours;
    /**
     * Store normal data in a float buffer.
     */
    private final FloatBuffer mCubeNormals;
    /**
     * Store texture coordinate data in a float buffer.
     */
    private final FloatBuffer mCubeTextureCoordinates;

    /**
     * Constructor for cube, setting up vertices, colours, normals, texture coordinates and the identity matrix.
     */
    public CubeGL2() {
        // Define points for a cube.
        // X, Y, Z
        cubeVertexData = new float[]
                {
                        // Front face
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        // Right face
                        1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,
                        // Back face
                        1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        // Left face
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        // Top face
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        // Bottom face
                        1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                };
        // R, G, B, A
        cubeColourData = new float[]
                {
                        // Front face
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        // Right face
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        // Back face
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        // Left face
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        // Top face
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        // Bottom face
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f
                };
        // X, Y, Z
        cubeNormalData = new float[]
                {
                        // Front face
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        // Right face
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        // Back face
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        // Left face
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        // Top face
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        // Bottom face
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f
                };
        // X, Y
        // Texture coordinate data.
        cubeTextureCoordinateData = new float[]
                {
                        // Front face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Right face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Back face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Left face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Top face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Bottom face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f
                };
        // Initialize the buffers.
        mCubeVertices = ByteBuffer.allocateDirect(cubeVertexData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeVertices.put(cubeVertexData).position(0);
        mCubeColours = ByteBuffer.allocateDirect(cubeColourData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeColours.put(cubeColourData).position(0);
        mCubeNormals = ByteBuffer.allocateDirect(cubeNormalData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeNormals.put(cubeNormalData).position(0);
        mCubeTextureCoordinates = ByteBuffer.allocateDirect(cubeTextureCoordinateData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    //Method for resetting identity matrix after transformations have been applied.
    public void resetMMatrix() {
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Method for rotating cube.
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
     * Method for translating cube.
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }

    //Getters for aspects of cube.
    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public FloatBuffer getCubeVertices() {
        return mCubeVertices;
    }

    public FloatBuffer getCubeColours() {
        return mCubeColours;
    }

    public FloatBuffer getCubeNormals() {
        return mCubeNormals;
    }

    public FloatBuffer getmCubeTextureCoordinates() {
        return mCubeTextureCoordinates;
    }

    public float[] getCubeVertexData() {
        return cubeVertexData;
    }

    public float[] getCubeColourData() {
        return cubeColourData;
    }

    public float[] getCubeNormalData() {
        return cubeNormalData;
    }

    public float[] getCubeTextureCoordinateData() {
        return cubeTextureCoordinateData;
    }

    /**
     * Draws a cube from the given  data.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param normalHandle - Model normal information.
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvHandle     - ModelView Matrix information.
     * @param lightHandle  - Light position information
     * @param mvpMat       - ModelViewProjection Matrix from renderer
     * @param viewMat      - View Matrix from renderer
     * @param projMat      - Projection Matrix from render
     * @param lightEye     - Light position in eye space
     */
    public void draw(int posHandle, int colourHandle, int normalHandle, int mvpHandle, int mvHandle, int lightHandle, float[] mvpMat, float[] viewMat, float[] projMat, float[] lightEye) {
        // Pass in the position information
        mCubeVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mCubeVertices);
        GLES20.glEnableVertexAttribArray(posHandle);
        // Pass in the colour information
        mCubeColours.position(0);
        GLES20.glVertexAttribPointer(colourHandle, 4, GLES20.GL_FLOAT, false,
                0, mCubeColours);
        GLES20.glEnableVertexAttribArray(colourHandle);
        // Pass in the normal information
        mCubeNormals.position(0);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                0, mCubeNormals);
        GLES20.glEnableVertexAttribArray(normalHandle);
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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }

    /**
     * Draws a cube from the given  data.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param normalHandle - Model normal information.
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvHandle     - ModelView Matrix information.
     * @param lightHandle  - Light position information.
     * @param texHandle    - Texture Coordinate information.
     * @param mvpMat       - ModelViewProjection Matrix from renderer
     * @param viewMat      - View Matrix from renderer.
     * @param projMat      - Projection Matrix from render.
     * @param lightEye     - Light position in eye space.
     */
    public void drawTexturedCube(int posHandle, int colourHandle, int normalHandle, int mvpHandle, int mvHandle, int lightHandle, int texHandle, float[] mvpMat, float[] viewMat, float[] projMat, float[] lightEye) {
        // Pass in the position information
        mCubeVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mCubeVertices);
        GLES20.glEnableVertexAttribArray(posHandle);
        // Pass in the color information
        mCubeColours.position(0);
        GLES20.glVertexAttribPointer(colourHandle, 4, GLES20.GL_FLOAT, false,
                0, mCubeColours);
        GLES20.glEnableVertexAttribArray(colourHandle);
        // Pass in the normal information
        mCubeNormals.position(0);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                0, mCubeNormals);
        GLES20.glEnableVertexAttribArray(normalHandle);
        // Pass in the texture coordinate information
        mCubeTextureCoordinates.position(0);
        GLES20.glVertexAttribPointer(texHandle, 2, GLES20.GL_FLOAT, false,
                0, mCubeTextureCoordinates);
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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }

    /**
     * Draws a cube from the given  data.
     *
     * @param posHandle    - Model position information.
     * @param colourHandle - Model Colour Information.
     * @param mvpHandle    - Transformation Matrix information.
     * @param mvpMat       - ModelViewProjection Matrix from renderer
     * @param viewMat      - View Matrix from renderer.
     * @param projMat      - Projection Matrix from render.
     */
    public void simpleDraw(int posHandle, int colourHandle, int mvpHandle, float[] mvpMat, float[] viewMat, float[] projMat) {
        // Pass in the position information
        mCubeVertices.position(0);
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
                0, mCubeVertices);
        GLES20.glEnableVertexAttribArray(posHandle);
        // Pass in the color information
        mCubeColours.position(0);
        GLES20.glVertexAttribPointer(colourHandle, 4, GLES20.GL_FLOAT, false,
                0, mCubeColours);
        GLES20.glEnableVertexAttribArray(colourHandle);
        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);
        // Pass in the combined matrix.
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
    }

    /**
     * Recolours the 6 faces of the cube, given colour data for each face as a hex colour. (eg. "#FFFFFF")
     *
     * @param front - Front face colour in hex
     * @param right - Right face colour in hex
     * @param back - Back face colour in hex
     * @param left - Left face colour in hex
     * @param top - Top face colour in hex
     * @param bottom - Bottom face colour in hex
     */
    public void reColourFaces(String front, String right, String back, String left, String top, String bottom) {

        // Convert each face's hex colour codes into float represented colours
        float rf, gf, bf, a;
        rf = Integer.valueOf(front.substring(1, 3), 16) / 255.00f;
        gf = Integer.valueOf(front.substring(3, 5), 16) / 255.00f;
        bf = Integer.valueOf(front.substring(5, 7), 16) / 255.00f;
        a = 1.00f;
        float rr, gr, br;
        rr = Integer.valueOf(right.substring(1, 3), 16) / 255.00f;
        gr = Integer.valueOf(right.substring(3, 5), 16) / 255.00f;
        br = Integer.valueOf(right.substring(5, 7), 16) / 255.00f;

        float rb, gb, bb;
        rb = Integer.valueOf(back.substring(1, 3), 16) / 255.00f;
        gb = Integer.valueOf(back.substring(3, 5), 16) / 255.00f;
        bb = Integer.valueOf(back.substring(5, 7), 16) / 255.00f;

        float rl, gl, bl;
        rl = Integer.valueOf(left.substring(1, 3), 16) / 255.00f;
        gl = Integer.valueOf(left.substring(3, 5), 16) / 255.00f;
        bl = Integer.valueOf(left.substring(5, 7), 16) / 255.00f;

        float rt, gt, bt;
        rt = Integer.valueOf(top.substring(1, 3), 16) / 255.00f;
        gt = Integer.valueOf(top.substring(3, 5), 16) / 255.00f;
        bt = Integer.valueOf(top.substring(5, 7), 16) / 255.00f;

        float rm, gm, bm;
        rm = Integer.valueOf(bottom.substring(1, 3), 16) / 255.00f;
        gm = Integer.valueOf(bottom.substring(3, 5), 16) / 255.00f;
        bm = Integer.valueOf(bottom.substring(5, 7), 16) / 255.00f;

        // Apply all the colour data to the cubeColourData array
        cubeColourData = new float[]{

                // Front face
                rf, gf, bf, a,
                rf, gf, bf, a,
                rf, gf, bf, a,
                rf, gf, bf, a,
                rf, gf, bf, a,
                rf, gf, bf, a,
                // Right face
                rr, gr, br, a,
                rr, gr, br, a,
                rr, gr, br, a,
                rr, gr, br, a,
                rr, gr, br, a,
                rr, gr, br, a,
                // Back face
                rb, gb, bb, a,
                rb, gb, bb, a,
                rb, gb, bb, a,
                rb, gb, bb, a,
                rb, gb, bb, a,
                rb, gb, bb, a,
                // Left face
                rl, gl, bl, a,
                rl, gl, bl, a,
                rl, gl, bl, a,
                rl, gl, bl, a,
                rl, gl, bl, a,
                rl, gl, bl, a,
                // Top face
                rt, gt, bt, a,
                rt, gt, bt, a,
                rt, gt, bt, a,
                rt, gt, bt, a,
                rt, gt, bt, a,
                rt, gt, bt, a,
                // Bottom face
                rm, gm, bm, a,
                rm, gm, bm, a,
                rm, gm, bm, a,
                rm, gm, bm, a,
                rm, gm, bm, a,
                rm, gm, bm, a
        };
        // Add the colour data to the colour buffer
        mCubeColours.put(cubeColourData).position(0);
    }

}
