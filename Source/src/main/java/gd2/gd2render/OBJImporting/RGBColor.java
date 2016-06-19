package gd2.gd2render.OBJImporting;

public class RGBColor {
	float R;
	float G;
	float B;

	/**
	 * Sets RGB components
	 * @param r - red value
	 * @param g - green value
	 * @param b - blue value
	 */
	public RGBColor(float r, float g, float b) {
		super();
		R = r;
		G = g;
		B = b;
	}

    /**
     * returns the red value
     * @return R
     */
	public float getR() {
		return R;
	}

    /**
     * sets the red value
     * @param r - value to change red to
     */
	public void setR(float r) {
		R = r;
	}

    /**
     * returns the green value
     * @return G
     */
	public float getG() {
		return G;
	}

    /**
     * sets the green value
     * @param g - value to change green to
     */
	public void setG(float g) {
		G = g;
	}

    /**
     * gets the blue value
     * @return B
     */
	public float getB() {
		return B;
	}

    /**
     * sets the blue value
     * @param b - value to change blue to
     */
	public void setB(float b) {
		B = b;
	}

    /**
     * returns the RGB values as a string
     * @return str - RGB values all together in a string
     */
	public String toString(){
		String str=new String();
		str+="R:"+R+" G:"+G+" B:"+B;
		return str;
	}
}
