package gd2.gd2render.UserInterface;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;
import junit.framework.TestCase;

import gd2.gd2render.Core.MainActivity;

/**
 * Created by Andy on 04/12/2015.
 */
public class UserInterfaceRendererTest extends TestCase {


    UserInterfaceRenderer uI;
    MainActivity activity;




    public void setUp() throws Exception{
        super.setUp();

    }
    public void tearDown() throws Exception{
        uI = null;
    }

    public void testCubeSmall() throws Exception{
        Assert.assertEquals( 0f, uI.smallCubeX);
        Assert.assertEquals( 0f,  uI.smallCubeY);
        Assert.assertEquals( -5f, uI.smallCubeZ);

    }
    public void testCubeMedium() throws Exception{
        Assert.assertEquals(5f, uI.mediumCubeX);
        Assert.assertEquals(0f, uI.mediumCubeY);
        Assert.assertEquals(-5f, uI.mediumCubeZ);
    }
    public void testCubeLarge() throws Exception{
        Assert.assertEquals(-5f, uI.largeCubeX);
        Assert.assertEquals(0f, uI.largeCubeY);
        Assert.assertEquals(-5f, uI.largeCubeZ);
    }


}