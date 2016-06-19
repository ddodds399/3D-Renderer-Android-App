package gd2.gd2render;

import android.util.Log;

import junit.framework.TestCase;

import gd2.gd2render.Primitives.CubeGL2;

/**
 * Created by Dean on 05/11/2015.
 */
public class CubeGL2Test extends TestCase {

    CubeGL2 cube;

    public void setUp() throws Exception {
        super.setUp();
      cube = new CubeGL2();
    }

    public void tearDown() throws Exception {
        cube = null;
    }

    public void testResetMMatrix() throws Exception {
        float[] testM = cube.getModelMatrix();
        cube.translate(1.0f,0.0f,0.0f);
        cube.resetMMatrix();
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotate() throws Exception {
        float[] testM = cube.getModelMatrix();
        cube.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslate() throws Exception {
        float[] testM = cube.getModelMatrix();
        cube.translate(5.0f, 0.0f, 3.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testGetModelMatrix() throws Exception {
        assertNotNull(cube.getModelMatrix());
    }

    public void testGetCubeVertices() throws Exception {
        assertNotNull(cube.getCubeVertices());
    }

    public void testGetCubeColours() throws Exception {
        assertNotNull(cube.getCubeColours());
    }

    public void testGetCubeNormals() throws Exception {
        assertNotNull(cube.getCubeNormals());
    }

    public void testGetmCubeTextureCoordinates() throws Exception {
        assertNotNull(cube.getmCubeTextureCoordinates());
    }

    public void testGetCubeTextureData() throws Exception {

        float[] testM = new float[]
                {
                        // Front face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Right face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Back face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Left face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Top face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                        // Bottom face
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f
                };
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], cube.getCubeTextureCoordinateData()[i]);
        }
    }

    public void testGetCubeNormalData() throws Exception {

        float[] testM = new float[]
                {
                        // Front face
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 1.0f,
                        // Right face
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 0.0f, 0.0f,
                        // Back face
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        0.0f, 0.0f, -1.0f,
                        // Left face
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, 0.0f,
                        // Top face
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        // Bottom face
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f
                };
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], cube.getCubeNormalData()[i]);
        }
    }

    public void testGetCubeColourData() throws Exception {

        float[] testM = new float[]
                {
                        // Front face
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        1.0f, 0.0f, 0.0f, 1.0f,
                        // Right face
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        0.0f, 1.0f, 0.0f, 1.0f,
                        // Back face
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        0.0f, 0.0f, 1.0f, 1.0f,
                        // Left face
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        1.0f, 1.0f, 0.0f, 1.0f,
                        // Top face
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 1.0f, 1.0f,
                        // Bottom face
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f,
                        1.0f, 0.0f, 1.0f, 1.0f
                };
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], cube.getCubeColourData()[i]);
        }
    }

    public void testGetCubeVertexData() throws Exception {

        float[] testM = new float[]
                {
                        // Front face
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        // Right face
                        1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,
                        // Back face
                        1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        // Left face
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        // Top face
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,
                        // Bottom face
                        1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                };
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], cube.getCubeVertexData()[i]);
        }
    }

    public void testRotation1() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 1",String.valueOf(index));
        }
        cube.rotate(0.0f,1.0f,0.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 1", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertEquals(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation2() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 2",String.valueOf(index));
        }
        cube.rotate(20.0f,0.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation3() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 3",String.valueOf(index));
        }
        cube.rotate(30.0f,0.0f,0.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation4() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 4",String.valueOf(index));
        }
        cube.rotate(40.0f,1.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation5() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 5",String.valueOf(index));
        }
        cube.rotate(50.0f,0.0f,1.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation6() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 6",String.valueOf(index));
        }
        cube.rotate(60.0f,1.0f,0.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 6", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation7() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 7",String.valueOf(index));
        }
        cube.rotate(70.0f,1.0f,1.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation8() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 8",String.valueOf(index));
        }
        cube.rotate(80.0f,0.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testRotation9() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 9",String.valueOf(index));
        }
        cube.rotate(90.0f,1.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Rotation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation1() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
         Log.d("Original: Translation 1",String.valueOf(index));
        }
         cube.translate(1.0f, 0.0f, 0.0f);
            for (float index:cube.getModelMatrix()) {
          Log.d("New: Translation 1", String.valueOf(index));
         }
        for (int i=0; i < testM.length; i++) {
        assertNotSame(testM[i], cube.getModelMatrix()[i]);
         }
    }

    public void testTranslation2() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        cube.translate(0.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation3() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 3",String.valueOf(index));
        }
        cube.translate(0.0f,0.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation4() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 4",String.valueOf(index));
        }
        cube.translate(1.0f,1.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation5() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 5",String.valueOf(index));
        }
        cube.translate(0.0f,1.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation6() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        cube.translate(1.0f,0.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation7() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 7",String.valueOf(index));
        }
        cube.translate(1.0f,1.0f,1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }
    public void testTranslation8() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 8",String.valueOf(index));
        }
        cube.translate(0.0f,-5.0f,0.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }

    public void testTranslation9() throws Exception{
        float[] testM = cube.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 9",String.valueOf(index));
        }
        cube.translate(-1.0f,0.0f,-1.0f);
        for (float index:cube.getModelMatrix()) {
            Log.d("New: Translation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], cube.getModelMatrix()[i]);
        }
    }
}