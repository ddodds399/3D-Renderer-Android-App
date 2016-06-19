package gd2.gd2render.OFFImporting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFLoader implements IOFFLoader{

    //Initial variables set to match the interface
    private int mMaxVertexCount = DEFAULT_MAX_VERTEX_COUNT;
    private int mMaxFaceCount = DEFAULT_MAX_FACE_COUNT;

    public OFFLoader() {
        super();
    }

    //Getters and Setters for maximum number of vertices and faces an mObjectect is allowed to have.
    @Override
    public void setMaxVertexCount(int count) {
        this.mMaxVertexCount = count;
    }
    @Override
    public int getMaxVertexCount() {
        return mMaxVertexCount;
    }
    @Override
    public void setMaxFaceCount(int count) {
        this.mMaxFaceCount = count;
    }
    @Override
    public int getMaxFaceCount() {
        return mMaxFaceCount;
    }

    /**
     * Method used in which to load an .OFF file.
     * @param in input stream from which the model will be read.
     * @return OffObject - returns loaded object file
     * @throws IOException
     */
    @Override
    public OFFObject load(InputStream in) throws IOException {
        try {
            final Reader reader = new InputStreamReader(in);
            final BufferedReader bufferedReader = new BufferedReader(reader);
            return load(bufferedReader);
        } finally {
            in.close();
        }
    }

    /**
     * Called from within the public method of the same name.
     * .OFF file data is passed into loader context to read vertex and index information.
     * @param reader - passed in from above, will contain data from .OFF file.
     * @return OffObject - returns the .OFF object data to the public method.
     * @throws IOException
     */
    private OFFObject load(BufferedReader reader) throws IOException {
        final OFFLoaderContext context = new OFFLoaderContext(this);
        context.readHeader(reader);
        context.readDimensions(reader);
        context.readVertices(reader);
        context.readFaces(reader);
        return context.getOffObject();
    }

}
