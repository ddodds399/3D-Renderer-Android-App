package gd2.gd2render.Primitives;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {

    //creating and initialising the buffers that will be used to hold the cube information
    private FloatBuffer vBuff;
    private FloatBuffer cBuff;
    private ByteBuffer iBuff;
    private float rAngle = 0f, r2 = 0f, r3 = 0f, r4 = 0f;
    private int vElement = 1;
    private float sX = 1f, sY = 1f, sZ = 1f;
    private float tX, tY, tZ;

    //creating and initialising arrays to hold the vertices, their colors, and the indices of the cube
    private float vertices[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f
    };
    private float colors[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.5f, 0.0f, 1.0f,
            1.0f, 0.5f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };

    private byte indices[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };

    /**
     * Constructor for the cube class
     */
    public Cube() {
        //Adding vertex information to the vertex buffer
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vBuff = byteBuf.asFloatBuffer();
        vBuff.put(vertices);
        vBuff.position(0);

        //Adding color information to the color buffer
        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        cBuff = byteBuf.asFloatBuffer();
        cBuff.put(colors);
        cBuff.position(0);

        //adding index information to the index buffer
        iBuff = ByteBuffer.allocateDirect(indices.length);
        iBuff.put(indices);
        iBuff.position(0);
    }

    /**
     * method used to draw the cube
     */
    public void draw(GL10 gl) {
        gl.glRotatef(rAngle, r2, r3, r4);
        gl.glScalef(sX, sY, sZ);
        gl.glTranslatef(tX, tY, tZ);
        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuff);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, cBuff);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        if (vElement == 0) {
            gl.glDrawElements(GL10.GL_LINE_STRIP, 36, GL10.GL_UNSIGNED_BYTE, iBuff);
        } else {
            gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, iBuff);
        }


        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }

    //method used to rotate the cube
    public void rotate(float f1, float f2, float f3, float f4) {
        rAngle = f1;
        r2 = f2;
        r3 = f3;
        r4 = f4;
    }

    public void scale(float a, float b, float c) {
        sX = a;
        sY = b;
        sZ = c;
    }

    //method to translate the vertices by x y z
    public void translate(float x, float y, float z) {
        tX = x;
        tY = y;
        tZ = z;
    }

    /**
     * method to return rAngle
     *
     * @return - returning the angle
     */
    public float getrAngle() {
        return rAngle;
    }

    public void changeDrawElement() {
        if (vElement == 0) {
            vElement += 1;
        } else {
            vElement = 0;
        }
    }

    //Getter methods for the cube
    public float[] getVertices() {
        return vertices;
    }

    public float[] getColors() {
        return colors;
    }

    public byte[] getIndices() {
        return indices;
    }
}
