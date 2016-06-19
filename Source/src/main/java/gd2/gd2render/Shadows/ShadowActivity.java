package gd2.gd2render.Shadows;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by James on 04/12/2015.
 * Used http://www.codeproject.com/Articles/822380/Shadow-Mapping-with-Android-OpenGL-ES as tutorial and base
 */

public class ShadowActivity extends Activity {

    static boolean sIsRunning = true;

    private ShadowSurfaceView mView;
    private ShadowRenderer mRenderer;
    /**
     * Shadow map size:
     * 	- displayWidth * SHADOW_MAP_RATIO
     * 	- displayHeight * SHADOW_MAP_RATIO
     */
    private float mShadowMapRatio = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it as the ContentView for this Activity
        mView = new ShadowSurfaceView(this);

        // Create an OpenGL ES 2.0 context.
        mView.setEGLContextClientVersion(2);

        mRenderer = new ShadowRenderer(this);
        mView.setRenderer(mRenderer);

        setContentView(mView);
        sIsRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        sIsRunning = false;
    }

    public float getmShadowMapRatio() {
        return mShadowMapRatio;
    }

}

