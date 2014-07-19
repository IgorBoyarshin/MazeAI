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

    private Graph convertMazeToGraph(Maze maze) {
        Graph graph = new Graph();

        // TODO
        // transformation code here

        return graph;
    }

    public String generatePath() {
        String path = "";

//        int startX = maze.getStartX();
//        int startY = maze.getStartY();
//        int finishX = maze.getFinishY();
//        int finishY = maze.getFinishY();

        Graph graph = convertMazeToGraph(maze);
        /*
        create new threads for each link from given vertex.
        for these links calculate new possible path to finish by adding new link's path.
        Wisely update graph's links based on results.
         */

        // Launching threads for children of Finish( first layer )
        for (int i = 0; i < graph.getFinish().getVerticesAmount(); i++) {
            MazeThread mainThread = new MazeThread(graph.getFinish(), graph.getFinish().getVertex(i));
        }

        return path;
    }

    /*
        terminate when found a link that exists in graph,
        but suggest a shorter path( if such exists ) before termination.

        join children
     */
}
