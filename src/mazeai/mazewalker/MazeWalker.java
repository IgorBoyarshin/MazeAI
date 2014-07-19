package mazeai.mazewalker;

import mazeai.Maze;

/**
 * Created:  15.07.2014 20:33.
 */
public class MazeWalker {
    private Maze maze;

    // TODO: Remove setters for MazeWalker in Maze
    public MazeWalker(Maze maze) {
        this.maze = maze;
    }

    // TODO: Make paths in both ways in links. Check existing implementations to support this ideology.
    private Graph convertMazeToGraph(Maze maze) {
        Graph graph = new Graph();

        // TODO
        // transformation code here

        /*
            Find all intersections.

        */

        return graph;
    }

    public String generatePath() {
        String path;

//        int startX = maze.getStartX();
//        int startY = maze.getStartY();
//        int finishX = maze.getFinishY();
//        int finishY = maze.getFinishY();

        Graph graph = convertMazeToGraph(maze);

        // Launching threads for children of Finish( first layer )
        graph.getFinish().setThread(null);
        for (int i = 0; i < graph.getFinish().getVerticesAmount(); i++) {
            MazeThread mainThread = new MazeThread(graph.getFinish(), graph.getFinish().getVertex(i));
            mainThread.start();

            // We have to wait for all these Threads to finish. Thus we are sure, that all their children have finished.
            // So all threads have finished, String S-F contains shortest path
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        path = graph.getPathFromStartToFinish();

        return path;
    }
}
