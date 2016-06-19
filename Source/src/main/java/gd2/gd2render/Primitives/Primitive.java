package gd2.gd2render.Primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class Primitive {

    private FloatBuffer vBuff;
    private FloatBuffer cBuff;
    private float rAngle = 0f, r2 = 0f, r3 = 0f, r4 = 0f;
    private int vElement = 1;

    private float vertices[], colors[];

    public Primitive(float[] verts, float[] cols) {
        vertices = verts;
        colors = cols;

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vBuff = byteBuf.asFloatBuffer();
        vBuff.put(vertices);
        vBuff.position(0);

        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        cBuff = byteBuf.asFloatBuffer();
        cBuff.put(colors);
        cBuff.position(0);
    }

    public void draw(GL10 gl) {

    }

}
