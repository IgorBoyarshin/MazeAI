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
        if (y < maze.getHeight() - 1) {
            if (!maze.getTileAt(x, y + 1).equals(Tile.WALL)) {
                turns += 'D';
            }
        }

        return turns;
    }

    // TODO: check deadlock
    private Key<Vertex, String> goThereTillNextIntersection
    (Key<Integer, Vertex> startingPoint, char direction, KeyTable<Integer, Vertex> vertices) {
        int x = startingPoint.getA();
        int y = startingPoint.getB();
//        Key<Integer, Vertex> current = startingPoint;

        switch (direction) {
            case 'U':
                y--;
                break;
            case 'D':
                y++;
                break;
            case 'R':
                x++;
                break;
            case 'L':
                x--;
                break;
            default:
                System.out.println("goThereTillNextIntersection: WRONG DIRECTION");
                return null;
        }

        String turns = possibleTurns(x, y);
        String path = "" + direction;

        if ((turns.length() == 1)
                && (!maze.getTileAt(x, y).equals(Tile.START))
                && (!maze.getTileAt(x, y).equals(Tile.FINISH))) {
            // Deadlock
            System.out.println("DEADLOCK");
            return null;
        }

        while ((turns.length() < 3)
                && (!maze.getTileAt(x, y).equals(Tile.START))
                && (!maze.getTileAt(x, y).equals(Tile.FINISH))) {

            // We know that there are 1 or 2 turns possible
            // We make sure that we don't go back
            char newDirection = turns.charAt(0);
//            System.out.println("Length_out: " + turns.length());
            if (newDirection == invert(direction + "").charAt(0)) {
//                System.out.println("Length_in: " + turns.length());
                newDirection = turns.charAt(1);
            }

            path += newDirection;

            switch (newDirection) {
                case 'U':
                    y--;
                    break;
                case 'D':
                    y++;
                    break;
                case 'R':
                    x++;
                    break;
                case 'L':
                    x--;
                    break;
                default:
                    System.out.println("goThereTillNextIntersection: WRONG DIRECTION");
                    return null;
            }

            direction = newDirection;

            turns = possibleTurns(x, y);

            if ((turns.length() == 1)
                    && (!maze.getTileAt(x, y).equals(Tile.START))
                    && (!maze.getTileAt(x, y).equals(Tile.FINISH))) {
                // Deadlock
                System.out.println("DEADLOCK");
                return null;
            }
        }

//        System.out.println("PATH: " + path);
//        if ((maze.getTileAt(x, y).equals(Tile.START))
//                || (maze.getTileAt(x, y).equals(Tile.FINISH))) {
//            System.out.println("S or F");
//        }

        Vertex finishingPoint = vertices.getKey(x, y).getValue();

        return new Key<Vertex, String>(startingPoint.getValue(), finishingPoint, path);
    }

    private void prepareForSolving(KeyTable<Vertex, String> table, Graph graph) {

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
//        System.out.println("TEST: vertices amount:" + vertices.size());

        for (int i = 0; i < vertices.size(); i++) {
            Key<Integer, Vertex> currentVertex = vertices.getKey(i);
            int x = currentVertex.getA();
            int y = currentVertex.getB();
            String turns = possibleTurns(x, y);
//            System.out.println("Poss turns amount for x,y: " + x + ":" + y + "=" + turns.length());
//            System.out.println("Poss turns: " + turns);

            for (int t = 0; t < turns.length(); t++) {
                Key<Vertex, String> ans = goThereTillNextIntersection(currentVertex, turns.charAt(t), vertices);
                // -newVertex- contains:
                // a: starting vertex
                // b: finishing vertex
                // value: path from starting to finishing vertex
                if (ans == null) {
                    // Deadlock
                    System.out.println("Found deadlock");
                    continue;
                } else {
//                    System.out.println("Gonna fill table now");

                    // Such way I properly handle when there are two or more paths to the same vertex from given
                    if (table.keyExists(ans.getA(), ans.getB())) {
                        if (ans.getValue().length() < table.getValueForKey(ans.getA(), ans.getB()).length()) {
                            table.setNewValueForKey(ans.getA(), ans.getB(), ans.getValue());
                            table.setNewValueForKey(ans.getB(), ans.getA(), invert(ans.getValue()));
                        }
                    } else {
                        table.addKey(ans);
                        table.addKey(ans.getB(), ans.getA(), invert(ans.getValue()));
                    }

                    // Case when such Key already exists is handled in KeyTable class, so I don't bother about it
//                    table.addKey(ans);
//                    table.addKey(ans.getB(), ans.getA(), invert(ans.getValue()));

                    Vertex v1 = ans.getA();
                    Vertex v2 = ans.getB();

                    // Case for existing children is handled in Vertex class
                    v1.addChild(v2);
                    v2.addChild(v1);
                }
            }
        }

        int startX = maze.getStartX();
        int startY = maze.getStartY();
        int finishX = maze.getFinishX();
        int finishY = maze.getFinishY();
        Vertex startVertex = vertices.getKey(startX, startY).getValue();
        Vertex finishVertex = vertices.getKey(finishX, finishY).getValue();

        graph.setStartAndFinishVertex(startVertex, finishVertex);
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


    // TODO: OPTIMIZATION: For now I added paths into table in both ways. Make it the way so that I add once, but
    // TODO: OPTIMIZATION: it checks for existing, inverts where necessary
    public String generatePath() {
        // Our input for solving
        Graph graph = new Graph();
        KeyTable<Vertex, String> table = new KeyTable<Vertex, String>();

        // Set starting values for input
        prepareForSolving(table, graph);

//        for (int r = 0; r < table.size(); r++) {
//            System.out.println("TEST: table: " + table.getValueForKey(table.getKey(r).getA(), table.getKey(r).getB()));
//        }

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

                        // I check also for parent==finish because otherwise everything(given only one child for
                        // finish) would terminate
                        if ((table.keyExists(thisVertex, finishVertex)) && (!parentVertex.equals(finishVertex))) {
                            if (table.getValueForKey(thisVertex, finishVertex).length() > calculatedPath.length()) {
                                reprocess = true;
                                // I set in both ways, so that upper existing check is always valid
                                table.setNewValueForKey(thisVertex, finishVertex, calculatedPath);
                                table.setNewValueForKey(finishVertex, thisVertex, calculatedPath);
                            }
                            // TERMINATE
                        } else {
                            // I add in both ways, so that upper existing check is always valid
                            table.addKey(thisVertex, finishVertex, calculatedPath);
                            table.addKey(finishVertex, thisVertex, calculatedPath);

                            // CONTINUE
                            nextLayer.add(thisVertex);
                        }
                    }
                }
            }
        }

        String p = table.getValueForKey(graph.getStart(), graph.getFinish());
        if (p == null) {
            System.out.println("I HAVE null");
        }

        return p;
    }
}
