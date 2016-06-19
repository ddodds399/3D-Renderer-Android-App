package gd2.gd2render;

import android.test.InstrumentationTestCase;

import gd2.gd2render.Primitives.Triangle;

/**
 * Created by SpoonPhilosophy on 03/11/2015.
 */
public class TriangleTest extends InstrumentationTestCase {
    Triangle triangle;

    private float[] vertices= {1f,0f,
            -1f,-0f,
            0f,-1f };

    public void testVertices() {
        triangle = new Triangle();
        for (int i=0; i<triangle.getVertices().length; i++) {
            assertEquals(triangle.getVertices()[i], vertices[i]);
        }
    }
}
