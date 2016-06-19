package gd2.gd2render.Lighting;

import android.opengl.GLES20;
import android.opengl.Matrix;

/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/android-lesson-two-ambient-and-diffuse-lighting/
 */
public class Light {

    /**
     * Stores the model matrix specifically for the light position.
     */
    private float[] mLightModelMatrix = new float[16];

    /**
     * Used to hold a light centered on the origin in model space.
     **/
    private final float[] mLightPosInModelSpace = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    /**
     * Used to hold the current position of the light in world space (after transformation via model matrix).
     */
    private final float[] mLightPosInWorldSpace = new float[4];
    /**
     * Used to hold the transformed position of the light in eye space (after transformation via modelview matrix)
     */
    private final float[] mLightPosInEyeSpace = new float[4];

    //Constructor for the light, sets identity matrix.
    public Light() {
        Matrix.setIdentityM(mLightModelMatrix, 0);
    }

    /**
     * Draws a point representing position of the light
     *
     * @param pointPosHandle - Point light position information
     * @param pointMVPHandle - Light transformation matrix
     * @param mvpMat         - ModelViewProjection Matrix from renderer
     * @param viewMat        - View Matrix from renderer
     * @param projMat        - Projection Matrix from render
     */
    public void draw(int pointPosHandle, int pointMVPHandle, float[] mvpMat, float[] viewMat, float[] projMat) {
        // Pass in the position.
        GLES20.glVertexAttrib3f(pointPosHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);
        // Since we are not using a buffer object, disable vertex arrays for this attribute.
        GLES20.glDisableVertexAttribArray(pointPosHandle);
        // Pass in the transformation matrix.
        Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mLightModelMatrix, 0);
        Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);
        GLES20.glUniformMatrix4fv(pointMVPHandle, 1, false, mvpMat, 0);
        // Draw the point.
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }

    //Method for resetting identity matrix after transformations have been applied.
    public void resetMMatrix() {
        Matrix.setIdentityM(mLightModelMatrix, 0);
    }

    /**
     * Method for rotating the light
     *
     * @param angle - angle of rotation
     * @param x     - enter 1.0f to rotate around 'x' axis
     * @param y     - enter 1.0f to rotate around 'y' axis
     * @param z     - enter 1.0f to rotate around 'z' axis
     */
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mLightModelMatrix, 0, angle, x, y, z);
    }

    /**
     * Method for translating light
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mLightModelMatrix, 0, x, y, z);
    }

    /**
     * Used to position light correctly in world.
     *
     * @param viewMat - View matrix from renderer
     */
    public void multiplyMV(float[] viewMat) {
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, viewMat, 0, mLightPosInWorldSpace, 0);
    }

    //Getters for matrices to be used in renderer.
    public float[] getmLightPosInEyeSpace() {
        return mLightPosInEyeSpace;
    }

    public float[] getModelMatrix() {
        return mLightModelMatrix;
    }
}
