package gd2.gd2render.Particles;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import gd2.gd2render.Particles.Particles;
import gd2.gd2render.R;


public class ParticleSystem {
    //number of particles of smoke and bubble
    public final int PARTICLE_COUNT_SMOKE = 100;
    public final int PARTICLE_COUNT_BUBBLE= 20;
    //magnitude of gravity
    public float GRAVITY = -1.0f;
    //size of particles
    public float PSIZE = 1.5f;
    //size of particle of GLES20
    private final int PARTICLE_SIZE = 2;

    private float mTime = 1.0f;

    //type of particle, resource of particle
    public int particleType, resource1;
    //array storing smoke and bubble particles
    public Particles[] mParticles,m1Particles;
    //buffers
    public FloatBuffer vBuff;
    public FloatBuffer tBuff;
    public ByteBuffer iBuff;
    //
    public long lastTime;
    //2 Random generators to get random float values
    public Random mGen, mGen1;
    //to bind texture
    public int mTexture;

    //Matrix
    public float[] mModelMatrix = new float[16];

    /**
     * Constructor for Particle System
     */
    public ParticleSystem() {


           // Create new array of smoke particles containing 100 smoke particles
            mParticles = new Particles[PARTICLE_COUNT_SMOKE];
            //m1Particles = new Particles[PARTICLE_COUNT_SMOKE];

            mGen = new Random(System.currentTimeMillis());
            //go through the smoke particle array and generate random float for each x,y,z
            for (int i = 0; i < PARTICLE_COUNT_SMOKE; i++) {
                mParticles[i] = new Particles(mGen.nextFloat(), mGen.nextFloat(), mGen.nextFloat(), R.drawable.smoke);

            }
            //create new array of bubble particles containing 20 bubble particles
            m1Particles = new Particles[PARTICLE_COUNT_BUBBLE];
            mGen1 = new Random(System.currentTimeMillis());
            //go through the bubble particle array and generate random float for each x,y,z
            for (int i = 0; i < PARTICLE_COUNT_BUBBLE; i++) {
                m1Particles[i] = new Particles(mGen1.nextFloat(), mGen1.nextFloat(), mGen1.nextFloat(), R.drawable.bubble);
            }


        //array holding vertices
        float vertices[] = {
                -PSIZE,	-PSIZE,	1.0f,
                PSIZE,	-PSIZE,	1.0f,
                -PSIZE,	PSIZE,	1.0f,
                PSIZE,	PSIZE,	1.0f,
        };


        float textures[] = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        byte indices[] = {
                0, 1, 3,
                0, 3, 2
        };

        //vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length*4);
        bb.order(ByteOrder.nativeOrder());
        vBuff = bb.asFloatBuffer();
        vBuff.put(vertices);
        vBuff.position(0);

        ByteBuffer bb1 = ByteBuffer.allocateDirect(vertices.length*4);
        bb1.order(ByteOrder.nativeOrder());
        tBuff = bb1.asFloatBuffer();
        tBuff.put(textures);
        tBuff.position(0);

        //index buffer
        iBuff = ByteBuffer.allocateDirect(indices.length);
        iBuff.order(ByteOrder.nativeOrder());
        iBuff.put(indices);
        iBuff.position(0);
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    /**
     * Update method to render animation of particles
     */
    public void update() {
        //current time
        long currentTime = System.currentTimeMillis();
        //time between each update
        float timeFrame = (currentTime - lastTime) / 1000f;
        //set last time to current time
        lastTime = currentTime;
        if (particleType == 0) {
            //change velocity and position vectors in each element of the array
            for (int i = 0; i < PARTICLE_COUNT_SMOKE; i++) {
                mParticles[i].velocity.y -= (GRAVITY * timeFrame);

                mParticles[i].velocity.x -= (GRAVITY * timeFrame);

                mParticles[i].velocity.z -= (GRAVITY * timeFrame);

                mParticles[i].position.x += mParticles[i].velocity.x * timeFrame;

                mParticles[i].position.y += mParticles[i].velocity.y * timeFrame;

                mParticles[i].position.z += mParticles[i].velocity.z * timeFrame;

                //decrease lifespan by a random float brightness
                mParticles[i].lifeSpan -= mParticles[i].brightness;

                //call method if lifespan is less than 0.45
                if (mParticles[i].lifeSpan < 0.45f)
                    initParticleSmokeAndBubble(i);
            }
        } else if (particleType == 1) {
            for (int i = 0; i < PARTICLE_COUNT_BUBBLE; i++) {
                m1Particles[i].velocity.y -= (GRAVITY * timeFrame);

                m1Particles[i].velocity.x -= (GRAVITY * timeFrame);

                m1Particles[i].velocity.z -= (GRAVITY * timeFrame);

                m1Particles[i].position.x += m1Particles[i].velocity.x * timeFrame;

                m1Particles[i].position.y += m1Particles[i].velocity.y * timeFrame;

                m1Particles[i].position.z += m1Particles[i].velocity.z * timeFrame;


                m1Particles[i].lifeSpan -= m1Particles[i].brightness;
                if (m1Particles[i].lifeSpan < 0.1) {
                    initParticleSmokeAndBubble(i);
                }

            }
        }
    }

    /**
     * Update method for GLES20 Renderer
     * @param mCenterPositionLoc
     * @param mColorLoc
     */
    public void update20(int mCenterPositionLoc, int mColorLoc){

            if (lastTime == 0)
                lastTime = SystemClock.uptimeMillis();
            long curTime = SystemClock.uptimeMillis();
        //time between current time and last time
            long elapsedTime = curTime - lastTime;
            float deltaTime = elapsedTime / 1000.0f;
            lastTime = curTime;

            mTime = deltaTime;

            if ( mTime >= 1.0f )
            {
                Random generator = new Random();
                float[] centerPos = new float[3];
                float[] color = new float[4];

                mTime = 0.0f;

                //  new start location
                centerPos[0] = generator.nextFloat() * 1.0f - 0.5f;
                centerPos[1] = generator.nextFloat() * 1.0f - 0.5f;
                centerPos[2] = generator.nextFloat() * 1.0f - 0.5f;

                GLES20.glUniform3f ( mCenterPositionLoc, centerPos[0], centerPos[1], centerPos[2]);

                // Random color
                color[0] = generator.nextFloat() * 0.5f + 0.5f;
                color[1] = generator.nextFloat() * 0.5f + 0.5f;
                color[2] = generator.nextFloat() * 0.5f + 0.5f;
                color[3] = 0.5f;

                GLES20.glUniform4f ( mColorLoc, color[0], color[1], color[2], color[3] );

            }
        //update velocity and position vectors in each element of smoke array
        for (int i = 0; i < PARTICLE_COUNT_SMOKE; i++) {
            mParticles[i].velocity.y -= (GRAVITY * deltaTime);

            mParticles[i].velocity.x -= (GRAVITY * deltaTime);

            mParticles[i].velocity.z -= (GRAVITY * deltaTime);

            mParticles[i].position.x += mParticles[i].velocity.x * deltaTime;

            mParticles[i].position.y += mParticles[i].velocity.y * deltaTime;

            mParticles[i].position.z += mParticles[i].velocity.z * deltaTime;


            mParticles[i].lifeSpan -= mParticles[i].brightness;


            if (mParticles[i].lifeSpan < 0.45f)
                initParticleSmokeAndBubble(i);
        }


    }

    /**
     * Method to spawn new instance of particle when lifespan is below minmum amount
     * @param i- index i
     */
    private void initParticleSmokeAndBubble(int i) {
        if (particleType == 0) {
            mParticles[i].lifeSpan = 0.8f;
            //Random float for brightness
            mParticles[i].brightness = (mGen.nextFloat() % 100.0f) / 500.0f;
            //set particle position back to the centre
            mParticles[i].position.x = 0.0f;
            mParticles[i].position.y = 0f;
            mParticles[i].position.z = 0.0f;

            mParticles[i].velocity.x = (mGen.nextFloat() * 2.0f) - 1.5f;
            mParticles[i].velocity.y = (mGen.nextFloat() * 2.0f) + 1.0f;
            mParticles[i].velocity.z = (mGen.nextFloat() * 2.0f) - 1.0f;
            Log.d("draw", "image moved");
        } else if (particleType == 1) {
            m1Particles[i].lifeSpan = 0.8f;
            m1Particles[i].brightness = (mGen.nextFloat() % 100.0f) / 500.0f;

            m1Particles[i].position.x = 0.0f;
            m1Particles[i].position.y = 0f;
            m1Particles[i].position.z = 0.0f;

            m1Particles[i].velocity.x = (mGen.nextFloat() * 2.0f) - 1.5f;
            m1Particles[i].velocity.y = (mGen.nextFloat() * 2.0f) + 1.0f;
            m1Particles[i].velocity.z = (mGen.nextFloat() * 2.0f) - 1.0f;


        } else {
            mParticles[i].lifeSpan = 0.8f;
            mParticles[i].brightness = (mGen.nextFloat() % 100.0f) / 500.0f;

            mParticles[i].position.x = 0.0f;
            mParticles[i].position.y = 0f;
            mParticles[i].position.z = 0.0f;

            mParticles[i].velocity.x = (mGen.nextFloat() * 2.0f) - 1.5f;
            mParticles[i].velocity.y = (mGen.nextFloat() * 2.0f) + 1.0f;
            mParticles[i].velocity.z = (mGen.nextFloat() * 2.0f) - 1.0f;
            Log.d("draw", "image moved");

        }
    }

    /**
     * Draw method for GLES2.0
     * @param lifeLocHandle
     * @param endLocHandle
     * @param startLocHandle
     * @param sampleLocHandle
     */
    public void draw20(int lifeLocHandle, int endLocHandle, int startLocHandle, int sampleLocHandle) {


        // Load the vertex attributes
        vBuff.position(0);
        GLES20.glVertexAttribPointer(lifeLocHandle, 1, GLES20.GL_FLOAT,
                false, PARTICLE_SIZE, vBuff);

        vBuff.position(1);
        GLES20.glVertexAttribPointer(endLocHandle, 3, GLES20.GL_FLOAT,
                false, PARTICLE_SIZE, vBuff);

        vBuff.position(4);
        GLES20.glVertexAttribPointer(startLocHandle, 3, GLES20.GL_FLOAT,
                false, PARTICLE_SIZE, vBuff);


        GLES20.glEnableVertexAttribArray(lifeLocHandle);
        GLES20.glEnableVertexAttribArray(endLocHandle);
        GLES20.glEnableVertexAttribArray(startLocHandle);



        // Blend particles
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);

        // Set the sampler texture unit to 0
        GLES20.glUniform1i(sampleLocHandle, 0);

        for (int i = 0; i < PARTICLE_COUNT_SMOKE; i++) {

            translate(mParticles[i].position.x, mParticles[i].position.y, mParticles[i].position.z);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, PARTICLE_COUNT_SMOKE);
            //GLES20.glDrawElements(GL10.GL_TRIANGLES, iBuff.capacity(), GL10.GL_UNSIGNED_BYTE, iBuff);

        }
        //Log.d("drawn", "draw20() called with: " + "lifeLocHandle = [" + lifeLocHandle + "], endLocHandle = [" + endLocHandle + "], startLocHandle = [" + startLocHandle + "], sampleLocHandle = [" + sampleLocHandle + "], mTextureHandle = [" + mTextureHandle + "]");

    }

    /**
     * Draw method for GL10 instance
     * @param gl
     */
    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuff);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tBuff);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        //bind texture
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glFrontFace(GL10.GL_CCW);
        //draw each particle of the array and move according to random float values
        if (particleType ==0) {
            for (int i = 0; i < PARTICLE_COUNT_SMOKE; i++) {
                gl.glPushMatrix();
                gl.glColor4f(1, 1, 1, mParticles[i].lifeSpan);
                gl.glTranslatef(mParticles[i].position.x, mParticles[i].position.y, mParticles[i].position.z);
                gl.glDrawElements(GL10.GL_TRIANGLES, iBuff.capacity(), GL10.GL_UNSIGNED_BYTE, iBuff);
                gl.glPopMatrix();
            }
        }
        if(particleType ==1) {
            for (int i = 0; i < PARTICLE_COUNT_BUBBLE; i++) {
                gl.glPushMatrix();
                gl.glColor4f(1, 1, 1, m1Particles[i].lifeSpan);
                gl.glTranslatef(m1Particles[i].position.x, m1Particles[i].position.y, m1Particles[i].position.z);
                gl.glDrawElements(GL10.GL_TRIANGLES, iBuff.capacity(), GL10.GL_UNSIGNED_BYTE, iBuff);
                gl.glPopMatrix();

            }
        }
        gl.glDisable(GL10.GL_TEXTURE_2D);


        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

    }

    /**
     * Method for translating particle.
     *
     * @param x - units in 'x' axis
     * @param y - units in 'y' axis
     * @param z - units in 'z' axis
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
    }



}