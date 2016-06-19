package gd2.gd2render.Shadows;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by James on 04/12/2015.
 * Used http://www.codeproject.com/Articles/822380/Shadow-Mapping-with-Android-OpenGL-ES as tutorial and base
 */
public class ShadowSurfaceView extends GLSurfaceView {
    private ShadowRenderer mRenderer;

    public ShadowSurfaceView(Context context) {
        super(context);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    /**
     * method to detect touch motion to move large cuboid
     * @param e - the motion event detected
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                //Set the rotation values in the shader based on the touch event detected
                mRenderer.setRotationX(
                        mRenderer.getRotationX() +
                                (dx * TOUCH_SCALE_FACTOR));

                mRenderer.setRotationY(
                        mRenderer.getRotationY() +
                                (dy * TOUCH_SCALE_FACTOR));

                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    /**
     * Sets the views renderer to the given renderer
     * @param renderer - renderer to set the views renderer to
     */
    public void setRenderer(ShadowRenderer renderer) {
        mRenderer = renderer;
        super.setRenderer(renderer);
    }

}
