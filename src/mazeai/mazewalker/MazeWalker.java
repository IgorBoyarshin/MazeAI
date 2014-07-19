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

    private void fillTable(KeyTable table, Graph graph) {
        Vertex a = new Vertex();
        Vertex b = new Vertex();
        Vertex c = new Vertex();
        Vertex d = new Vertex();

        a.addChild(b);
        a.addChild(c);
        a.addChild(d);

        b.addChild(a);
        b.addChild(c);

        c.addChild(b);
        c.addChild(a);
        c.addChild(d);

        d.addChild(a);
        d.addChild(c);

        graph.setStartAndFinishVertex(d, a);

        table.addKey(a, b, "D");
        table.addKey(b, c, "D");
        table.addKey(c, d, "R");
        table.addKey(a, c, "LDDR");
        table.addKey(a, d, "RRDDL");
    }

    public String generatePath() {

        Graph graph = convertMazeToGraph(maze);

        List<Vertex> layer;
        List<Vertex> nextLayer = new ArrayList<Vertex>();

        // TODO: Fill table with starting values!!!
        KeyTable table = new KeyTable();
        fillTable(table, graph);
        table.addKey(graph.getFinish(), graph.getFinish(), "");

        nextLayer.add(graph.getFinish());

        while (nextLayer.size() > 0) {
            layer = nextLayer;
            nextLayer = new ArrayList<Vertex>();

            for (int i = 0; i < layer.size(); i++) {

                boolean reprocess = true;
                // TODO: think of a better way!
                // TODO: Not sure, just trying...
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
    }
}
