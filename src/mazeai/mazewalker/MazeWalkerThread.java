package mazeai.mazewalker;

/**
 * Created:  16.07.2014 13:50.
 */
public class MazeWalkerThread extends Thread {

    private int parentPathsSum;
    private String path;

    public MazeWalkerThread(MazeWalkerMaze maze, int startX, int startY, int finishX, int finishY) {

    }

    public int getPathLength() {
        return path.length();
    }

    public int getParentPathsSumLength() {
        return parentPathsSum;
    }

    public String requestPath() {

    }

    @Override
    public void run() {



        /*
            while (finish not reached) {

            }
         */

        Situation situation = getCurrentSituation();
        switch (situation) {
            case STRAIGHT_AHEAD:

                break;
            case INTERSECTION:

                break;
            case DEAD_END:

                break;
            case ANOTHER_THREAD_HEAD:

                break;
            case ANOTHER_THREAD_TAIL:

                break;
        }
    }

    private Situation getCurrentSituation() {

    }
}
