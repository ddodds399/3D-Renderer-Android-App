package gd2.gd2render.Utils;

import android.opengl.GLES20;

/*
    Learn Open GLES tutorial used and modified.
    http://www.learnopengles.com/
 */

public class Handler {

    /**
     * This will be used to pass in the transformation matrix.
     */
    private int mMVPMatrixHandle;
    /**
     * This will be used to pass in the modelview matrix.
     */
    private int mMVMatrixHandle;
    /**
     * This will be used to pass in the light position.
     */
    private int mLightPosHandle;
    /**
     * This will be used to pass in model position information.
     */
    private int mPositionHandle;
    /**
     * This will be used to pass in model color information.
     */
    private int mColourHandle;
    /**
     * This will be used to pass in model normal information.
     */
    private int mNormalHandle;
    /**
     * This is a handle to our per-vertex shading program.
     */
    private int mShaderProgramHandle;
    /**
     * This is a handle to our light point program.
     */
    private int mPointProgramHandle;
    /**
     * This will be used to pass in the light transformation matrix.
     */
    private int mPointMVPMatrixHandle;
    /**
     * This will be used to pass in point light position information.
     */
    private int mPointPositionHandle;
    /**
     * This will be used to pass in model texture coordinate information.
     */
    private int mTextureCoordinateHandle;
    /**
     * This will be used to pass in the texture.
     */
    private int mTextureUniformHandle;

    /**
     * Constructor sets up accompanying handles for the shader chosen.
     *
     * @param pvShader - String for shader type
     */
    public Handler(String pvShader) {
        ProgramShader vertexShader = new ProgramShader(pvShader);
        ProgramShader pointShader = new ProgramShader("point");
        mShaderProgramHandle = vertexShader.getProgramHandle();
        mPointProgramHandle = pointShader.getProgramHandle();
        if (pvShader.equals("simple")) {
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_MVPMatrix");
            mPositionHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Position");
            mColourHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Color");
        } else if (pvShader.equals("perVertex") || pvShader.equals("perPixel")) {
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_MVPMatrix");
            mMVMatrixHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_MVMatrix");
            mLightPosHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_LightPos");
            mPositionHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Position");
            mColourHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Color");
            mNormalHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Normal");
            mPointMVPMatrixHandle = GLES20.glGetUniformLocation(mPointProgramHandle, "u_MVPMatrix");
            mPointPositionHandle = GLES20.glGetAttribLocation(mPointProgramHandle, "a_Position");
        } else if (pvShader.equals("texture")) {
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_MVPMatrix");
            mMVMatrixHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_MVMatrix");
            mLightPosHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_LightPos");
            mTextureUniformHandle = GLES20.glGetUniformLocation(mShaderProgramHandle, "u_Texture");
            mPositionHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Position");
            mColourHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Color");
            mNormalHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_Normal");
            mTextureCoordinateHandle = GLES20.glGetAttribLocation(mShaderProgramHandle, "a_TexCoordinate");
            mPointMVPMatrixHandle = GLES20.glGetUniformLocation(mPointProgramHandle, "u_MVPMatrix");
            mPointPositionHandle = GLES20.glGetAttribLocation(mPointProgramHandle, "a_Position");
        }
    }

    //Getter Methods for all handles need within the Renderer.

    public int getmColourHandle() {
        return mColourHandle;
    }

    public int getmLightPosHandle() {
        return mLightPosHandle;
    }

    public int getmMVMatrixHandle() {
        return mMVMatrixHandle;
    }

    public int getmMVPMatrixHandle() {
        return mMVPMatrixHandle;
    }

    public int getmNormalHandle() {
        return mNormalHandle;
    }

    public int getmShaderProgramHandle() {
        return mShaderProgramHandle;
    }

    public int getmPointProgramHandle() {
        return mPointProgramHandle;
    }

    public int getmPositionHandle() {
        return mPositionHandle;
    }

    public int getmTextureCoordinateHandle() {
        return mTextureCoordinateHandle;
    }

    public int getmTextureUniformHandle() {
        return mTextureUniformHandle;
    }

    public int getmPointMVPMatrixHandle() {
        return mPointMVPMatrixHandle;
    }

    public int getmPointPositionHandle() {
        return mPointPositionHandle;
    }
}
