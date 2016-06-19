package gd2.gd2render.Utils;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Dean on 30/10/2015.
 */

/*
    Learn Open GLES tutorial used for Strings of shader source code and moving compiling into a separate method.
    http://www.learnopengles.com
 */
public class ProgramShader {

    private int vertexShaderHandle; // - To hold vertex shader
    private int fragmentShaderHandle; // - To hold fragment shader
    private int programHandle; // - To hold the created program object

    /**
     * Constructor sets up appropriate shader for scene based on user input string
     *
     * @param shaderType - passed in when creating a new shader, allows the correct type of shader to be constructed.
     */
    public ProgramShader(String shaderType) {
        //Shader with ambient lighting and simple colouring
        if (shaderType.equals("simple")) {
            setupSimpleShader();
            setupFragmentShader();
            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{"a_Position", "a_Color"});

            //Shader that allows perVertex lighting
        } else if (shaderType.equals("perVertex")) {
            setupPerVertexShader();
            setupFragmentShader();
            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal"});

            //Shader that allows perPixel lighting
        } else if (shaderType.equals("perPixel")) {
            setupPerPixelShader();
            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal"});

            //Shader for the point light source
        } else if (shaderType.equals("point")) {
            setupPointShader();
            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{"a_Position"});

            //Shader allowing textures to be displayed
        } else if (shaderType.equals("texture")) {
            setupTextureShader();
            programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});
        }
    }

    private void setupSimpleShader() {
        //Define the simple shader source code
        final String simpleShader =
                "uniform mat4 u_MVPMatrix; \n"
                        + "attribute vec4 a_Position; \n"
                        + "attribute vec4 a_Color; \n"
                        + "varying vec4 v_Color; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " v_Color = a_Color; \n"
                        + " gl_Position = u_MVPMatrix \n"
                        + " * a_Position; \n"
                        + "} \n";
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, simpleShader);
    }

    private void setupPerVertexShader() {
        //Define the vertex shader source code
        final String vertexShader =
                "uniform mat4 u_MVPMatrix; \n"
                        + "uniform mat4 u_MVMatrix; \n"
                        + "uniform vec3 u_LightPos; \n"
                        + "attribute vec4 a_Position; \n"
                        + "attribute vec4 a_Color; \n"
                        + "attribute vec3 a_Normal; \n"
                        + "varying vec4 v_Color; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " vec3 modelViewVertex = vec3(u_MVMatrix * a_Position); \n"
                        + " vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0)); \n"
                        + " float distance = length(u_LightPos - modelViewVertex); \n"
                        + " vec3 lightVector = normalize(u_LightPos - modelViewVertex); \n"
                        + " float diffuse = max(dot(modelViewNormal, lightVector), 0.1); \n"
                        + " diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance))); \n"
                        + " v_Color = a_Color * diffuse; \n"
                        + " gl_Position = u_MVPMatrix * a_Position; \n"
                        + "} \n";
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
    }

    private void setupPointShader() {
        //Define the vertex shader source code
        final String pointVertexShader =
                "uniform mat4 u_MVPMatrix; \n"
                        + "attribute vec4 a_Position; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " gl_Position = u_MVPMatrix \n"
                        + " * a_Position; \n"
                        + " gl_PointSize = 5.0; \n"
                        + "} \n";
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
        // Define the fragment shader source code
        final String pointFragmentShader =
                "precision mediump float; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " gl_FragColor = vec4(1.0, \n"
                        + " 1.0, 1.0, 1.0); \n"
                        + "} \n";
        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);

    }

    private void setupPerPixelShader() {
        // Define our per-pixel lighting shader source code.
        final String perPixelVertexShader =
                "uniform mat4 u_MVPMatrix; \n"
                        + "uniform mat4 u_MVMatrix; \n"
                        + "attribute vec4 a_Position; \n"
                        + "attribute vec4 a_Color; \n"
                        + "attribute vec3 a_Normal; \n"
                        + "varying vec3 v_Position; \n"
                        + "varying vec4 v_Color; \n"
                        + "varying vec3 v_Normal; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " v_Position = vec3(u_MVMatrix * a_Position); \n"
                        + " v_Color = a_Color; \n"
                        + " v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0)); \n"
                        + " gl_Position = u_MVPMatrix * a_Position; \n"
                        + "} \n";
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
        // Define the fragment shader source code
        final String perPixelFragmentShader =
                "precision mediump float; \n"
                        + "uniform vec3 u_LightPos; \n"
                        + "varying vec3 v_Position; \n"
                        + "varying vec4 v_Color; \n"
                        + "varying vec3 v_Normal; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " float distance = length(u_LightPos - v_Position); \n"
                        + " vec3 lightVector = normalize(u_LightPos - v_Position); \n"
                        + " float diffuse = max(dot(v_Normal, lightVector), 0.1); \n"
                        + " diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance))); \n"
                        + " gl_FragColor = v_Color * diffuse; \n"
                        + "} \n";
        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    private void setupTextureShader() {
        final String perPixelVertexShader =
                "uniform mat4 u_MVPMatrix; \n"
                        + "uniform mat4 u_MVMatrix; \n"
                        + "attribute vec4 a_Position; \n"
                        + "attribute vec4 a_Color; \n"
                        + "attribute vec3 a_Normal; \n"
                        + "attribute vec2 a_TexCoordinate; \n"
                        + "varying vec3 v_Position; \n"
                        + "varying vec4 v_Color; \n"
                        + "varying vec3 v_Normal; \n"
                        + "varying vec2 v_TexCoordinate; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " v_Position = vec3(u_MVMatrix * a_Position); \n"
                        + " v_Color = a_Color; \n"
                        + "v_TexCoordinate = a_TexCoordinate; \n"
                        + " v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0)); \n"
                        + " gl_Position = u_MVPMatrix * a_Position; \n"
                        + "} \n";
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
        // Define the fragment shader source code
        final String perPixelFragmentShader =
                "precision mediump float; \n"
                        + "uniform vec3 u_LightPos; \n"
                        + "uniform sampler2D u_Texture; \n"
                        + "varying vec3 v_Position; \n"
                        + "varying vec4 v_Color; \n"
                        + "varying vec3 v_Normal; \n"
                        + "varying vec2 v_TexCoordinate; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " float distance = length(u_LightPos - v_Position); \n"
                        + " vec3 lightVector = normalize(u_LightPos - v_Position); \n"
                        + " float diffuse = max(dot(v_Normal, lightVector), 0.1); \n"
                        + " diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance))); \n"
                        + " diffuse = diffuse + 0.3; \n"
                        + " gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate)); \n"
                        + "} \n";
        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    private void setupFragmentShader() {
        // Define the fragment shader source code
        final String fragmentShader =
                "precision mediump float; \n"
                        + "varying vec4 v_Color; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " gl_FragColor = v_Color; \n"
                        + "} \n";

        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
    }

    /**
     * Getter for the handle to the create program
     *
     * @return - the program handle.
     */
    public int getProgramHandle() {
        return programHandle;
    }

    /**
     * Used to compile a shader in each setup method.
     *
     * @param shaderType   The shader type.
     * @param shaderSource The source code.
     * @return The handle to the shader.
     */
    private int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = GLES20.glCreateShader(shaderType);
        if (shaderHandle != 0) {
            // Pass in the shader source code.
            GLES20.glShaderSource(shaderHandle, shaderSource);
            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);
            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0) {
                //Log message with error info about the shader.
                Log.e("Shader Creation Failure", "Failed to create shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }
        if (shaderHandle == 0) {
            throw new RuntimeException("Error creating shader.");
        }
        return shaderHandle;
    }

    /**
     * Used to create a program to which the shaders are attached.
     *
     * @param vertexShaderHandle   An OpenGL handle to an already-compiled vertex shader.
     * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @param attributes           Attributes that need to be bound to the program.
     * @return An OpenGL handle to the program.
     */
    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();
        if (programHandle != 0) {
            // Attach the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);
            // Attach the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);
            // Bind attributes using for loop
            if (attributes != null) {
                for (int i = 0; i < attributes.length; i++) {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }
            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);
            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
            // If the link failed, delete the program.
            if (linkStatus[0] == 0) {
                //Log message with error info about the program.
                Log.e("Program shader failure", "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }
        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }
        return programHandle;
    }

}
