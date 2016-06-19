package gd2.gd2render.OFFImporting;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dean on 30/11/2015.
 */
public interface IOFFLoader {

    /**
     * Default maximum vertex count for a model.
     */
    public static final int DEFAULT_MAX_VERTEX_COUNT = 65536;
    /**
     * Default maximum face count for a model.
     */
    public static final int DEFAULT_MAX_FACE_COUNT = 65536;
    /**
     * Sets a limit on the number of vertices that a model being loaded can
     * have.
     *
     * @param count maximum number of vertices
     */
    public void setMaxVertexCount(int count);
    /**
     * Returns the maximum number of vertices that a model being loaded can
     * have.
     * By default this value is equal to
     * DEFAULT_MAX_VERTEX_COUNT.
     *
     * @return maximum number of vertices
     */
    public int getMaxVertexCount();
    /**
     * Sets the maximum number of faces that a model being loaded can have.
     *
     * @param count maximum number of faces
     */
    public void setMaxFaceCount(int count);
    /**
     * Returns the maximum number of faces that a model being loaded can have.
     * By default this value is equal to
     * DEFAULT_MAX_FACE_COUNT.
     *
     * @return maximum number of faces
     */
    public int getMaxFaceCount();
    /**
     * Parses an OFF resource from the specified InputStream and returns
     * the OFF model.
     *
     * @param in input stream from which the model will be read.
     * @return an instance of OFFmObjectect
     * @throws IOException if an I/O error occurs
     */
    public OFFObject load(InputStream in) throws IOException;

}
