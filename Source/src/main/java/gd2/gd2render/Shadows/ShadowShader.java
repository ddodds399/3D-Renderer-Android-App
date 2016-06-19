package gd2.gd2render.Shadows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by James on 04/12/2015.
 * Used http://www.codeproject.com/Articles/822380/Shadow-Mapping-with-Android-OpenGL-ES as tutorial and base
 */
public class ShadowShader {
    private static final String TAG = "ShadowShader";

    private int mProgram, mVertexShader, mPixelShader;

    private String mVertexS, mFragmentS;

    public ShadowShader(String vertexS, String fragmentS) {
        setup(vertexS, fragmentS);
    }

    // Takes in ids for files to be read
    public ShadowShader(int vID, int fID, Context context) {
        StringBuffer vs = new StringBuffer();
        StringBuffer fs = new StringBuffer();

        // read the files
        try {
            // Read the file from the resource
            InputStream inputStream = context.getResources().openRawResource(vID);
            // setup Bufferedreader
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            String read = in.readLine();
            while (read != null) {
                vs.append(read + "\n");
                read = in.readLine();
            }

            vs.deleteCharAt(vs.length() - 1);

            // Now read FS
            inputStream = context.getResources().openRawResource(fID);
            // setup Bufferedreader
            in = new BufferedReader(new InputStreamReader(inputStream));

            read = in.readLine();
            while (read != null) {
                fs.append(read + "\n");
                read = in.readLine();
            }

            fs.deleteCharAt(fs.length() - 1);
        } catch (Exception e) {
            Log.d(TAG, "Could not read shader: " + e.getLocalizedMessage());
        }


        // Setup everything
        setup(vs.toString(), fs.toString());
    }

    /**
     * Sets the vertex and fragment shaders to their respective variables
     * @param vs - String of the vertex shader
     * @param fs - String of the Fragment shader
     */
    private void setup(String vs, String fs) {
        this.mVertexS = vs;
        this.mFragmentS = fs;

        // create the program
        if (createProgram() != 1) {
            throw new RuntimeException("Error at creating shaders");
        };
    }

    /**
     * loads the shaders and creates a shader program to attach them to
     * @return - int representation of the success/failure of the program creation
     */
    private int createProgram() {
        // Vertex shader
        mVertexShader = loadShader(GLES20.GL_VERTEX_SHADER, mVertexS);
        if (mVertexShader == 0) {
            return 0;
        }

        // pixel shader
        mPixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentS);
        if (mPixelShader == 0) {
            return 0;
        }

        // Create the program
        mProgram = GLES20.glCreateProgram();
        if (mProgram != 0) {
            GLES20.glAttachShader(mProgram, mVertexShader);
            GLES20.glAttachShader(mProgram, mPixelShader);
            GLES20.glLinkProgram(mProgram);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link _program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(mProgram));
                GLES20.glDeleteProgram(mProgram);
                mProgram = 0;
                return 0;
            }
        }
        else
            Log.d("CreateProgram", "Could not create program");

        return 1;
    }

    /**
     * Loads a shader given an int representing its shader type(fragment/vertex) and source as a string
     * @param shaderType - int showing if it is vertex or fragment shader
     * @param source - String holding the source of the shader to be loaded
     * @return
     */
    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * returns the created shader program
     * @return
     */
    public int getProgram() {
        return mProgram;
    }
}
