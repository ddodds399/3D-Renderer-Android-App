package gd2.gd2render.Shadows;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by James on 04/12/2015.
 */
public class ShadowActivityTest extends ActivityInstrumentationTestCase2<ShadowActivity> {

    ShadowActivity activity;

    public ShadowActivityTest() {
        super(ShadowActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testGetSMapRatio(){
        assertEquals(activity.getmShadowMapRatio(), 1.0f);
    }

    public void testOnBackPressed() {
        activity.onBackPressed();
        assertEquals(activity.sIsRunning, false);
    }
}