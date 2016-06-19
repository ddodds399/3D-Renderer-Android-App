package gd2.gd2render.Core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class SurfaceViewObject extends GLSurfaceView {

    private final SurfaceRenderer mRenderer = new SurfaceRenderer();
    public RendererGL2 rMK2; //remember to revert back to private once getters have been implemented and all other uses get updated
    //max and min amount of scaling that can occur
    private static float MAX_SCALE = 5.0f;
    private static float MIN_SCALE = 1.0f;
    private boolean scaleCheck = true;
    //amount to be scaled by
    private float scaleFactor = 1.0f;
    //initialisation of class to detect pinch movement
    private ScaleGestureDetector scaleDetector;
    private GLGraphics grap = new GLGraphics(this);

    /**
     * Super constructor for SurfaceViewObject
     *
     * @param context
     * @param primitive
     * @param renderer
     */
    public SurfaceViewObject(Context context, int primitive, int renderer) {
        super(context);
        setEGLContextClientVersion(1);
        if (renderer == 0) {
            setRenderer(mRenderer);
            mRenderer.setvPrimitive(primitive);
        } else {
            setEGLContextClientVersion(2);
            rMK2 = new RendererGL2(context);
            rMK2.setShaderMode(primitive);
            setRenderer(rMK2);
        }

        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

    /**
     * Super constructor for SurfaceViewObject with atrribute set.
     *
     * @param context
     * @param attrs
     * @param primitive
     * @param renderer
     */
    public SurfaceViewObject(Context context, AttributeSet attrs, int primitive, int renderer) {
        super(context, attrs);
        setEGLContextClientVersion(1);
        if (renderer == 0) {
            setRenderer(mRenderer);
            mRenderer.setvPrimitive(primitive);
        } else {
            setEGLContextClientVersion(2);
            rMK2 = new RendererGL2(context);
            rMK2.setShaderMode(primitive);
            setRenderer(rMK2);
        }
        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;
    private float scaleX = 0, scalePrev = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        scaleX = x;


        switch (e.getAction()) {

            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;
                if (scaleX > scalePrev) {
                    scaleFactor += 0.1f;
                    if (scaleFactor >= MAX_SCALE) {
                        scaleFactor -= 0.1f;
                    }
                } else if (scaleX < scalePrev) {
                    scaleFactor -= 0.1f;
                    if (scaleFactor <= MIN_SCALE) {
                        scaleFactor += 0.1f;
                    }
                }

                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                mRenderer.tri.rotate(mRenderer.tri.getrAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR), 0.0f, 0.0f, 1.0f);
                mRenderer.cube.rotate(mRenderer.cube.getrAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR), 0.0f, 0.0f, 1.0f);
                mRenderer.line.rotate(mRenderer.cube.getrAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR), 0.0f, 0.0f, 1.0f);
                mRenderer.cube.scale(scaleFactor, scaleFactor, scaleFactor);
                mRenderer.tri.scale(scaleFactor, scaleFactor, scaleFactor);

                break;

            case MotionEvent.ACTION_DOWN:

                mRenderer.changeColour();
                if (rMK2 != null) {
                    rMK2.setRotating(true);
                }
                break;

            case MotionEvent.ACTION_UP:
                mRenderer.tri.changeDrawElement();
                mRenderer.tri.changeDrawElement();
                mRenderer.cube.changeDrawElement();
                scaleX = 0;
                if (rMK2 != null) {
                    rMK2.setRotating(false);
                    rMK2.cube.reColourFaces("#ff0000", "#ff8000", "#ffff00", "#80ff00", "#007fff", "#ff00ff");
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mRenderer.cube.scale(scaleDetector.getScaleFactor(), scaleDetector.getScaleFactor(), scaleDetector.getScaleFactor());


        }


        previousX = x;
        previousY = y;
        scalePrev = scaleX;
        return true;

    }

    /**
     * Return the Surface Renderer MK1
     *
     * @return
     */
    public SurfaceRenderer getSurfaceRenderer() {
        return mRenderer;
    }

    /**
     * Return the Surface Renderer MK2
     *
     * @return
     */
    public RendererGL2 getRendererMK2() {
        return rMK2;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        /**
         * Changes scale factor in order for the shapes size to be changed
         *
         * @param detector
         * @return
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));

            return true;
        }
    }
}
