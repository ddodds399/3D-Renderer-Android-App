package gd2.gd2render;

import junit.framework.Assert;
import junit.framework.TestCase;








import gd2.gd2render.Utils.CameraMovement;


/**
 * Created by Andrew on 23/11/2015.
 */
public class CameraMovementTest extends TestCase {





        private CameraMovement cM, cM1;


        public void setUp() {
            cM = new CameraMovement(0, 2, 3, 1, 2, 4);
            cM1 = new CameraMovement(0, 1, 2);
        }


        public void testGetX() throws Exception {
            Assert.assertEquals((float) 0, cM.getX(), 0);



        }


        public void testGetX2() throws Exception {
            Assert.assertEquals((float) 0, cM1.getX(),0);
        }


        public void testGetY() throws Exception {
            Assert.assertEquals((float) 2, cM.getY(),0);

        }


        public void testGetY2() throws Exception {
            Assert.assertEquals((float) 1, cM1.getY(),0);
        }


        public void testGetZ() throws Exception {
            Assert.assertEquals((float) 3, cM.getZ(),0);

        }


        public void testGetZ2() throws Exception {
            Assert.assertEquals((float) 2, cM1.getZ(),0);
        }


        public void testGetEyeX() throws Exception {
            Assert.assertEquals((float) 1, cM.getEyeX(),0);
        }


        public void testGetEyeY() throws Exception {
            Assert.assertEquals((float) 2, cM.getEyeY(),0);
        }


        public void testGetEyeZ() throws Exception {
            Assert.assertEquals((float) 4, cM.getEyeZ(),0);
        }

        public void testGetXR() throws Exception {
            Assert.assertEquals((float)0,cM.getXR(),0);
        }

        public void testGetYR() throws Exception {
            Assert.assertEquals((float)0,cM.getYR(),0);
        }

        public void testGetZR() throws Exception {
            Assert.assertEquals((float)0,cM.getYR(),0);
        }

        public void testGetLeftRot() throws Exception {
            Assert.assertEquals(0,cM.getLeftRot(),0);
        }

        public void testGetUpRot() throws Exception {
            Assert.assertEquals(0,cM.getUpRot(),0);
        }

        public void testSetXR() throws Exception {
            cM.setXR(5);
            Assert.assertEquals((float)5,cM.getXR(),0);
        }

        public void testSetYR() throws Exception {
            cM.setYR(-1);
            Assert.assertEquals((float)-1,cM.getYR(),0);

        }

        public void testSetZR() throws Exception {
            cM.setZR(20);
            Assert.assertEquals((float)20, cM.getZR(),0);
        }


        public void testSetX() throws Exception {
            cM.setX(10);
            Assert.assertEquals((float) 10, cM.getX(),0);
        }



        public void testSetY() throws Exception {
            cM.setY(5);
            Assert.assertEquals((float) 5, cM.getY(),0);
        }


        public void testSetZ() throws Exception {
            cM.setZ(11);
            Assert.assertEquals((float) 11, cM.getZ(),0);
        }


        public void testSetEyeX() throws Exception {
            cM.setEyeX(9);
            Assert.assertEquals((float) 9, cM.getEyeX(),0);
        }


        public void testSetEyeY() throws Exception {
            cM.setEyeY(19);
            Assert.assertEquals((float) 19, cM.getEyeY(),0);
        }


        public void testSetEyeZ() throws Exception {
            cM.setEyeZ(2);
            Assert.assertEquals((float) 2, cM.getEyeZ(),0);
        }

        public void testSetLeftRot() throws Exception {
            cM.setLeftRot(10);
            Assert.assertEquals(10,cM.getLeftRot(),0);
        }

        public void testSetUpRot() throws Exception {
            cM.setUpRot(12);
            Assert.assertEquals(12,cM.getUpRot(),0);
        }


        public void testTranslateXPlus() throws Exception {
            cM.translateXPlus();
            Assert.assertEquals((float) 1, cM.getX(),0);


        }


