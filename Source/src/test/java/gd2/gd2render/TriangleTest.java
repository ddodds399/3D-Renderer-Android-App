package gd2.gd2render;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import gd2.gd2render.Primitives.Triangle;

/**
 * Created by SpoonPhilosophy on 03/11/2015.
 */
public class TriangleTest extends TestCase {
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