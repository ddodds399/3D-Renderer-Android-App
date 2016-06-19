package gd2.gd2render.Shadows;

/**
 * Created by James on 04/12/2015.
 * Used http://www.codeproject.com/Articles/822380/Shadow-Mapping-with-Android-OpenGL-ES as tutorial and base
 */
public class ShadowConstants {
    public static final String MVP_MATRIX_UNIFORM = "uMVPMatrix";
    public static final String MV_MATRIX_UNIFORM = "uMVMatrix";
    public static final String NORMAL_MATRIX_UNIFORM = "uNormalMatrix";
    public static final String LIGHT_POSITION_UNIFORM = "uLightPos";
    public static final String POSITION_ATTRIBUTE = "aPosition";
    public static final String NORMAL_ATTRIBUTE = "aNormal";
    public static final String COLOR_ATTRIBUTE = "aColor";

    public static final String SHADOW_TEXTURE = "uShadowTexture";
    public static final String SHADOW_PROJ_MATRIX = "uShadowProjMatrix";
    public static final String SHADOW_X_PIXEL_OFFSET = "uxPixelOffset";
    public static final String SHADOW_Y_PIXEL_OFFSET = "uyPixelOffset";

    public static final String SHADOW_POSITION_ATTRIBUTE = "aShadowPosition";
}
