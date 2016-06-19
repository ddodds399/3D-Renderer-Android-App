package gd2.gd2render.Utils;


public class CameraMovement {
    //initialise variables
    //camera position
    private float x,y,z;
    //where the camer is looking
    private float eyeX;
    private float eyeY;
    private float eyeZ;
    //rotation variables
    private float xR = 0.0f, yR = 0.0f, zR = 0.0f;
    private int leftRot = 0, upRot = 0;
    //constructor

    /**
     * Constructa a camera by setting the position of the camera and also setting where the camera is looking
     * @param x1 - the x location of the camera
     * @param x2 - the y location of the camra
     * @param x3 - the z ocation of rthe camera
     * @param e1 - teh x location of the point that the camera is looking at
     * @param e2 - the y location of the point that the camera is looking at
     * @param e3 - the z location of the point that the camera is looking at
     */
    public CameraMovement(float x1, float x2, float x3, float e1, float e2, float e3){
        x= x1;
        y= x2;
        z =x3;
        eyeX=e1;
        eyeY=e2;
        eyeZ=e3;

    }
    //constructor

    /**
     * Constructor to create a camera looking at the origin by setting the position of the camera
     * @param x4 - the x location of the camera
     * @param x5 - the y location of the camera
     * @param x6 - the z location of the camera
     */
    public CameraMovement(float x4, float x5, float x6){
        x = x4;
        y = x5;
        z = x6;
    }

    //getters and setters for the different variables

    /**
     * gets the x location of the camera
     * @return x - the x location of the camera
     */
    public float getX(){
        return x;
    }

    /**
     * gets the y location of the camera
     * @return y - the y location of the camera
     */
    public float getY(){
        return y;
    }

    /**
     * gets the z location of the camera
     * @return z - the z loction of the camera
     */
    public float getZ(){
        return z;
    }

    /**
     * gets the x locaation of the point that the camera is looking at
     * @return eyeX - the x location of the point that the camera is looking at
     */
    public float getEyeX(){
        return eyeX;
    }
    /**
     * gets the y location of the point that the camera is looking at
     * @return eyeY - the y location of the point that the camera is looking at
     */
    public float getEyeY(){
        return eyeY;
    }
    /**
     * gets the z location of the point that the camera is looking at
     * @return eyeZ - the z location of the point that the camera is looking at
     */
    public float getEyeZ(){
        return eyeZ;
    }

    /**
     * gets xR amount along the axis the camera is to rotate along
     * @return xR the amount of rotaion to be applied along the x axis
     */
    public float getXR(){
        return xR;
    }
    /**
     * gets yR amount along the axis the camera is to rotate along
     * @return yR the amount of rotaion to be applied along the y axis
     */
    public float getYR(){
        return yR;
    }
    /**
     * gets zR amount along the axis the camera is to rotate along
     * @return zR the amount of rotaion to be applied along the z axis
     */
    public float getZR(){
        return zR;
    }

    /**
     * sets the location to which the camera should move to
     * @param x1 the amonut which the camera will move in the x axis
     */
    public void setX(int x1){
        x = x1;
    }
    /**
     * sets the location to which the camera should move to
     * @param y1 the amount which the camera will move in the y axis
     */
    public void setY(int y1){
        y = y1;
    }
    /**
     * sets the location to which the camera should move to
     * @param z1 the amonut which the camera will move in the z axis
     */
    public void setZ(int z1){
        z = z1;
    }

    /**
     * sets where the camera will look
     * @param eX - where along the x axis the camera will look
     */
    public void setEyeX(int eX){
        eyeX = eX;
    }
    /**
     * sets where the camera will look
     * @param eY - where along the y axis the camera will look
     */
    public void setEyeY(int eY){
        eyeY = eY;
    }
    /**
     * sets where the camera will look
     * @param eZ - where along the z axis the camera will look
     */
    public void setEyeZ(int eZ){
        eyeZ = eZ;
    }

    /**
     * sets the rotational axis
     * @param rX - will rotate along the x axis
     */
    public void setXR(float rX){
        xR = rX;
    }
    /**
     * sets the rotational axis
     * @param rY - will rotate along the y axis
     */
    public void setYR(float rY){
        yR = rY;
    }
    /**
     * sets the rotational axis
     * @param rZ - will rotate along the z axis
     */
    public void setZR(float rZ){
        zR = rZ;
    }

    public int getLeftRot(){
        return leftRot;
    }
    public int getUpRot(){
        return upRot;
    }
    public void setLeftRot(int lRot){
        leftRot = lRot;
    }
    public void setUpRot(int uRot){
        upRot = uRot;
    }

