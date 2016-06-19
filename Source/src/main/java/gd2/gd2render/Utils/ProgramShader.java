package gd2.gd2render.Utils;

import android.opengl.GLES20;
import android.util.Log;

/*
    Learn Open GLES tutorial used for Strings of shader source code and moving compiling into a separate method.
    http://www.learnopengles.com
 */
public class ProgramShader {

    private int mVertexShaderHandle; // - To hold vertex shader
    private int mFragmentShaderHandle; // - To hold fragment shader
    private int mProgramHandle; // - To hold the created program object

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
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position", "a_Color"});

            //Shader that allows perVertex lighting
        } else if (shaderType.equals("perVertex")) {
            setupPerVertexShader();
            setupFragmentShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal"});

            //Shader that allows perPixel lighting
        } else if (shaderType.equals("perPixel")) {
            setupPerPixelShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal"});

            //Shader for the point light source
        } else if (shaderType.equals("point")) {
            setupPointShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position"});

            //Shader allowing textures to be displayed
        } else if (shaderType.equals("texture")) {
            setupTextureShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});
        } else if (shaderType.equals("particle")) {
            setupParticleShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_lifetime", "a_startPosition", "a_endPosition"});
        } else if (shaderType.equals("skyBox")) {
            setupSkyBoxShader();
            mProgramHandle = createAndLinkProgram(mVertexShaderHandle, mFragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal", "a_TexCoordinate"});
        }
    }

    /**
     * Method for defining and compiling a shader program to
     * simply draw the primitive
     */
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, simpleShader);
    }

    /**
     * Method for defining and compiling shader programs for use in
     * simple per vertex lighting demonstrations.
     */
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
    }

    /**
     * Programs used for drawing the point to represent the
     * light source position
     */
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
        // Define the fragment shader source code
        final String pointFragmentShader =
                "precision mediump float; \n"
                        + "void main() \n"
                        + "{ \n"
                        + " gl_FragColor = vec4(1.0, \n"
                        + " 1.0, 1.0, 1.0); \n"
                        + "} \n";
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, pointFragmentShader);

    }

    /**
     * Method for defining and compiling vertex and fragment shader programs
     * for use in per-pixel lighting demonstrations
     */
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
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
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    /**
     * Method for defining and compiling the vertex and fragment shaders
     * for use in drawing textured primitives
     */
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
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
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    /**
     * Method to define the vertex and fragment shaders for use
     * when drawing the skybox elements.
     */
    private void setupSkyBoxShader() {
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
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
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
                        + " gl_FragColor = (texture2D(u_Texture, v_TexCoordinate)); \n"
                        + "} \n";
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    /**
     * Method for defining and compiling shader programs for use in
     * drawing particles in the particle demonstration
     */
    private void setupParticleShader() {
        final String perPixelVertexShader =
                "uniform float u_time;                                \n" +
                        "uniform vec3 u_centerPosition;                       \n" +
                        "attribute float a_lifetime;                          \n" +
                        "attribute vec3 a_startPosition;                      \n" +
                        "attribute vec3 a_endPosition;                        \n" +
                        "varying float v_lifetime;                            \n" +
                        "void main()                                          \n" +
                        "{                                                    \n" +
                        "  if ( u_time <= a_lifetime )                        \n" +
                        "  {                                                  \n" +
                        "    gl_Position.xyz = a_startPosition +              \n" +
                        "                      (u_time * a_endPosition);      \n" +
                        "    gl_Position.xyz += u_centerPosition;             \n" +
                        "    gl_Position.w = 1.0;                             \n" +
                        "  }                                                  \n" +
                        "  else                                               \n" +
                        "     gl_Position = vec4( -1000, -1000, 0, 0 );       \n" +
                        "  v_lifetime = 1.0 - ( u_time / a_lifetime );        \n" +
                        "  v_lifetime = clamp ( v_lifetime, 0.0, 1.0 );       \n" +
                        "  gl_PointSize = ( v_lifetime * v_lifetime ) * 40.0; \n" +
                        "}\n";
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, perPixelVertexShader);
        // Define the fragment shader source code
        final String perPixelFragmentShader =
                "precision mediump float;                             \n" +
                        "uniform vec4 u_color;                                \n" +
                        "varying float v_lifetime;                            \n" +
                        "uniform sampler2D s_texture;                         \n" +
                        "void main()                                          \n" +
                        "{                                                    \n" +
                        "  vec4 texColor;                                     \n" +
                        "  texColor = texture2D( s_texture, gl_PointCoord );  \n" +
                        "  gl_FragColor = vec4( u_color ) * texColor;         \n" +
                        "  gl_FragColor.a *= v_lifetime;                      \n" +
                        "}\n";
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, perPixelFragmentShader);
    }

    /**
     * Method for defining and compiling shader programs for use in
     * drawing objects with reflectivity.
     */
    private void setupReflectionShader() {
        final String reflectVertexShader =
                "precision highp float;\n" +
                        "\n" +
                        "// Matrices\n" +
                        "uniform mat4 u_mvMatrix;\n" +
                        "uniform mat4 u_mvpMatrix;\n" +
                        "\n" +
                        "// Attributes\n" +
                        "attribute vec4 a_position; \n" +
                        "attribute vec3 a_normal;\n" +
                        "\n" +
                        "// Varyings\n" +
                        "varying vec3 v_ecPosition;\n" +
                        "varying vec3 v_ecNormal;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    \n" +
                        "    // Define position and normal in model coordinates\n" +
                        "    vec4 mcPosition = a_position;\n" +
                        "    vec3 mcNormal = a_normal;\n" +
                        "    \n" +
                        "    // Calculate position in eye space\n" +
                        "    vec3 ecPosition = vec3(u_mvMatrix * mcPosition); \n" +
                        "    \n" +
                        "    // Calculate and normalize eye space normal\n" +
                        "    vec3 ecNormal = vec3(u_mvMatrix * vec4(mcNormal, 0.0));\n" +
                        "    ecNormal = ecNormal / length(ecNormal);\n" +
                        "    \n" +
                        "    // Set varyings\n" +
                        "    v_ecNormal = ecNormal;\n" +
                        "    v_ecPosition = ecPosition;\n" +
                        "    \n" +
                        "    gl_Position = u_mvpMatrix * mcPosition;    \n" +
                        "    \n" +
                        "}";

        final String reflectFragmentShader =
                "precision highp float;\n" +
                        "\n" +
                        "// Environment map\n" +
                        "uniform lowp samplerCube s_environmentMap;\n" +
                        "\n" +
                        "// Varyings\n" +
                        "varying vec3 v_ecPosition;\n" +
                        "varying vec3 v_ecNormal;\n" +
                        "\n" +
                        "void main() { \n" +
                        "    \n" +
                        "    // Normalize v_ecNormal\n" +
                        "    vec3 ecNormal = v_ecNormal / length(v_ecNormal);\n" +
                        "    \n" +
                        "    // Calculate reflection vector\n" +
                        "    vec3 ecEyeReflection = reflect(v_ecPosition, ecNormal);\n" +
                        "    \n" +
                        "    // Sample environment color\n" +
                        "    vec4 environmentColor = textureCube(s_environmentMap, ecEyeReflection);\n" +
                        "    \n" +
                        "    gl_FragColor = environmentColor;\n" +
                        "}";
        mVertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, reflectVertexShader);
        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, reflectFragmentShader);
    }

    /**
     * Method for defining and compiling shader programs in order to
     * draw shadows in demonstrations.
     */
    private void setupShadowMapShader() {
        final String shadowFragmentShader =
                "precision highp float;\n" +
                        "\n" +
                        "varying vec4 vPosition; \n" +
                        "\n" +
                        "// from Fabien Sangalard's DEngine \n" +
                        "vec4 pack (float depth)\n" +
                        "{\n" +
                        "    const vec4 bitSh = vec4(256.0 * 256.0 * 256.0,\n" +
                        "                            256.0 * 256.0,\n" +
                        "                            256.0,\n" +
                        "                            1.0);\n" +
                        "    const vec4 bitMsk = vec4(0,\n" +
                        "                             1.0 / 256.0,\n" +
                        "                             1.0 / 256.0,\n" +
                        "                             1.0 / 256.0);\n" +
                        "    vec4 comp = fract(depth * bitSh);\n" +
                        "    comp -= comp.xxyz * bitMsk;\n" +
                        "    return comp;\n" +
                        "}\n" +
                        "\n" +
                        "void main() {\n" +
                        "    // the depth\n" +
                        "    float normalizedDistance  = vPosition.z / vPosition.w;\n" +
                        "    // scale -1.0;1.0 to 0.0;1.0 \n" +
                        "    normalizedDistance = (normalizedDistance + 1.0) / 2.0;\n" +
                        "\n" +
                        "    // pack value into 32-bit RGBA texture\n" +
                        "    gl_FragColor = pack(normalizedDistance);\n" +
                        "}";

        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, shadowFragmentShader);
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

        mFragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
    }

    /**
     * Getter for the handle to the create program
     *
     * @return - the program handle.
     */
    public int getProgramHandle() {
        return mProgramHandle;
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
     * @param mVertexShaderHandle   An OpenGL handle to an already-compiled vertex shader.
     * @param mFragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @param attributes           Attributes that need to be bound to the program.
     * @return An OpenGL handle to the program.
     */
    private int createAndLinkProgram(final int mVertexShaderHandle, final int mFragmentShaderHandle, final String[] attributes) {
        int mProgramHandle = GLES20.glCreateProgram();
        if (mProgramHandle != 0) {
            // Attach the vertex shader to the program.
            GLES20.glAttachShader(mProgramHandle, mVertexShaderHandle);
            // Attach the fragment shader to the program.
            GLES20.glAttachShader(mProgramHandle, mFragmentShaderHandle);
            // Bind attributes using for loop
            if (attributes != null) {
                for (int i = 0; i < attributes.length; i++) {
                    GLES20.glBindAttribLocation(mProgramHandle, i, attributes[i]);
                }
            }
            // Link the two shaders together into a program.
            GLES20.glLinkProgram(mProgramHandle);
            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(mProgramHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
            // If the link failed, delete the program.
            if (linkStatus[0] == 0) {
                //Log message with error info about the program.
                Log.e("Program shader failure", "Error compiling program: " + GLES20.glGetProgramInfoLog(mProgramHandle));
                GLES20.glDeleteProgram(mProgramHandle);
                mProgramHandle = 0;
            }
        }
        if (mProgramHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }
        return mProgramHandle;
    }

}
