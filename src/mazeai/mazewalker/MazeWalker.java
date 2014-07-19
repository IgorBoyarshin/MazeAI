package mazeai.mazewalker;

import mazeai.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created:  15.07.2014 20:33.
 */
public class MazeWalker {
    private Maze maze;

    // TODO: Remove setters for MazeWalker in Maze
    public MazeWalker(Maze maze) {
        this.maze = maze;
    }

    private MazeWalkerGraph convertMazeToGraph(Maze maze) {
        MazeWalkerGraph graph = new MazeWalkerGraph();

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

        MazeWalkerGraph graph = convertMazeToGraph(maze);

        return path;
    }
}
