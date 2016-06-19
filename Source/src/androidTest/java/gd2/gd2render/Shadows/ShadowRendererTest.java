package gd2.gd2render.Shadows;

import junit.framework.TestCase;

/**
 * Created by James on 04/12/2015.
 */
public class ShadowRendererTest extends TestCase {

    ShadowActivity shadowActivity = new ShadowActivity();

    ShadowRenderer shadowRenderer = new ShadowRenderer(shadowActivity);

    public void setup() throws Exception {
        super.setUp();
    }

    public void testGetRotX() {
        assertEquals(shadowRenderer.getRotationX(), 0.0f);
    }

    public void testGetRotY() {
        assertEquals(shadowRenderer.getRotationY(), 0.0f);
    }

    public void testSetRotX() {
        shadowRenderer.setRotationX(2.0f);
        assertEquals(shadowRenderer.getRotationX(), 2.0f);
    }

    public void testSetRotY() {
        shadowRenderer.setRotationY(3.0f);
        assertEquals(shadowRenderer.getRotationY(), 3.0f);
    }

}