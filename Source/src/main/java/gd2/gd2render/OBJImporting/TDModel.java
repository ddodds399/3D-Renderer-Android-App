package gd2.gd2render.OBJImporting;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class TDModel {
	Vector<Float> v;
	Vector<Float> vn;
	Vector<Float> vt;
	Vector<TDModelPart> parts;
	FloatBuffer vertexBuffer, texBuffer;
	float rAngle = 0f, r2 = 0f, r3 = 0f, r4 = 0f;

	/**
	 * Store the model matrix.
	 */
	private float[] mModelMatrix = new float[16];

	public TDModel(Vector<Float> v, Vector<Float> vn, Vector<Float> vt,
			Vector<TDModelPart> parts) {
		super();
		this.v = v;
		this.vn = vn;
		this.vt = vt;
		this.parts = parts;

		Matrix.setIdentityM(mModelMatrix, 0);
	}



	public String toString(){
		String str=new String();
		str+="Number of parts: "+parts.size();
		str+="\nNumber of vertexes: "+v.size();
		str+="\nNumber of vns: "+vn.size();
		str+="\nNumber of vts: "+vt.size();
		str+="\n/////////////////////////\n";
		for(int i=0; i<parts.size(); i++){
			str+="Part "+i+'\n';
			str+=parts.get(i).toString();
			str+="\n/////////////////////////";
		}
		return str;
	}

	//Method for resetting identity matrix after transformations have been applied.
	public void resetMMatrix() {
		Matrix.setIdentityM(mModelMatrix, 0);
	}

	/**
	 * Method for rotating model
	 *
	 * @param angle - angle of rotation
	 * @param x     - enter 1.0f to rotate around 'x' axis
	 * @param y     - enter 1.0f to rotate around 'y' axis
	 * @param z     - enter 1.0f to rotate around 'z' axis
	 */
	public void rotate(float angle, float x, float y, float z) {
		Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
	}

	/**
	 * Method for translating model
	 *
	 * @param x - units in 'x' axis
	 * @param y - units in 'y' axis
	 * @param z - units in 'z' axis
	 */
	public void translate(float x, float y, float z) {
		Matrix.translateM(mModelMatrix, 0, x, y, z);
	}

	/**
	 * Draws a model in GL10
	 * @param gl - gl10 interface
	 */
	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glRotatef(rAngle, r2, r3, r4);
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glTranslatef(0f, -10f, 5f);
		
		for(int i=0; i<parts.size(); i++){
			TDModelPart t=parts.get(i);
			Material m=t.getMaterial();
			if(m!=null){
				FloatBuffer a=m.getAmbientColorBuffer();
				FloatBuffer d=m.getDiffuseColorBuffer();
				FloatBuffer s=m.getSpecularColorBuffer();
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,a);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,s);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,d);
			}
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, t.getNormalBuffer());
			gl.glDrawElements(GL10.GL_TRIANGLES, t.getFacesCount(), GL10.GL_UNSIGNED_SHORT, t.getFaceBuffer());
			//gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
	}
	/**
	 * Draws a model from the given data in GL20
	 *
	 * @param posHandle    - Model position information.
	 * @param mvpHandle    - Transformation Matrix information.
	 * @param mvpMat       - ModelViewProjection Matrix from renderer
	 * @param viewMat      - View Matrix from renderer
	 * @param projMat      - Projection Matrix from render
	 */
		public void draw20(int posHandle, int mvpHandle, float[] mvpMat, float[] viewMat, float[] projMat){
			for(int i=0; i<parts.size(); i++){
				TDModelPart t=parts.get(i);

			final int[] vbo = new int[1];
			final int[] ibo = new int[1];
			GLES20.glGenBuffers(1, vbo, 0);
			GLES20.glGenBuffers(1, ibo, 0);

			if (vbo[0]>0 && ibo[0] >0) {
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
				GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4,
						vertexBuffer, GLES20.GL_STATIC_DRAW);

				GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
						0, 0);
				GLES20.glEnableVertexAttribArray(posHandle);

				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
				GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, t.getFaceBuffer().capacity()
						* 2, t.getFaceBuffer(), GLES20.GL_STATIC_DRAW);
				Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);

				// This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
				Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);

				GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);

				GLES20.glDrawElements(GLES20.GL_TRIANGLES, t.getFacesCount(), GLES20.GL_UNSIGNED_SHORT, 0);

				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

			}}
			}

	/**
	 * Draws a model from the given  data.
	 *
	 * @param posHandle    - Model position information.
	 * @param colourHandle - Model Colour Information.
	 * @param normalHandle - Model normal information.
	 * @param mvpHandle    - Transformation Matrix information.
	 * @param mvHandle     - ModelView Matrix information.
	 * @param lightHandle  - Light position information
	 * @param texHandle    - Texture information
	 * @param mvpMat       - ModelViewProjection Matrix from renderer
	 * @param viewMat      - View Matrix from renderer
	 * @param projMat      - Projection Matrix from render
	 * @param lightEye     - Light position in eye space
	 */
	public void drawTextured(int posHandle, int colourHandle, int normalHandle, int mvpHandle, int mvHandle, int lightHandle, int texHandle, float[] mvpMat, float[] viewMat, float[] projMat, float[] lightEye){
		for(int i=0; i<parts.size(); i++){
			TDModelPart t=parts.get(i);

			final int[] vbo = new int[1];
			final int[] ibo = new int[1];

			GLES20.glGenBuffers(1, vbo, 0);
			GLES20.glGenBuffers(1, ibo, 0);

			if (vbo[0]>0 && ibo[0] >0) {
				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0]);
				GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4,
						vertexBuffer, GLES20.GL_STATIC_DRAW);

				GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false,
						0, 0);
				GLES20.glEnableVertexAttribArray(posHandle);

					GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
							0, t.getNormalBuffer());
					GLES20.glEnableVertexAttribArray(normalHandle);

					GLES20.glVertexAttribPointer(texHandle, 3, GLES20.GL_FLOAT, false,
							0, texBuffer);
					GLES20.glEnableVertexAttribArray(texHandle);

				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
				GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, t.getFaceBuffer().capacity()
						* 2, t.getFaceBuffer(), GLES20.GL_STATIC_DRAW);
				Matrix.multiplyMM(mvpMat, 0, viewMat, 0, mModelMatrix, 0);

				// Pass in the modelview matrix.
				GLES20.glUniformMatrix4fv(mvHandle, 1, false, mvpMat, 0);
				// This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
				Matrix.multiplyMM(mvpMat, 0, projMat, 0, mvpMat, 0);

				GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMat, 0);
				GLES20.glUniform3f(lightHandle, lightEye[0], lightEye[1], lightEye[2]);
				GLES20.glDrawElements(GLES20.GL_TRIANGLES, t.getFacesCount(), GLES20.GL_UNSIGNED_SHORT, 0);

				GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

				GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

			}}
	}

	/**
	 * build the vertex buffer
	 */
	public void buildVertexBuffer(){
		ByteBuffer vBuf = ByteBuffer.allocateDirect(v.size() * 4);
		vBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = vBuf.asFloatBuffer();
		vertexBuffer.put(toPrimitiveArrayF(v));
		vertexBuffer.position(0);
	}

	/**
	 * build the texture buffer
	 */
	public void buildTexBuffer(){
		ByteBuffer vBuf = ByteBuffer.allocateDirect(vt.size() * 4);
		vBuf.order(ByteOrder.nativeOrder());
		texBuffer = vBuf.asFloatBuffer();
		texBuffer.put(toPrimitiveArrayF(vt));
		texBuffer.position(0);
	}
	private static float[] toPrimitiveArrayF(Vector<Float> vector){
		float[] f;
		f=new float[vector.size()];
		for (int i=0; i<vector.size(); i++){
			f[i]=vector.get(i);
		}
		return f;
	}

	/**
	 * method for roating GL10 version of the model, takes in an angle for rotation and each axsis it should be rotated on
	 * @param angle - angle for the rotation
	 * @param x     - set to 1.0 to rotate around x-axis
	 * @param y     - set to 1.0 to rotate around y-axis
	 * @param z     - set to 1.0 to rotate around z-axis
	 */
	public void rotate10(float angle, float x, float y, float z) {
		rAngle = angle;
		r2 = x;
		r3 = y;
		r4 = z;
	}

	public float getrAngle() {
		return rAngle;
	}
}