        public void testTranslateEyeXPlus() throws Exception {
            cM.translateXPlus();
            Assert.assertEquals((float) 2, cM.getEyeX(),0);
        }


        public void testTranslateXMinus() throws Exception {
            cM.translateXMinus();
            Assert.assertEquals((float) -1, cM.getX(),0);
        }


        public void testTranslateEyeXMinus() throws Exception {
            cM.translateXMinus();
            Assert.assertEquals((float) 0, cM.getEyeX(),0);

        }



        public void testTranslateYPlus() throws Exception {
            cM.translateYPlus();
            Assert.assertEquals((float) 3, cM.getY(),0);
        }

        public void testTranslateEyeYPlus() throws Exception {
            cM.translateYPlus();
            Assert.assertEquals((float) 3, cM.getEyeY(),0);
        }


        public void testTranslateYMinus() throws Exception {
            cM.translateYMinus();
            Assert.assertEquals((float) 1, cM.getY(),0);
        }

        public void testTranslateEyeYMinus() throws Exception{
            cM.translateYMinus();
            Assert.assertEquals((float) 1, cM.getEyeY(),0);
        }


        public void testTranslateZPlus() throws Exception {
            cM.translateZPlus();

            Assert.assertEquals((float) 4, cM.getZ(),0);
        }

        public void testTranslateEyeZPlus() throws Exception {
            cM.translateZPlus();
            Assert.assertEquals((float) 5, cM.getEyeZ(),0);
        }



        public void testTranslateZMinus() throws Exception {
            cM.translateZMinus();
            Assert.assertEquals((float) 2, cM.getZ(),0);
        }


        public void testTranslateEyeZMinus() throws Exception {
            cM.translateZMinus();
            Assert.assertEquals((float) 3, cM.getEyeZ(),0);
        }


        public void testTranslationXPlusTimesTwo() throws Exception {
            cM.translateXPlus();
            cM.translateXPlus();
            Assert.assertEquals((float) 2, cM.getX(), 0);
            Assert.assertEquals((float) 3, cM.getEyeX(),0);
        }

        public void testTranslationXMinusTimesTwo() throws Exception{
            cM.translateXMinus();
            cM.translateXMinus();
            Assert.assertEquals((float) -2, cM.getX(), 0);
            Assert.assertEquals((float) -1, cM.getEyeX(),0);
        }

        public void testTranslationYPlusTimesTwo() throws Exception {
            cM.translateYPlus();
            cM.translateYPlus();
            Assert.assertEquals((float) 4, cM.getY(),0);
            Assert.assertEquals((float) 4, cM.getEyeY(),0);
        }

        public void testTranslationYMinusTwo() throws Exception {
            cM.translateYMinus();
            cM.translateYMinus();
            Assert.assertEquals((float) 0, cM.getY(),0);
            Assert.assertEquals((float) 0, cM.getEyeY(),0);
        }

        public void testTranslationZPlusTimes2() throws Exception {
            cM.translateZPlus();
            cM.translateZPlus();
            Assert.assertEquals((float) 5, cM.getZ(),0);
            Assert.assertEquals((float) 6, cM.getEyeZ(),0);
        }

        public void testTranslationZMinusTimes2() throws Exception {
            cM.translateZMinus();
            cM.translateZMinus();
            Assert.assertEquals((float) 1, cM.getZ(),0);
            Assert.assertEquals((float) 2, cM.getEyeZ(),0);
        }

        public void testXMinusTimes3XPlusTimes2() throws Exception{
            cM.translateXMinus();
            cM.translateXMinus();
            cM.translateXMinus();
            cM.translateXPlus();
            cM.translateXPlus();
            Assert.assertEquals((float) -1, cM.getX(),0);
            Assert.assertEquals((float) 0, cM.getEyeX(),0);
        }

        public void testYMinusTimes3YPlusTimes2() throws Exception{
            cM.translateYMinus();
            cM.translateYMinus();
            cM.translateYMinus();
            cM.translateYPlus();
            cM.translateYPlus();
            Assert.assertEquals((float) 1, cM.getY(),0);
            Assert.assertEquals((float) 1, cM.getEyeY(),0);
        }

