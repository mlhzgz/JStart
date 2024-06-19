package jstart;

/**
 * Timeout class emulating JavaScript funciton setTimeout()
 */
public class Timeout {
    private Runnable code;
    private Thread thread;

    private Timeout(long ms, boolean asInterval, Runnable code) {
        this.code = code;
        this.thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {

                }

                code.run();

                if (!asInterval)
                    break;
            }
        });
    }

    /**
     * Runs code in background after a number of milliseconds
     * @param ms milliseconds before code run
     * @param code lambda to run 
     * @return Timeout object
     */
    public static Timeout run(long ms, Runnable code) {
        Timeout to = new Timeout(ms, false, code);

        to.thread.start();

        return to;
    }

    /**
     * Runs code in background repeteadly after a number of milliseconds (like JS setInterval())
     * @param ms milliseconds before code run
     * @param code lambda to run 
     * @return Timeout object
     */
    public static Timeout runAsInterval(long ms, Runnable code) {
        Timeout to = new Timeout(ms, true, code);

        to.thread.start();

        return to;
    }

    /**
     * Soft stop for running code
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * Hard (inmmediatelly) stop  fro running code
     */
    public void hardStop(){
        thread.stop();
    }
}
