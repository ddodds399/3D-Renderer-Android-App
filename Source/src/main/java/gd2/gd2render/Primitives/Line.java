package gd2.gd2render.Primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Line {


    private float vertices[] = {
            1.0f, 0.0f, 0.0f, //Point 1
            5.0f, 0.0f, 0.f    //Point 2
    };

    private float colours[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private FloatBuffer vBuff, colourBuffer;

    float rAngle = 0f, r2 = 0f, r3 = 0f, r4 = 0f;

    public Line() {
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
    }

    public void draw(GL10 gl) {

        gl.glRotatef(rAngle, r2, r3, r4);
        gl.glFrontFace(GL10.GL_CW);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vBuff);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colourBuffer);

        gl.glDrawArrays(GL10.GL_LINES, 0, vertices.length / 2);
        gl.glLineWidth(6.0f);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

    }

    public void rotate(float f1, float f2, float f3, float f4) {
        rAngle = f1;
        r2 = f2;
        r3 = f3;
        r4 = f4;
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

    public float[] getVertices() {
        return vertices;
    }

    public float[] getColours() {
        return colours;
    }
}
