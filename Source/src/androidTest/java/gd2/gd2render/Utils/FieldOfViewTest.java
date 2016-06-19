package gd2.gd2render.Utils;

import android.content.Context;

import junit.framework.TestCase;

import gd2.gd2render.Core.RendererGL2;

/**
 * Created by Thomas on 03/12/2015.
 */
public class FieldOfViewTest extends TestCase {

    FieldOfView fovEmptyTest, fovTest;


    public void setUp() throws Exception{
        super.setUp();
        fovEmptyTest = new FieldOfView();
        fovTest = new FieldOfView(90);
    }


    public void tearDown() throws Exception{
        fovEmptyTest = null;
        fovTest = null;
    }

    public void testSetFieldOfView() throws Exception {
        fovEmptyTest.setFieldOfView(1800);
        assertEquals(fovEmptyTest.getFieldOfView(), (float)1800);
    }

    public void testChangeFieldOfView() throws Exception {

    }

    public void testResetFovEmpty() throws Exception{
        fovEmptyTest.setFieldOfView(90);
        assertNotSame(fovTest.getFieldOfView(), fovTest.getInitFieldOfView());
        fovEmptyTest.resetFov();
        assertEquals(fovEmptyTest.getFieldOfView(), fovEmptyTest.getInitFieldOfView());
    }

    public void testResetFov() throws Exception{
        fovTest.setFieldOfView(10);
        assertNotSame(fovTest.getFieldOfView(), fovTest.getInitFieldOfView());
        fovTest.resetFov();
        assertEquals(fovTest.getFieldOfView(), fovTest.getInitFieldOfView());
    }
}