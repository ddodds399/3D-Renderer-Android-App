package gd2.gd2render.Primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;


public class Triangle {
    //Creating the arrays for the vertices and colors, and creating and initialising the buffers
    //3 Points
    private float[] vertices = {1f, 0f,
            -1f, -0f,
            0f, -1f};
    private float[] colours = {
            1f, 0f, 0f, 1f, //1st Point
            0f, 1f, 0f, 1f, //2nd Point
            0f, 0f, 1f, 1f
    };
    private FloatBuffer vBuff, colourBuffer;

    private short[] pId = {0, 1, 2};
    private ShortBuffer pBuff;

    //Variables to be used for transformations
    float rAngle = 0f, r2 = 0f, r3 = 0f, r4 = 0f;
    private int vElement = 1;

    float sX = 1, sY = 1, sZ = 1;

    /**
     * Constructor for the triangle class
     */
    public Triangle() {
        //Capacity = Vertices * 4.  4 bytes per vertice
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        vBuff = bBuff.asFloatBuffer();
        vBuff.put(vertices);
        vBuff.position(0);

        ByteBuffer aBuff = ByteBuffer.allocateDirect(colours.length * 4);
        aBuff.order(ByteOrder.nativeOrder());
        colourBuffer = aBuff.asFloatBuffer();
        colourBuffer.put(colours);
        colourBuffer.position(0);


        ByteBuffer b2Buff = ByteBuffer.allocateDirect(pId.length * 2);
        b2Buff.order(ByteOrder.nativeOrder());
        pBuff = b2Buff.asShortBuffer();
        pBuff.put(pId);
        pBuff.position(0);
    }

    /**
     * Method used to draw the triangle
     * @param gl - instance of GL10 used to draw the triangle
     */
    public void draw(GL10 gl) {
        gl.glRotatef(rAngle, r2, r3, r4);
        gl.glScalef(sX, sY, sZ);
        gl.glFrontFace(GL10.GL_CW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vBuff);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colourBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, pId.length, GL10.GL_UNSIGNED_SHORT, pBuff);
        if (vElement == 0) {
            gl.glDrawElements(GL10.GL_LINE_STRIP, pId.length, GL10.GL_UNSIGNED_SHORT, pBuff);
        } else {
            gl.glDrawElements(GL10.GL_TRIANGLES, pId.length, GL10.GL_UNSIGNED_SHORT, pBuff);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


    }

    /**
     * Changes the color of the triangle
     * @param gl - GL10 instance
     * @param f1
     * @param f2
     * @param f3
     * @param f4 - all 4 float values will be used to change the color (rgba)
     */
    public void reColour(GL10 gl, float f1, float f2, float f3, float f4) {
        gl.glColor4f(f1, f2, f3, f4);

    }

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

    public void translate(float x, float y, float z) {
        int xyzCheck = 1;
        for (int i = 0; i < vertices.length; i++) {
            if (xyzCheck == 1) {
                vertices[i] += x;
                xyzCheck = 2;
            } else if (xyzCheck == 2) {
                vertices[i] += y;
                xyzCheck = 3;
            } else if (xyzCheck == 3) {
                vertices[i] += z;
                xyzCheck = 1;
            }
        }
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vBuff = byteBuf.asFloatBuffer();
        vBuff.put(vertices);
        vBuff.position(0);
    }

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

    public float[] getVertices() {
        return vertices;
    }
}
