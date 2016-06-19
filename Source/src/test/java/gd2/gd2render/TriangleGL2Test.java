package gd2.gd2render;

import android.util.Log;

import junit.framework.TestCase;

import gd2.gd2render.Primitives.TriangleGL2;

/**
 * Created by James on 05/11/2015.
 */
public class TriangleGL2Test extends TestCase {

    TriangleGL2 triangle;

    public void setUp() throws Exception {
        super.setUp();
        triangle = new TriangleGL2();
    }

    public void testResetMMatrix() throws Exception {
        float[] testM = triangle.getModelMatrix();
        triangle.translate(1.0f, 0.0f, 0.0f);
        triangle.resetMMatrix();
        for (int i=0; i<testM.length; i++) {
            assertEquals(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotatation() throws Exception {
        float[] testM = triangle.getModelMatrix();
        triangle.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation1() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 1", String.valueOf(index));
        }
        triangle.rotate(0.0f,1.0f,0.0f,0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 1", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertEquals(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation2() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 2",String.valueOf(index));
        }
        triangle.rotate(20.0f,0.0f,1.0f,0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation3() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 3",String.valueOf(index));
        }
        triangle.rotate(30.0f,0.0f,0.0f,1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation4() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 4",String.valueOf(index));
        }
        triangle.rotate(40.0f,1.0f,1.0f,0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation5() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 5",String.valueOf(index));
        }
        triangle.rotate(50.0f,0.0f,1.0f,1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation6() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 6",String.valueOf(index));
        }
        triangle.rotate(60.0f,1.0f,0.0f,1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 6", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation7() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 7",String.valueOf(index));
        }
        triangle.rotate(70.0f, 1.0f, 1.0f, 1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation8() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 8",String.valueOf(index));
        }
        triangle.rotate(80.0f, 0.0f, 1.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testRotation9() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Rotation 9",String.valueOf(index));
        }
        triangle.rotate(90.0f, 1.0f, 1.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Rotation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation() throws Exception {
        float[] testM = triangle.getModelMatrix();
        triangle.translate(5.0f, 0.0f, 3.0f);
        for (int i=0; i<testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation1() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 1",String.valueOf(index));
        }
        triangle.translate(1.0f, 0.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 1", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation2() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        triangle.translate(0.0f, 1.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation3() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 3",String.valueOf(index));
        }
        triangle.translate(0.0f, 0.0f, 1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 3", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation4() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 4",String.valueOf(index));
        }
        triangle.translate(1.0f, 1.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 4", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation5() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 5",String.valueOf(index));
        }
        triangle.translate(0.0f, 1.0f, 1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 5", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation6() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 2",String.valueOf(index));
        }
        triangle.translate(1.0f, 0.0f, 1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 2", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation7() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 7",String.valueOf(index));
        }
        triangle.translate(1.0f, 1.0f, 1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 7", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }
    public void testTranslation8() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 8",String.valueOf(index));
        }
        triangle.translate(0.0f, -5.0f, 0.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 8", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testTranslation9() throws Exception{
        float[] testM = triangle.getModelMatrix();
        for (float index:testM) {
            Log.d("Original: Translation 9",String.valueOf(index));
        }
        triangle.translate(-1.0f,0.0f,-1.0f);
        for (float index:triangle.getModelMatrix()) {
            Log.d("New: Translation 9", String.valueOf(index));
        }
        for (int i=0; i < testM.length; i++) {
            assertNotSame(testM[i], triangle.getModelMatrix()[i]);
        }
    }

    public void testGetModelMatrix() throws Exception {
        assertNotNull(triangle.getModelMatrix());
    }

    public void testGetTriangleVertices() throws Exception {
        assertNotNull(triangle.getTriangleVertices());
    }

    public void tearDown() throws Exception {
        triangle = null;
    }

}