package gd2.gd2render.OFFImporting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFObject {

    private final List<OFFVertex> vertices = new ArrayList<OFFVertex>();
    private final List<OFFFace> faces = new ArrayList<OFFFace>();
    private final List<Short> indices = new ArrayList<Short>();
    private final List<Float> aVertices = new ArrayList<Float>();
    private boolean hasVertexColors = false;
    /**
     * Creates a new OFFObject.
     */
    public OFFObject() {
        super();
    }
    /**
     * Returns a modifiable list of vertices.
     *
     * @return a non-null List
     */
    public List<OFFVertex> getVertices() {
        return vertices;
    }
    /**
     * Helper method that returns the vertex with the specified index.
     *
     * @param index index of the vertex
     * @return the vertex at the specified index
     */
    public OFFVertex getVertex(int index) {
        return vertices.get(index);
    }
    /**
     * Returns a modifiable list of faces.
     *
     * @return a non-null List
     */
    public List<OFFFace> getFaces() {
        return faces;
    }
    /**
     * Helper method that returns the face with the specified index.
     *
     * @param index index of the face
     * @return the face at the specified index
     */
    public OFFFace getFace(int index) { return faces.get(index);}

    public List<Float> getaVertices() {
        return aVertices;
    }

    public List<Short> getIndices() {
        return indices;
    }

    public Short getIndex(int index){
        return indices.get(index);
    }

    public Float getAVertex(int index){
        return aVertices.get(index);
    }

    /**
     * Sets whether this object has color information per each vertex.
     *
     * @param hasVertexColors if true then each vertex of this
     * object will have color information, otherwise false
     */
    public void setHasVertexColors(boolean hasVertexColors) {
        this.hasVertexColors = hasVertexColors;
    }
    /**
     * Returns whether this object has color information per each vertex.
     *
     * @return true if each face is vertex-colored,
     * false otherwise.
     */
    public boolean getHasVertexColors() {
        return hasVertexColors;
    }

}
