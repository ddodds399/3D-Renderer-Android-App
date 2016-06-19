package gd2.gd2render.Particles;

import junit.framework.Assert;
import junit.framework.TestCase;

import gd2.gd2render.R;


/**
 * Created by Andy on 30/11/2015.
 */
public class ParticleSystemTest extends TestCase{

    ParticleSystem pTest;


        public void setUp() throws Exception {
            super.setUp();
            pTest = new ParticleSystem();

        }

        public void tearDown() throws Exception {
           pTest = null;
        }

        public void testSmokeImage() throws Exception {
            pTest.resource1 = R.drawable.smoke;
            Assert.assertNotSame("Test", null, pTest.resource1);

        }
        public void testBubbleImage() throws Exception {
            pTest.resource1 = R.drawable.bubble;
            Assert.assertNotSame("Test2", null, pTest.resource1);
        }
        public void testParticleSmoke() throws Exception {
            int test1 = pTest.mParticles.length;
            assertEquals("Test3", 100, test1);
        }
        public void testParticleBubble() throws Exception {
            int test1 = pTest.m1Particles.length;
            assertEquals("Test4", 20, test1);
        }
        public void testParticlePositionX() throws Exception {
            Assert.assertTrue(pTest.mParticles[0].position.x >= 0 && pTest.mParticles[0].position.x < 1);

        }
        public void testParticlePositionY() throws Exception {
            Assert.assertTrue(pTest.mParticles[0].position.y >= 0 && pTest.mParticles[0].position.y < 1);
        }
        public void testParticlePositionZ() throws Exception {
            Assert.assertTrue(pTest.mParticles[0].position.z >= 0 && pTest.mParticles[0].position.z < 1);
        }
        public void testSmokeUpdatePositionX() throws Exception {
            float temp1 = pTest.mParticles[0].position.x;
            pTest.particleType =0;
            pTest.update();
            Assert.assertNotSame(pTest.mParticles[0].position.x, temp1);

        }
      public void testSmokeUpdatePositionY() throws Exception {
        float temp1 = pTest.mParticles[0].position.y;
        pTest.particleType =0;
        pTest.update();
        Assert.assertNotSame(pTest.mParticles[0].position.y, temp1);

    }
    public void testSmokeUpdatePositionZ() throws Exception {
        float temp1 = pTest.mParticles[0].position.z;
        pTest.particleType =0;
        pTest.update();
        Assert.assertNotSame(pTest.mParticles[0].position.z,temp1);

    }
    public void testSmokeUpdateVelocityX() throws Exception {

            float temp1 = pTest.mParticles[0].velocity.x;
            pTest.particleType =0;
            pTest.update();
            Assert.assertNotSame(pTest.mParticles[0].velocity.x,temp1);

        }
    public void testSmokeUpdateVelocityY() throws Exception {

        float temp1 = pTest.mParticles[0].velocity.y;
        pTest.particleType =0;
        pTest.update();
        Assert.assertNotSame(pTest.mParticles[0].velocity.y,temp1);

    }
    public void testSmokeUpdateVelocityZ() throws Exception {

        float temp1 = pTest.mParticles[0].velocity.z;
        pTest.particleType =0;
        pTest.update();
        Assert.assertNotSame(pTest.mParticles[0].velocity.z,temp1);

    }
    public void testSmokeLifespan() throws Exception {
        float temp1 = pTest.mParticles[0].lifeSpan;
        pTest.particleType = 1;
        pTest.update();
        Assert.assertNotSame(pTest.mParticles[0].lifeSpan, temp1);

    }
    public void testBubbleUpdatePositionX() throws Exception {
        float temp1 = pTest.m1Particles[0].position.x;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].position.x, temp1);

    }
    public void testBubbleUpdatePositionY() throws Exception {
        float temp1 = pTest.m1Particles[0].position.y;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].position.y, temp1);

    }
    public void testBubbleUpdatePositionZ() throws Exception {
        float temp1 = pTest.m1Particles[0].position.z;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].position.z, temp1);

    }
    public void testBubbleUpdateVelocityX() throws Exception {
        float temp1 = pTest.m1Particles[0].velocity.x;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].velocity.x, temp1);

    }
    public void testBubbleUpdateVelocityY() throws Exception {
        float temp1 = pTest.m1Particles[0].velocity.y;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].velocity.y, temp1);

    }
    public void testBubbleUpdateVelocityZ() throws Exception {
        float temp1 = pTest.m1Particles[0].velocity.z;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].velocity.z, temp1);

    }
    public void testBubbleUpdateLifespan() throws Exception {
        float temp1 = pTest.m1Particles[0].lifeSpan;
        pTest.particleType =1;
        pTest.update();
        Assert.assertNotSame(pTest.m1Particles[0].lifeSpan, temp1);

    }











}