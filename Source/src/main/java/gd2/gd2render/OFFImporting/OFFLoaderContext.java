package gd2.gd2render.OFFImporting;

import static gd2.gd2render.OFFImporting.OFFLoaderHelper.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFLoaderContext {

    //Variables created and initialised
    private static final String OFF_BINARY_HEADER_SUFFIX = "OFF BINARY";
    private static final String OFF_HEADER = "OFF";
    private static final String OFF_COLOR_HEADER = "COFF";
    private final IOFFLoader mLoader;
    private final OFFObject mOffObject = new OFFObject();
    private int mVertexCount = 0;
    private int mFaceCount = 0;

    public OFFLoaderContext(IOFFLoader loader) {
        this.mLoader = loader;
    }

    public OFFObject getOffObject() {
        return mOffObject;
    }

    /**
     * Reads the header of the .OFF file.
     * This makes sure the correct filetype has been loaded.
     * @param reader reader containing file data. The head is read and checked against set strings.
     * @throws IOException
     */
    public void readHeader(BufferedReader reader) throws IOException {
        final String header = readContentLineSingle(reader);
        if (OFF_BINARY_HEADER_SUFFIX.equalsIgnoreCase(header)) {
            throw new IOException("Binary OFF not supported.");
        }
        if (!header.endsWith(OFF_HEADER)) {
            throw new IOException("Missing header.");
        }
        if (OFF_HEADER.equalsIgnoreCase(header)) {
            return;
        }
        if (OFF_COLOR_HEADER.equalsIgnoreCase(header)) {
            mOffObject.setHasVertexColors(true);
            return;
        }
        throw new IOException("Unsupported header.");
    }

    /**
     * Reads the next line down from the header of the .OFF file.
     * This contains the number of vertices, faces and edges.
     * Edges however is ignored.
     * @param reader reader containing file data. The second line is read and checked if the values are correct.
     * @throws IOException
     */
    public void readDimensions(BufferedReader reader) throws IOException {
        final String[] segments = readContentLineMultiple(reader);
        if (segments.length < 3) {
            throw new IOException("Incomplete count indicators.");
        }
        mVertexCount = parseIntSafe(segments[0]);
        if (mVertexCount > mLoader.getMaxVertexCount()) {
            throw new IOException("Number of vertices exceeds max range.");
        }
        mFaceCount = parseIntSafe(segments[1]);
        if (mFaceCount > mLoader.getMaxFaceCount()) {
            throw new IOException("Number of faces exceeds max range.");
        }
    }

    /**
     * Used to read the vertices with the file and apply to an OffObject.
     * @param reader reader containing file data. The vertices are read and placed in a list with an OffObject.
     * @throws IOException
     */
    public void readVertices(BufferedReader reader) throws IOException {
        for (int i = 0; i < mVertexCount; ++i) {
            mOffObject.getVertices().add(readVertex(reader));
        }
    }

    /**
     * Used to read the lines that correspond to vertices with the file.
     * @param reader reader containing file data. The vertices are read and returned to be placed in a list with an OffObject.
     * @return an OFFVertex conatining an x,y,z and sometimes r,g,b,a value if applicable.
     * @throws IOException
     */
    private OFFVertex readVertex(BufferedReader reader) throws IOException {
        final String[] segments = readContentLineMultiple(reader);
        if (segments.length < 3) {
            throw new IOException("Insufficient coordinates.");
        }
        final OFFVertex vertex = new OFFVertex();
        vertex.x = parseFloatSafe(segments[0]);
        vertex.y = parseFloatSafe(segments[1]);
        vertex.z = parseFloatSafe(segments[2]);

        mOffObject.getaVertices().add(parseFloatSafe(segments[0]));
        mOffObject.getaVertices().add(parseFloatSafe(segments[1]));
        mOffObject.getaVertices().add(parseFloatSafe(segments[2]));

        if (mOffObject.getHasVertexColors()) {
            if (segments.length < 7) {
                throw new IOException("Missing vertex color.");
            }
            vertex.r = parseColorSegmentSafe(segments[3]);
            vertex.g = parseColorSegmentSafe(segments[4]);
            vertex.b = parseColorSegmentSafe(segments[5]);
            vertex.a = parseColorSegmentSafe(segments[6]);

            mOffObject.getaVertices().add(parseFloatSafe(segments[3]));
            mOffObject.getaVertices().add(parseFloatSafe(segments[4]));
            mOffObject.getaVertices().add(parseFloatSafe(segments[5]));
            mOffObject.getaVertices().add(parseFloatSafe(segments[6]));

        }
        return vertex;
    }

    /**
     * Used to read the faces with the file and apply to an OffObject.
     * @param reader reader containing file data. The faces are read and placed in a list with an OffObject.
     * @throws IOException
     */
    public void readFaces(BufferedReader reader) throws IOException {
        for (int i = 0; i < mFaceCount; ++i) {
            mOffObject.getFaces().add(readFace(reader));
        }
    }

    /**
     * Used to read the lines that correspond to faces with the file.
     * @param reader reader containing file data. The faces are read and returned to be placed in a list with an OffObject.
     * @return an OFFVertex conatining a reference that connects vertices and sometimes r,g,b,a values if applicable.
     * @throws IOException
     */
    private OFFFace readFace(BufferedReader reader) throws IOException {
        final String[] segments = readContentLineMultiple(reader);
        if (segments.length == 0) {
            throw new IOException("Missing face information");
        }
        final int vertexRefCount = parseIntSafe(segments[0]);
        if (vertexRefCount > segments.length - 1) {
            throw new IOException("Insufficient face data.");
        }
        final OFFFace face = new OFFFace();
        for (int i = 0; i < vertexRefCount; ++i) {
            final int vertexRef = parseIntSafe(segments[i + 1]);
            final short vertexRef2 = parseShortSafe(segments[i + 1]);
            mOffObject.getIndices().add(vertexRef2);
            face.getVertexReferences().add(vertexRef);
        }
        if (segments.length - 1 >= vertexRefCount + 3) {
            face.setHasColor(true);
            face.r = parseColorSegmentSafe(segments[vertexRefCount + 1]);
            face.g = parseColorSegmentSafe(segments[vertexRefCount + 2]);
            face.b = parseColorSegmentSafe(segments[vertexRefCount + 3]);
            if (segments.length - 1 >= vertexRefCount + 4) {
                face.a = parseColorSegmentSafe(segments[vertexRefCount + 4]);
            } else {
                face.a = 1.0f;
            }
        }
        return face;
    }

}
