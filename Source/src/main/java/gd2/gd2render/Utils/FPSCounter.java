package gd2.gd2render.Utils;


public class FPSCounter {
    static long sStartTime = System.nanoTime();
    static int sFrames = 0;
    static int sFrameRate;

    /**
     * method that logs the number of frames
     */
    public static void logFrame() {
        sFrames++;
        if(System.nanoTime() - sStartTime >= 1000000000) {
            //Log.d("FPSCounter", "fps: " + frames);
            // Set framerate to be equal to frames
            sFrameRate = sFrames;
            sFrames = 0;
            sStartTime = System.nanoTime();
        }
    }

    /**
     * method that returns the number of frames per second
     * @return sFrameRate
     */
    static public int getFrameRate(){
        return sFrameRate;
    }
}
