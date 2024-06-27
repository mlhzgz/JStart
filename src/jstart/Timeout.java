package jstart;

/**
 * Timeout class emulating JavaScript funciton setTimeout()
 */
public class Timeout {
    private Thread thread;

    private Timeout(final long ms, final boolean asInterval, final Runnable code) {
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
     * 
     * @param ms   milliseconds before code run
     * @param code lambda to run
     * @return Timeout object
     */
    public static Timeout run(final long ms, final Runnable code) {
        final Timeout to = new Timeout(ms, false, code);

        to.thread.start();

        return to;
    }

    /**
     * Runs code in background repeteadly after a number of milliseconds (like JS
     * setInterval())
     * 
     * @param ms   milliseconds before code run
     * @param code lambda to run
     * @return Timeout object
     */
    public static Timeout runAsInterval(final long ms, final Runnable code) {
        final Timeout to = new Timeout(ms, true, code);

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
     * Hard (inmmediatelly) stop fro running code
     */
    @SuppressWarnings("deprecation")
    public void hardStop() {
        thread.stop();
    }
}
