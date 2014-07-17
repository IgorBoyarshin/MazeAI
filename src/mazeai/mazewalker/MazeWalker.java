package mazeai.mazewalker;

import mazeai.Maze;

/**
 * Created:  15.07.2014 20:33.
 */
public class MazeWalker {
    private Maze maze;
    private MazeWalkerThread mainThread;

    // TODO: Remove setters for MazeWalker in Maze
    public MazeWalker(Maze maze) {
        this.maze = maze;
    }

    public String generatePath() {
        String path = "";

        int startX = maze.getStartX();
        int startY = maze.getStartY();
        int finishX = maze.getFinishY();
        int finishY = maze.getFinishY();
        mainThread = new MazeWalkerThread(startX, startY, finishX, finishY);
        mainThread.run();
        try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        path = mainThread.requestPath();

        return path;
    }
}
