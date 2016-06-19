package gd2.gd2render.Utils;

import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by Thomas on 03/12/2015.
 */
public class FieldOfView {

    /**
     * Constructor  for the Field of view class
     * @param init - the value to which the initial field of view will be set
     */
    public FieldOfView(float init){
        fieldOfView = init;
        initFOV = init;
    }

    /**
     * Constructor for the Filed of View class
     */
    public FieldOfView(){
        initFOV = 0;

    }

    //the value of the angle of the field of view
    private float fieldOfView;
    private float initFOV;

    /**
     * Increases the field of view by the amount extra
     * @param extra - the amount which will be added to the current field of view
     */
    public void setFieldOfView(float extra){
        fieldOfView += extra;
    }

    /**
     * this method is used to reset the FOV to the initialised value
     */
    public void resetFov(){
        fieldOfView = initFOV;
    }

    /**
     * getter method for the angle of the field of view
     * @return
     */
    public float getFieldOfView(){
        return fieldOfView;
    }

    public float getInitFieldOfView(){
        return initFOV;
    }

    /**
     * changes the field of view and sets the aspect ratio to 16/9
     * @param proj - the projection matrix of the view to be changed
     */
    public void changeFieldOfView169(float [] proj){
        Matrix.perspectiveM(proj, 0, fieldOfView, 16/9, 0.5f, 100f);
    }

    /**
     * changes the field of view and sets the aspect ratio to 4/3
     * @param proj - the projection matrix of the view to be changed
     */
    public void changeFieldOfView43(float [] proj){
        Matrix.perspectiveM(proj, 0, fieldOfView, 4/3, 0.5f, 100f);
    }


}
