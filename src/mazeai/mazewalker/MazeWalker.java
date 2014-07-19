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

        Graph graph = convertMazeToGraph(maze);



        return "";
    }
}
