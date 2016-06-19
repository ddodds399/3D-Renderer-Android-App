package gd2.gd2render.Particles;

public class Vector3 {
    //floats
    public float x,y,z;

    /**
     * Constructor to copy another Vector 3 object
     * @param other - other Vector3 object you want to copy
     */
    public Vector3(Vector3 other){
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;

    }

    /**
     * Vector 3 constructor
     */
    public Vector3(){

    }

    /**
     * Method that set different values for x,y and z of Vector3
     * @param x
     * @param y
     * @param z
     * @return Vector3
     */
public Vector3 set(float x, float y, float z){
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
}

    /**
     * Add floats to x,y and z values
     * @param x
     * @param y
     * @param z
     * @return Vector3
     */
public Vector3 add(float x, float y, float z){
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
}

    /**
     * subtract floats from x,y and z values
     * @param x
     * @param y
     * @param z
     * @return Vector3
     */
public Vector3 sub(float x, float y, float z){
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
}

    /**
     * multiple all x,y and z values by times
     * @param times
     * @return Vector3
     */
public Vector3 mult(float times) {
    this.x *= x;
    this.y *= y;
    this.z *= z;
    return this;
}


}
