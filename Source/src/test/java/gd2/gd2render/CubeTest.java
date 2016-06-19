package gd2.gd2render;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import gd2.gd2render.Primitives.Cube;


/**
 * Created by SpoonPhilosophy on 03/11/2015.
 */
public class CubeTest extends TestCase {

    Cube cube = new Cube();

    private float vertices[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f
    };

    private float colors[] = {
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            0.0f,  0.0f,  1.0f,  1.0f,
            1.0f,  0.0f,  1.0f,  1.0f
    };

    private byte indices[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };

    public void testVertices() throws Exception {
        for (int i=0; i<cube.getVertices().length; i++) {
            assertEquals(cube.getVertices()[i], vertices[i]);
        }
    }

    public void testColors() throws Exception {
        for (int i=0; i<cube.getColors().length; i++) {
            assertEquals(cube.getColors()[i], colors[i]);
        }
    }

    public void testIndices() throws Exception {
        for (int i=0;i<cube.getIndices().length; i++) {
            assertEquals(cube.getIndices()[i], indices[i]);
        }
    }

    public void testRotate() throws Exception {
        cube.rotate(1.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(cube.getrAngle(), 1.0f);
    }

    public void testRotate1() throws Exception {
        cube.rotate(-1.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(cube.getrAngle(), -1.0f);
    }

}