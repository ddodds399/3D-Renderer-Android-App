package gd2.gd2render;

import junit.framework.TestCase;

import gd2.gd2render.Core.SurfaceRenderer;


/**
 * Created by Andrew on 23/11/2015.
 */
public class SurfaceRendererTest extends TestCase {




        SurfaceRenderer sf = new SurfaceRenderer();

        public void testChangeColour() throws Exception {
            sf.changeColour();
            assertEquals(1,sf.getColour());
        }

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



        public void testSetvPrimitive() throws Exception {
            sf.setvPrimitive(2);
            assertEquals(2,sf.getvPrimitive());

        }


        public void testChangeShape() throws Exception {
            sf.changeShape();
            assertEquals(1,sf.getvPrimitive());
        }


        public void testChangeShape2() throws Exception {
            sf.changeShape();
            sf.changeShape();
            assertEquals(2,sf.getvPrimitive());
        }


        public void testChangeShape3() throws Exception {
            sf.changeShape();
            sf.changeShape();
            sf.changeShape();
            assertEquals(0,sf.getvPrimitive());
        }


        public void testGetColour() throws Exception {
            assertEquals(0, sf.getColour());

        }


        public void testGetvPrimitive() throws Exception {
            assertEquals(0,sf.getvPrimitive());
        }


        public void testSetColour() throws Exception {
            sf.setColour(1);
            assertEquals(1,sf.getColour()) ;
        }


        public void testGetLineCamera() throws Exception {
            assertEquals( 2, sf.getLineCamera().getX(),0);
        }


        public void testGetTriCamera() throws Exception {
            assertEquals((float) 0, sf.getTriCamera().getX(),0);
        }


        public void testGetCubeCamera() throws Exception {
            assertEquals((float) -3, sf.getCubeCamera().getX());

        }
    }

