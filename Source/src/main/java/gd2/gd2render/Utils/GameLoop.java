package gd2.gd2render.Utils;


import android.util.Log;


/**
 * This is a currently unimplemented gameloop, it is for future expansion.
 */
public abstract class GameLoop implements Runnable {

    //Instance variables
    private int targetFramesPerSecond;
    private Thread renderThread = null;
    private volatile boolean running = false;
    private long targetUpdatePeriod;

    /**
     * This is the constructor for the game loop
     *
     * @param targetFPS this is the Frames per second that we want our app to run at
     */
    public GameLoop(int targetFPS) {
        targetFramesPerSecond = targetFPS;
        targetUpdatePeriod = 1000000000 / targetFramesPerSecond;
        run();
    }


    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     *
     * The run method is the method that contains the code that runs on the other thread
     * It is automatically called
     */
    @Override
    public void run() {

        long startTime, endTime;
        long sleepTime, overSleepTime;
        long timeBeyondPeriod;

        startTime = System.nanoTime() - targetUpdatePeriod;

        overSleepTime = 0;
        timeBeyondPeriod = 0;

        //The main loop that deals with the FPS
        try {
            while (running) {
                startTime = System.nanoTime();

                doUpdate();
                doDraw();

                endTime = System.nanoTime();
                sleepTime = (targetUpdatePeriod - (startTime - endTime)) - overSleepTime;

                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 10000000L);

                    overSleepTime = (System.nanoTime() - endTime) - sleepTime;
                } else {

                    timeBeyondPeriod = timeBeyondPeriod - sleepTime;
                    overSleepTime = 0;

                    while (timeBeyondPeriod > targetUpdatePeriod) {
                        doUpdate();
                        timeBeyondPeriod = -targetUpdatePeriod;
                    }
                }
            }
        } catch (InterruptedException e) {
            Log.w("Loop", "Interrupt has occured");
        }
    }

    /**
     * The abstract draw method that will get overridden in the main game fragment
     */
    public abstract void doDraw();

    /**
     * The abstract update method that will get overridden in the main game fragment
     */
    public abstract void doUpdate();

    /**
     * The method used to pause the game loop and can be called from the other class
     * It sets the running to false and removes the other thread
     */
    public void pause() {
        running = false;

        while (true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                Log.e("Game Loop", "pauing the render thread failed");
            }
        }
    }

    /**
     * The resume method has to create a new instance of the thread because of the way we deal with pausing
     */
    public void resume() {
        running = true;

        renderThread = new Thread(this);
        renderThread.start();
    }
}