        public void testZMinusTimes3ZPlusTimes2() throws Exception{
            cM.translateZMinus();
            cM.translateZMinus();
            cM.translateZMinus();
            cM.translateZPlus();
            cM.translateZPlus();
            Assert.assertEquals((float) 2, cM.getZ(), 0);
            Assert.assertEquals((float) 3, cM.getEyeZ(),0);
        }

        public void testRotateCameraLeft() throws Exception {
            cM.rotateCameraLeft();
            Assert.assertEquals((float)-1, cM.getYR(),0);
        }

        public void testRotateCameraRight() throws Exception {
            cM.rotateCameraRight();
            Assert.assertEquals((float)1, cM.getYR(),0);
        }

        public void testRotateCameraUp() throws Exception {
            cM.rotateCameraUp();
            Assert.assertEquals((float)-1,cM.getXR(),0);
        }

        public void testRotateCameraDown() throws Exception {
            cM.rotateCameraDown();
            Assert.assertEquals((float)1, cM.getXR(),0);
        }

        public void testReset() throws Exception{
            cM.setX(5);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getX(), 0);
        }

        public void testReset2() throws Exception{
            cM.setY(6);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getY(), 0);

        }

        public void testReset3() throws Exception{
            cM.setZ(7);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getZ(), 0);

        }

        public void testReset4() throws Exception{
            cM.setXR(1);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getXR(), 0);
        }

        public void testReset5() throws Exception{
            cM.setYR(10);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getYR(), 0);
        }

        public void testReset6() throws Exception{
            cM.setZR(16);
            cM.reset();
            Assert.assertEquals((float) 0, cM.getZR(), 0);
        }

        public void testTranslateXPlusLeftRot() throws Exception {
            cM.reset();
            cM.setLeftRot(45);
            cM.translateXPlus();
            Assert.assertEquals((float)0.5,cM.getX(),0);
            Assert.assertEquals((float) 1.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float)3.5,cM.getEyeZ(),0);
        }

        public void testTranslateXPlusLeftRot2() throws Exception {
            cM.reset();
            cM.setLeftRot(90);
            cM.translateXPlus();
            Assert.assertEquals(-1, cM.getZ(), 0);
            Assert.assertEquals(3,cM.getEyeZ(),0);
        }

        public void testTranslateXPlusLeftRot3() throws Exception {
            cM.reset();
            cM.setLeftRot(135);
            cM.translateXPlus();
            Assert.assertEquals((float) -0.5, cM.getX(), 0);
            Assert.assertEquals((float) 0.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float)3.5,cM.getEyeZ(),0);
        }

        public void testTranslateXPlusLeftRot4() throws Exception {
            cM.reset();
            cM.setLeftRot(180);
            cM.translateXPlus();
            Assert.assertEquals((float)-1,cM.getX(),0);
            Assert.assertEquals((float) 0, cM.getEyeX(), 0);

        }

        public void testTranslateXPlusLeftRot5() throws Exception {
            cM.reset();
            cM.setLeftRot(225);
            cM.translateXPlus();
            Assert.assertEquals((float) -0.5, cM.getX(), 0);
            Assert.assertEquals((float) 0.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float)4.5,cM.getEyeZ(),0);
        }

        public void testTranslateXPlusLeftRot6() throws Exception {
            cM.reset();
            cM.setLeftRot(270);
            cM.translateXPlus();
            Assert.assertEquals((float) 1, cM.getZ(), 0);
            Assert.assertEquals((float)5,cM.getEyeZ(),0);
        }

        public void testTranslateXPlusLeftRot7() throws Exception {
            cM.reset();
            cM.setLeftRot(315);
            cM.translateXPlus();
            Assert.assertEquals((float) 0.5, cM.getX(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float)4.5,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot() throws Exception {
            cM.reset();
            cM.setLeftRot(45);
            cM.translateXMinus();
            Assert.assertEquals((float) -0.5, cM.getX(), 0);
            Assert.assertEquals((float) 0.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float)4.5,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot2() throws Exception {
            cM.reset();
            cM.setLeftRot(90);
            cM.translateXMinus();
            Assert.assertEquals((float) 1, cM.getZ(), 0);
            Assert.assertEquals((float)5,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot3() throws Exception {
            cM.reset();
            cM.setLeftRot(135);
            cM.translateXMinus();
            Assert.assertEquals((float) 0.5, cM.getX(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float)4.5,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot4() throws Exception {
            cM.reset();
            cM.setLeftRot(180);
            cM.translateXMinus();
            Assert.assertEquals((float)1,cM.getX(),0);
            Assert.assertEquals((float) 2, cM.getEyeX(), 0);

        }

        public void testTranslateXMinusLeftRot5() throws Exception {
            cM.reset();
            cM.setLeftRot(225);
            cM.translateXMinus();
            Assert.assertEquals((float) 0.5, cM.getX(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float)3.5,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot6() throws Exception {
            cM.reset();
            cM.setLeftRot(270);
            cM.translateXMinus();
            Assert.assertEquals((float) -1, cM.getZ(), 0);
            Assert.assertEquals((float)3,cM.getEyeZ(),0);
        }

        public void testTranslateXMinusLeftRot7() throws Exception {
            cM.reset();
            cM.setLeftRot(315);
            cM.translateXMinus();
            Assert.assertEquals((float) -0.5, cM.getX(), 0);
            Assert.assertEquals((float) 0.5, cM.getEyeX(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float)3.5,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot() throws Exception {
            cM.reset();
            cM.setUpRot(45);
            cM.translateYPlus();
            Assert.assertEquals((float) 0.5, cM.getY(), 0);
            Assert.assertEquals((float) 2.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 4.5,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot2() throws Exception {
            cM.reset();
            cM.setUpRot(90);
            cM.translateYPlus();
            Assert.assertEquals((float) 1, cM.getZ(), 0);
            Assert.assertEquals((float) 5,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot3() throws Exception {
            cM.reset();
            cM.setUpRot(135);
            cM.translateYPlus();
            Assert.assertEquals((float) -0.5, cM.getY(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 4.5,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot4() throws Exception {
            cM.reset();
            cM.setUpRot(180);
            cM.translateYPlus();
            Assert.assertEquals((float) -1, cM.getY(), 0);
            Assert.assertEquals((float) 1, cM.getEyeY(), 0);

        }

        public void testTranslateYPlusLeftRot5() throws Exception {
            cM.reset();
            cM.setUpRot(225);
            cM.translateYPlus();
            Assert.assertEquals((float) -0.5, cM.getY(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 3.5,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot6() throws Exception {
            cM.reset();
            cM.setUpRot(270);
            cM.translateYPlus();
            Assert.assertEquals((float) -1, cM.getZ(), 0);
            Assert.assertEquals((float) 3,cM.getEyeZ(),0);
        }

        public void testTranslateYPlusLeftRot7() throws Exception {
            cM.reset();
            cM.setUpRot(315);
            cM.translateYPlus();
            Assert.assertEquals((float) 0.5, cM.getY(), 0);
            Assert.assertEquals((float) 2.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 3.5,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot() throws Exception {
            cM.reset();
            cM.setUpRot(45);
            cM.translateYMinus();
            Assert.assertEquals((float) -0.5, cM.getY(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 3.5,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot2() throws Exception {
            cM.reset();
            cM.setUpRot(90);
            cM.translateYMinus();
            Assert.assertEquals((float) -1, cM.getZ(), 0);
            Assert.assertEquals((float) 3,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot3() throws Exception {
            cM.reset();
            cM.setUpRot(135);
            cM.translateYMinus();
            Assert.assertEquals((float) 0.5, cM.getY(), 0);
            Assert.assertEquals((float) 2.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) -0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 3.5,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot4() throws Exception {
            cM.reset();
            cM.setUpRot(180);
            cM.translateYMinus();
            Assert.assertEquals((float) 1, cM.getY(), 0);
            Assert.assertEquals((float) 3, cM.getEyeY(), 0);

        }

        public void testTranslateYMinusLeftRot5() throws Exception {
            cM.reset();
            cM.setUpRot(225);
            cM.translateYMinus();
            Assert.assertEquals((float) 0.5, cM.getY(), 0);
            Assert.assertEquals((float) 2.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 4.5,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot6() throws Exception {
            cM.reset();
            cM.setUpRot(270);
            cM.translateYMinus();
            Assert.assertEquals((float) 1, cM.getZ(), 0);
            Assert.assertEquals((float) 5,cM.getEyeZ(),0);
        }

        public void testTranslateYMinusLeftRot7() throws Exception {
            cM.reset();
            cM.setUpRot(315);
            cM.translateYMinus();
            Assert.assertEquals((float) -0.5, cM.getY(), 0);
            Assert.assertEquals((float) 1.5, cM.getEyeY(), 0);
            Assert.assertEquals((float) 0.5, cM.getZ(), 0);
            Assert.assertEquals((float) 4.5,cM.getEyeZ(),0);
        }

        public void testTranslateZPlusLeftRot() throws Exception {
            cM.reset();
            cM.setLeftRot(180);
            cM.translateZPlus();
            Assert.assertEquals((float)-1, cM.getZ(),0);
            Assert.assertEquals((float)3, cM.getEyeZ(),0);

        }

        public void testTranslateZPlusLeftRot2() throws Exception {
            cM.reset();
            cM.setLeftRot(90);
            cM.translateZPlus();
            Assert.assertEquals((float) 1, cM.getX(), 0);
            Assert.assertEquals((float)2, cM.getEyeX(),0);

        }

        public void testTranslateZPlusLeftRot3() throws Exception {
            cM.reset();
            cM.setLeftRot(270);
            cM.translateZPlus();
            Assert.assertEquals((float)-1, cM.getX(),0);
            Assert.assertEquals((float)0, cM.getEyeX(),0);

        }

        public void testTranslateZPlusLeftRot4() throws Exception {
            cM.reset();
            cM.setUpRot(90);
            cM.translateZPlus();
            Assert.assertEquals((float)-1, cM.getY(),0);
            Assert.assertEquals((float)1, cM.getEyeY(),0);

        }

        public void testTranslateZPlusLeftRot5() throws Exception {
            cM.reset();
            cM.setUpRot(270);
            cM.translateZPlus();
            Assert.assertEquals((float)1, cM.getY(),0);
            Assert.assertEquals((float)3, cM.getEyeY(),0);

        }

        public void testTranslateZMinusLeftRot() throws Exception {
            cM.reset();
            cM.setLeftRot(180);
            cM.translateZMinus();
            Assert.assertEquals((float)1, cM.getZ(),0);
            Assert.assertEquals((float)5, cM.getEyeZ(),0);

        }

        public void testTranslateZMinusLeftRot2() throws Exception {
            cM.reset();
            cM.setLeftRot(90);
            cM.translateZMinus();
            Assert.assertEquals((float)-1, cM.getX(),0);
            Assert.assertEquals((float)0, cM.getEyeX(),0);

        }

        public void testTranslateZMinusLeftRot3() throws Exception {
            cM.reset();
            cM.setLeftRot(270);
            cM.translateZMinus();
            Assert.assertEquals((float)1, cM.getX(),0);
            Assert.assertEquals((float)2, cM.getEyeX(),0);

        }

        public void testTranslateZMinusLeftRot4() throws Exception {
            cM.reset();
            cM.setUpRot(180);
            cM.translateZMinus();
            Assert.assertEquals((float)1, cM.getZ(),0);
            Assert.assertEquals((float)5, cM.getEyeZ(),0);

        }

        public void testTranslateZMinusLeftRot5() throws Exception {
            cM.reset();
            cM.setUpRot(90);
            cM.translateZMinus();
            Assert.assertEquals((float)1, cM.getY(),0);
            Assert.assertEquals((float)3, cM.getEyeY(),0);

        }







    }

