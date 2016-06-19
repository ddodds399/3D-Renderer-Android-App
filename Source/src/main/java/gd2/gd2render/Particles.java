package gd2.gd2render;

/**
 * Created by Andy on 24/11/2015.
 */
public class Particles {

    private float x,y,z;

    public Particles(float x1, float y1, float z1){
        x = x1;
        y = y1;
        z = z1;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    public void setX(float xx){
        x = xx;
    }
    public void setY(float yy){
        y = yy;
    }
    public void setZ(float zz){
        z = zz;
    }
}
