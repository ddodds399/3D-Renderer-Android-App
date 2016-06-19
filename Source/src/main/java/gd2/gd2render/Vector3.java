package gd2.gd2render;

/**
 * Created by Andy on 24/11/2015.
 */
public class Vector3 {
    public float x,y,z;

    public Vector3(Vector3 other){
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;

    }
public Vector3 set(float x, float y, float z){
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
}
public Vector3 add(float x, float y, float z){
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
}
public Vector3 sub(float x, float y, float z){
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
}
public Vector3 mult(float times) {
    this.x *= x;
    this.y *= y;
    this.z *= z;
    return this;
}


}
