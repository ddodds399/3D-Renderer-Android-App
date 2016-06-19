package gd2.gd2render;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import gd2.gd2render.Primitives.Line;

/**
 * Created by James on 03/11/2015.
 */
public class LineTest extends TestCase {
    Line line = new Line();

    private float vertices[] = {
            1.0f, 0.0f, 0.0f, //Point 1
            5.0f,0.0f,0.f    //Point 2
    };

    private float colours[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    public void testVertices() {
        for (int i=0; i<line.getVertices().length; i++) {
            assertEquals(line.getVertices()[i], vertices[i]);
        }
    }

    public void testColours() {
        for (int i=0;i<line.getColours().length;i++) {
            assertEquals(line.getColours()[i], colours[i]);
        }
    }

    public void testRotate() throws Exception {
        line.rotate(1.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(line.getrAngle(), 1.0f);
    }

    public void testRotate1() throws Exception {
        line.rotate(-1.0f, 0.0f, 0.0f, 0.0f);
        assertEquals(line.getrAngle(), -1.0f);
    }
}