    /**
     *moves the camera right by a single unit
     */
    public void translateXPlus(){
        if(leftRot == 0){
            x+=1;
            eyeX+=1;
        }else if(leftRot == 45&&upRot==0){
            x+=0.5f;
            eyeX += 0.5f;
            z -= 0.5f;
            eyeZ -= 0.5f;
        }else if(leftRot == 90&&upRot==0){
            z-=1;
            eyeZ-=1;
        }else if (leftRot ==135&&upRot==0){
            x -=0.5f;
            eyeX -=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }else if (leftRot == 225){
            x -=0.5f;
            eyeX-=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if (leftRot == 270){
            z+=1;
            eyeZ+=1;
        }else if (leftRot == 315){
            x+=0.5f;
            eyeX+=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if (leftRot == 180||(leftRot==0&&upRot ==180)){
            x -=1;
            eyeX -=1;
        }
    }

    /**
     * translate the camera left by one unit
     */

    public void translateXMinus(){
        if(leftRot == 0){
            x-=1;
            eyeX-=1;
        }else if(leftRot == 45){
            x-=0.5f;
            eyeX -= 0.5f;
            z += 0.5f;
            eyeZ += 0.5f;
        }else if(leftRot == 90){
            z+=1;
            eyeZ+=1;
        }else if (leftRot ==135){
            x +=0.5f;
            eyeX +=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if (leftRot == 180){
            x +=1;
            eyeX +=1;
        }else if (leftRot == 225){
            x +=0.5f;
            eyeX+=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }else if (leftRot == 270){
            z-=1;
            eyeZ-=1;
        }else if (leftRot == 315){
            x-=0.5f;
            eyeX-=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }
    }

    /**
     * move the camera up by one unit
     */
    public void translateYPlus(){
        if (upRot == 0){
            y+=1;
            eyeY+=1;
        }else if (upRot == 45){
            y+=0.5f;
            eyeY+=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if(upRot == 90){
            z+=1;
            eyeZ+=1;
        }else if (upRot == 135){
            y-=0.5f;
            eyeY-=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if (upRot == 180){
            y-=1;
            eyeY-=1;
        }else if (upRot ==225){
            y-=0.5f;
            eyeY-=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }else if (upRot == 270){
            z-=1;
            eyeZ-=1;
        }else if(upRot == 315){
            y+=0.5f;
            eyeY+=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }
    }

    /**
     * move the camera down by one unit
     */
    public void translateYMinus(){
        if (upRot == 0){
            y-=1;
            eyeY-=1;
        }else if (upRot == 45){
            y-=0.5f;
            eyeY-=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }else if(upRot == 90){
            z-=1;
            eyeZ-=1;
        }else if (upRot == 135){
            y+=0.5f;
            eyeY+=0.5f;
            z-=0.5f;
            eyeZ-=0.5f;
        }else if (upRot == 180){
            y+=1;
            eyeY+=1;
        }else if (upRot ==225){
            y+=0.5f;
            eyeY+=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }else if (upRot == 270){
            z+=1;
            eyeZ+=1;
        }else if(upRot == 315){
            y-=0.5f;
            eyeY-=0.5f;
            z+=0.5f;
            eyeZ+=0.5f;
        }
    }

    /**
     * zoom in with the camera
     */
    public void translateZPlus(){
        if (leftRot == 0 && upRot == 0){
            z+=1;
            eyeZ+=1;
        }else if ((leftRot == 180 && upRot ==0)||(leftRot == 180 && upRot ==180)||(leftRot == 0 && upRot ==180)){
            z-=1;
            eyeZ-=1;
        }else if ((leftRot == 90 && upRot ==0)){
            x+=1;
            eyeX+=1;
        }else if (leftRot == 270 && upRot == 0){
            x-=1;
            eyeX-=1;
        }else if (leftRot == 0 && upRot ==90){
            y-=1;
            eyeY-=1;
        }else if (leftRot == 0 && upRot == 270){
            y+=1;
            eyeY+=1;
        }
    }

    /**
     * zoom out with the camera
     */
    public void translateZMinus(){

        if (leftRot == 0 && upRot == 0){
            z-=1;
            eyeZ-=1;
        }else if (leftRot == 180 && upRot ==0){
            z+=1;
            eyeZ+=1;
        }else if (leftRot == 90 && upRot ==0){
            x-=1;
            eyeX-=1;
        }else if (leftRot == 270 && upRot == 0){
            x+=1;
            eyeX+=1;
        }else if (leftRot == 0 && upRot ==180){
            z+=1;
            eyeZ+=1;
        }else if (leftRot == 0 && upRot ==90){
            y+=1;
            eyeY+=1;
        }else if (leftRot == 0 && upRot == 270){
            y-=1;
            eyeY-=1;
        }

    }

    /**
     * rotate the camera left
     */
    public void rotateCameraLeft(){
        yR += -1.0f;
        leftRot += 45;
        if(leftRot ==360){
            leftRot = 0;
        }
    }

    /**
     * rotate the camera right
     */
    public void rotateCameraRight() {
        if (leftRot == 0) {
            leftRot = 360;
        }

        yR += 1.0f;
        leftRot -= 45;
    }

    /**
     * make the camera look up
     */
    public void rotateCameraUp(){
        xR += -1.0f;
        upRot += 45;
        if(leftRot == 360){
            leftRot = 0;
        }
    }

    /**
     * make the camera look down
     */
    public void rotateCameraDown(){
        xR += 1.0f;
        if(upRot == 0){
            upRot = 360;
        }
        upRot -=45;
    };

    /**
     * reset the variables after a movement to prevnet constant movement
     */
    public void reset(){
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        xR = 0.0f;
        yR = 0.0f;
        zR = 0.0f;
    }

}
