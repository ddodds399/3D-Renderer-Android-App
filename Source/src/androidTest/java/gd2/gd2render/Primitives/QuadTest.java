package gd2.gd2render.Primitives;

import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by Dean on 27/11/2015.
 */
public class QuadTest extends TestCase {

    Quad quad;

    public void setUp() throws Exception {
        super.setUp();
        quad = new Quad();
    }

    public void tearDown() throws Exception {
        quad = null;
    }

    public void testResetMMatrix() throws Exception {
        float[] testM = quad.getModelMatrix();
        quad.translate(1.0f,0.0f,0.0f);
        quad.resetMMatrix();
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotate() throws Exception {
        float[] testM = quad.getModelMatrix();
        quad.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslate() throws Exception {
        float[] testM = quad.getModelMatrix();
        quad.translate(5.0f, 0.0f, 3.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testGetQuadVertices() throws Exception {
        assertNotNull(quad.getQuadVertices());
    }

    public void testGetModelMatrix() throws Exception {
        assertNotNull(quad.getModelMatrix());
    }

    public void testGetQuadVertexData() throws Exception {
        float[] testM = new float[]
                {
                        // Left bottom triangle
                        -1.0f, 1.0f, 0f,
                        -1.0f, -1.0f, 0f,
                        1.0f, -1.0f, 0f,
                        // Right top triangle
                        1.0f, -1.0f, 0f,
                        1.0f, 1.0f, 0f,
                        -1.0f, 1.0f, 0f
                };
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], quad.getQuadVertexData()[i]);
        }
    }

    public void testRotation1() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 1", String.valueOf(index));
        }
        quad.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 1", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertEquals(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation2() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 2",String.valueOf(index));
        }
        quad.rotate(20.0f,0.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation3() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 3",String.valueOf(index));
        }
        quad.rotate(30.0f,0.0f,0.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation4() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 4",String.valueOf(index));
        }
        quad.rotate(40.0f,1.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation5() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 5",String.valueOf(index));
        }
        quad.rotate(50.0f,0.0f,1.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation6() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 6",String.valueOf(index));
        }
        quad.rotate(60.0f,1.0f,0.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 6", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation7() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 7",String.valueOf(index));
        }
        quad.rotate(70.0f,1.0f,1.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation8() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 8",String.valueOf(index));
        }
        quad.rotate(80.0f,0.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testRotation9() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 9",String.valueOf(index));
        }
        quad.rotate(90.0f,1.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Rotation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation1() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 1",String.valueOf(index));
        }
        quad.translate(1.0f, 0.0f, 0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 1", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation2() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        quad.translate(0.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation3() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 3",String.valueOf(index));
        }
        quad.translate(0.0f,0.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation4() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 4",String.valueOf(index));
        }
        quad.translate(1.0f,1.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation5() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 5",String.valueOf(index));
        }
        quad.translate(0.0f,1.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation6() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        quad.translate(1.0f,0.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation7() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 7",String.valueOf(index));
        }
        quad.translate(1.0f,1.0f,1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }
    public void testTranslation8() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 8",String.valueOf(index));
        }
        quad.translate(0.0f,-5.0f,0.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }

    public void testTranslation9() throws Exception{
        float[] testM = quad.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 9",String.valueOf(index));
        }
        quad.translate(-1.0f,0.0f,-1.0f);
        for (float index:quad.getModelMatrix()) {
            Log.d("New: Translation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], quad.getModelMatrix()[i]);
        }
    }
}