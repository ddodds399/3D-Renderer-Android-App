package gd2.gd2render;

import org.junit.Assert;
import org.junit.Test;

import gd2.gd2render.Core.SurfaceRenderer;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 05/11/2015.
 */
public class SurfaceRendererTest {
SurfaceRenderer sf = new SurfaceRenderer();
    @Test
    public void testChangeColour() throws Exception {
        sf.changeColour();
        assertEquals(1,sf.getColour());
    }
    @Test
    public void testChangeColour2() throws Exception {
        sf.changeColour();
        sf.changeColour();
        assertEquals(2,sf.getColour());

    }
    public void testChangeColour3() throws Exception {
        sf.changeColour();
        sf.changeColour();
        sf.changeColour();
        assertEquals(0,sf.getColour());
    }


    @Test
    public void testSetvPrimitive() throws Exception {
    sf.setvPrimitive(2);
        assertEquals(2,sf.getvPrimitive());

    }

    @Test
    public void testChangeShape() throws Exception {
    sf.changeShape();
        assertEquals(1,sf.getvPrimitive());
    }

    @Test
    public void testChangeShape2() throws Exception {
        sf.changeShape();
        sf.changeShape();
        assertEquals(2,sf.getvPrimitive());
    }

    @Test
    public void testChangeShape3() throws Exception {
        sf.changeShape();
        sf.changeShape();
        sf.changeShape();
        assertEquals(0,sf.getvPrimitive());
    }

    @Test
    public void testGetColour() throws Exception {
    assertEquals(0, sf.getColour());

    }

    @Test
    public void testGetvPrimitive() throws Exception {
    assertEquals(0,sf.getvPrimitive());
    }

    @Test
    public void testSetColour() throws Exception {
    sf.setColour(1);
    assertEquals(1,sf.getColour()) ;
    }

    @Test
    public void testGetLineCamera() throws Exception {
    assertEquals( 2, sf.getLineCamera().getX(),0);
    }

    @Test
    public void testGetTriCamera() throws Exception {
    assertEquals((float) 0, sf.getTriCamera().getX(),0);
    }

    @Test
    public void testGetCubeCamera() throws Exception {
    assertEquals((float) -3, sf.getCubeCamera().getX(),0);

    }
}