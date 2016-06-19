package gd2.gd2render.OFFImporting;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFVertex {
    /**
     * The X coordinate of this vertex.
     */
    public float x = 0.0f;
    /**
     * The Y coordinate of this vertex.
     */
    public float y = 0.0f;
    /**
     * The Z coordinate of this vertex.
     */
    public float z = 0.0f;
    /**
     * The Red component of the color of this vertex.
     */
    public float r = 0.0f;
    /**
     * The Green component of the color of this vertex.
     */
    public float g = 0.0f;
    /**
     * The Blue component of the color of this vertex.
     */
    public float b = 0.0f;
    /**
     * The Alpha component of the color of this vertex.
     */
    public float a = 0.0f;
    /**
     * Creates a new OFFVertex.
     */
    public OFFVertex() {
        super();
    }
    /**
     * Creates a new @link OFFVertex with the specified coordinates.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public OFFVertex(float x, float y, float z) {
        super();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OFFVertex)) {
            return false;
        }
        final OFFVertex other = (OFFVertex) obj;
        return (Float.floatToRawIntBits(x) == Float.floatToRawIntBits(other.x))
                && (Float.floatToRawIntBits(y) == Float.floatToRawIntBits(other.y))
                && (Float.floatToRawIntBits(z) == Float.floatToRawIntBits(other.z))
                && (Float.floatToRawIntBits(r) == Float.floatToRawIntBits(other.r))
                && (Float.floatToRawIntBits(g) == Float.floatToRawIntBits(other.g))
                && (Float.floatToRawIntBits(b) == Float.floatToRawIntBits(other.b))
                && (Float.floatToRawIntBits(a) == Float.floatToRawIntBits(other.a));
    }
    @Override
    public int hashCode() {
        int result = Float.floatToRawIntBits(x);
        result += result * 31 + Float.floatToRawIntBits(y);
        result += result * 31 + Float.floatToRawIntBits(z);
        result += result * 31 + Float.floatToRawIntBits(r);
        result += result * 31 + Float.floatToRawIntBits(g);
        result += result * 31 + Float.floatToRawIntBits(b);
        result += result * 31 + Float.floatToRawIntBits(a);
        return result;
    }

}
