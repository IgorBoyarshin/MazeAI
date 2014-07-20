package mazeai.mazewalker;

import mazeai.Maze;
import mazeai.Tile;

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

    private String possibleTurns(int x, int y) {
//        if ((x <= 0) || (x >= maze.getWidth() - 1) || (y <= 0) || (y >= maze.getHeight() - 1)) {
//            return false;
//        }

        String turns = "";

        if (x > 0) {
            if (!maze.getTileAt(x - 1, y).equals(Tile.WALL)) {
                turns += 'L';
            }
        }
        if (x < maze.getWidth() - 1) {
            if (!maze.getTileAt(x + 1, y).equals(Tile.WALL)) {
                turns += 'R';
            }
        }
        if (y > 0) {
            if (!maze.getTileAt(x, y - 1).equals(Tile.WALL)) {
                turns += 'U';
            }
        }
        if (y > maze.getHeight() - 1) {
            if (!maze.getTileAt(x, y + 1).equals(Tile.WALL)) {
                turns += 'D';
            }
        }

        return turns;
    }

    private void prepareForSolving(KeyTable table, Graph graph) {

        KeyTable<Integer, Vertex> vertices = new KeyTable<>();

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Tile tile = maze.getTileAt(x, y);

                // Checked current column for future vertices
                if (tile.equals(Tile.START) || tile.equals(Tile.FINISH)) {
                    vertices.addKey(x, y, new Vertex());
                } else if (tile.equals(Tile.SPACE)) {
                    if (possibleTurns(x, y).length() > 2) {
                        vertices.addKey(x, y, new Vertex());
                    }
                }
            }
        }

        // Now -vertices- contains all future vertices



//        Vertex a = new Vertex();
//        Vertex b = new Vertex();
//        Vertex c = new Vertex();
//        Vertex d = new Vertex();
//
//        a.addChild(b);
//        a.addChild(c);
//        a.addChild(d);
//
//        b.addChild(a);
//        b.addChild(c);
//
//        c.addChild(b);
//        c.addChild(a);
//        c.addChild(d);
//
//        d.addChild(a);
//        d.addChild(c);
//
//        graph.setStartAndFinishVertex(d, a);
//
//        table.addKey(a, b, "D");
//        table.addKey(b, c, "D");
//        table.addKey(c, d, "R");
//        table.addKey(a, c, "LDDR");
//        table.addKey(a, d, "RRDDL");
    }

    private String invert(String path) {
        String invertedPath = "";

        for (int i = 0; i < path.length(); i++) {
            switch (path.charAt(i)) {
                case 'U':
                    invertedPath += "D";
                    break;
                case 'D':
                    invertedPath += "U";
                    break;
                case 'L':
                    invertedPath += "R";
                    break;
                case 'R':
                    invertedPath += "L";
                    break;
                default:
                    return null;
            }
        }
        return invertedPath;
    }


    // TODO: no more invert! Implement again!!!
    // TODO: not working anymore!
    public String generatePath() {

        return "";
        /*


        // Out input for solving
        Graph graph = new Graph();
        KeyTable table = new KeyTable();

        // Set starting values for input
        prepareForSolving(table, graph);

        // Working space for solving
        List<Vertex> layer;
        List<Vertex> nextLayer = new ArrayList<Vertex>();

        // Starting preparations
        table.addKey(graph.getFinish(), graph.getFinish(), "");
        nextLayer.add(graph.getFinish());

        while (nextLayer.size() > 0) {
            layer = nextLayer;
            nextLayer = new ArrayList<Vertex>();

            for (int i = 0; i < layer.size(); i++) {

                boolean reprocess = true;
                // TODO: think of a better way!
                while (reprocess) {
                    reprocess = false;
                    // take its children, process them
                    for (int j = 0; j < layer.get(i).getChildrenAmount(); j++) {
                        Vertex thisVertex = layer.get(i).getChild(j);
                        Vertex parentVertex = layer.get(i);
                        Vertex finishVertex = graph.getFinish();

                        // check table for that key
                        String calculatedPath = table.getValueForKey(thisVertex, parentVertex)
                                + table.getValueForKey(parentVertex, finishVertex);

                        if (table.keyExists(thisVertex, finishVertex)) {
                            if (table.getValueForKey(thisVertex, finishVertex).length() > calculatedPath.length()) {
                                reprocess = true;
                                table.setNewValueForKey(thisVertex, finishVertex, calculatedPath);
                            }
                            // TERMINATE
                        } else {
                            table.addKey(thisVertex, finishVertex, calculatedPath);

                            // CONTINUE
                            nextLayer.add(thisVertex);
                        }
                    }
                }
            }
        }

        return table.getValueForKey(graph.getStart(), graph.getFinish());

        */
    }
}
