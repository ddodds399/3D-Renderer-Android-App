package gd2.gd2render.OFFImporting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFFace {

    /**
     * The Red component of the color of this face.
     */
    public float r = 0.666f;
    /**
     * The Green component of the color of this face.
     */
    public float g = 0.666f;
    /**
     * The Blue component of the color of this face.
     */
    public float b = 0.666f;
    /**
     * The Alpha component of the color of this face.
     */
    public float a = 0.666f;
    private final List<Integer> vertexReferences = new ArrayList<Integer>(4);
    private boolean hasColor = false;
    /**
     * Creates a new OFFFace.
     */
    public OFFFace() {
        super();
    }
    /**
     * Sets whether this face has color information.
     * @param hasColor true if this face has color information,
     * false otherwise.
     */
    public void setHasColor(boolean hasColor) {
        this.hasColor = hasColor;
    }
    /**
     * Returns whether this face has color information.
     * By default faces don't have color information.
     * @return true if this face has color information,
     * false otherwise.
     */
    public boolean getHasColor() {
        return hasColor;
    }
    /**
     * Returns a modifiable list of vertex references.
     * @return a non-null writable List
     */
    public List<Integer> getVertexReferences() {
        for(int i:vertexReferences){
            //System.out.println("Ref: " + i);
        }
        return vertexReferences;
    }

}
