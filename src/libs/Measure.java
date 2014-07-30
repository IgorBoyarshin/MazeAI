package libs;

/**
 * Created by Igor on 26.07.2014 at 18:13.
 */
public class Measure {
    private long startTime = -1;
    private long finishTime = -1;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void finish() {
        finishTime = System.currentTimeMillis();
    }

    public long getTime() {
        return finishTime - startTime;
    }
}
