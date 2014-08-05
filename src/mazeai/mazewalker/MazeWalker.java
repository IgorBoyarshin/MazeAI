package mazeai.mazewalker;

import mazeai.ETile;
import mazeai.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created:  15.07.2014 20:33.
 */
public class MazeWalker {
    private Maze maze;

    public MazeWalker(Maze maze) {
        this.maze = maze;
    }

    // TODO: OPTIMIZATION: For now I added paths into table in both ways. Make it the way so that I add once, but
    // TODO: OPTIMIZATION: it checks for existing, inverts where necessary
    public String generatePath() {
        // Our input for solving
        StartFinish startFinish = new StartFinish();
        KeyTable<Vertex, String> table = new KeyTable<Vertex, String>();

        prepareForSolving(table, startFinish);

        Vertex start = startFinish.getStart();
        Vertex finish = startFinish.getFinish();

//        for (int r = 0; r < table.size(); r++) {
//            System.out.println("TEST: table: " + table.getValueForKey(table.getKey(r).getA(), table.getKey(r).getB()));
//        }

        // Working space for solving
        List<Vertex> layer;
        List<Vertex> nextLayer = new ArrayList<Vertex>();

        // Starting preparations
        table.addKey(finish, finish, "");
        table.addKey(start, start, "");
        nextLayer.add(finish);

        while (nextLayer.size() > 0) {
            layer = nextLayer;
            nextLayer = new ArrayList<Vertex>();

            for (int i = 0; i < layer.size(); i++) {
                // take its children, process them
                for (int j = 0; j < layer.get(i).getChildrenAmount(); j++) {
                    Vertex thisVertex = layer.get(i).getChild(j);
                    Vertex parentVertex = layer.get(i);

                    // check table for that key
//                    String calculatedPath = table.getValueForKey(thisVertex, parentVertex)
//                            + table.getValueForKey(parentVertex, finish);
                    String calculatedPath = getValueForThisOrOppositeKey(table, thisVertex, parentVertex) +
                            getValueForThisOrOppositeKey(table, parentVertex, finish);

                    // I check also for parent==finish because otherwise everything(given only one child for
                    // finish) would terminate
                    if (table.keyExists(thisVertex, finish) || table.keyExists(finish, thisVertex)) {
                        if (getValueForThisOrOppositeKey(table, thisVertex, finish).length() > calculatedPath.length()) {
                            setNewValueForKeyOrOpposite(table, thisVertex, finish, calculatedPath);
                        }

                        if (parentVertex.equals(finish)) {
                            // CONTINUE
                            nextLayer.add(thisVertex);
                        }

                        // TERMINATE

                    } else {
                        table.addKey(thisVertex, finish, calculatedPath);
//                        table.addKey(finish, thisVertex, invert(calculatedPath));

                        if (!thisVertex.equals(start)) {
                            // CONTINUE
                            nextLayer.add(thisVertex);
                        }

                        // If this is Start then TERMINATE
                    }
                }
            }
        }

//        System.out.println("Table size:" + table.size());

//        String p = table.getValueForKey(start, finish);
        String p = getValueForThisOrOppositeKey(table, start, finish);
        if (p == null) {
            System.out.println("Start -> Finish == null");
        }

        return p;
    }

    private String getValueForThisOrOppositeKey(KeyTable<Vertex, String> table, Vertex a, Vertex b) {
        if (table.keyExists(a,b)) {
            return table.getValueForKey(a,b);
        } else if (table.keyExists(b,a)) {
            return invert(table.getValueForKey(b,a));
        } else {
            return null;
        }
    }

    private void setNewValueForKeyOrOpposite(KeyTable<Vertex, String> table, Vertex a, Vertex b, String value) {
        if (table.keyExists(a,b)) {
            table.setNewValueForKey(a,b,value);
        } else if (table.keyExists(b,a)) {
            table.setNewValueForKey(b,a, invert(value));
        }
    }

    private void prepareForSolving(KeyTable<Vertex, String> table, StartFinish startFinish) {

        KeyTable<Integer, Vertex> vertices = new KeyTable<>();

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                ETile ETile = maze.getTileAt(x, y);

                if (ETile.equals(ETile.START) || ETile.equals(ETile.FINISH)) {
                    vertices.addKey(x, y, new Vertex());
                } else if (ETile.equals(ETile.SPACE)) {
                    if (possibleTurns(x, y).length() > 2) {
                        vertices.addKey(x, y, new Vertex());
                    }
                }
            }
        }

        /*
        // TEST
        System.out.println("TEST: Prepared graph:");
        for (int q = 0; q < vertices.size(); q++) {
            System.out.println("(" + vertices.getKey(q).getA() + ";" + vertices.getKey(q).getB() + ")");
        }
        System.out.println("----");
        // TEST
        */

        // Now -vertices- contains all future vertices

        for (int i = 0; i < vertices.size(); i++) {
            Key<Integer, Vertex> currentVertex = vertices.getKey(i);
            int x = currentVertex.getA();
            int y = currentVertex.getB();
            String turns = possibleTurns(x, y);

            /*
            // TEST
            System.out.println("TEST: Checking maze for (" + x + ";" + y + "):");
            // TEST
            */

            for (int t = 0; t < turns.length(); t++) {
                Key<Vertex, String> ans = goUntilVertexOrDeadlock(currentVertex, turns.charAt(t), vertices);
                // -ans- contains:
                // a: starting vertex
                // b: finishing vertex
                // value: path from starting to finishing vertex
                // -ans- == null if deadlock

                /*
                // TEST
                System.out.print("TEST: For direction " + turns.charAt(t) + " :");
                // TEST
                */

                if (ans == null) {
                    /*
                    // TEST
                    System.out.println("That was a deadlock");
                    // TEST
                    */

                    // Deadlock
//                    System.out.println("Found deadlock");
                } else {
                    /*
                    // TEST
                    System.out.println(" path " + ans.getValue());
                    // TEST
                    */

                    // Such way I properly handle when there are two or more paths to the same vertex from given
                    if (table.keyExists(ans.getA(), ans.getB())) {
                        /*
                        // TEST
                        System.out.println("TEST: such key exists");
                        // TEST
                        */
                        if (ans.getValue().length() < table.getValueForKey(ans.getA(), ans.getB()).length()) {
                            /*
                            // TEST
                            System.out.println("TEST: setting new: " + ans.getValue() + " and " + invert(ans.getValue()));
                            // TEST
                            */

                            table.setNewValueForKey(ans.getA(), ans.getB(), ans.getValue());
//                            table.setNewValueForKey(ans.getB(), ans.getA(), invert(ans.getValue()));
                        }
                    } else if (table.keyExists(ans.getB(), ans.getA())) {
                        if (ans.getValue().length() < table.getValueForKey(ans.getB(), ans.getA()).length()) {
                            table.setNewValueForKey(ans.getB(), ans.getA(), invert(ans.getValue()));
                        }
                    } else {
                        /*
                        // TEST
                        System.out.println("TEST: such key doesn't. Adding " + ans.getValue() + " and " + invert(ans.getValue()));
                        // TEST
                        */

                        table.addKey(ans);
//                        table.addKey(ans.getB(), ans.getA(), invert(ans.getValue()));
                    }

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
        startFinish.setStart(vertices.getKey(startX, startY).getValue());
        startFinish.setFinish(vertices.getKey(finishX, finishY).getValue());
    }

    private Key<Vertex, String> goUntilVertexOrDeadlock
            (Key<Integer, Vertex> startingPoint, char direction, KeyTable<Integer, Vertex> vertices) {
        int x = startingPoint.getA();
        int y = startingPoint.getB();

        if (direction == EDirection.UP.getSymbol()) {
            y--;
        } else if (direction == EDirection.DOWN.getSymbol()) {
            y++;
        } else if (direction == EDirection.RIGHT.getSymbol()) {
            x++;
        } else if (direction == EDirection.LEFT.getSymbol()) {
            x--;
        } else {
            System.out.println("goUntilVertexOrDeadlock: WRONG DIRECTION");
            return null;
        }

        String turns = possibleTurns(x, y);
        String path = "" + direction;

        if ((turns.length() == 1)
                && (!maze.getTileAt(x, y).equals(ETile.START))
                && (!maze.getTileAt(x, y).equals(ETile.FINISH))) {
            // Deadlock
//            System.out.println("DEADLOCK_OUT");
            return null;
        }

        while ((turns.length() < 3)
                && (!maze.getTileAt(x, y).equals(ETile.START))
                && (!maze.getTileAt(x, y).equals(ETile.FINISH))) {

            // We know that there are 1 or 2 turns possible
            // We make sure that we don't go back
            char newDirection = turns.charAt(0);
//            System.out.println("Length_out: " + turns.length());
            if (newDirection == invert(direction + "").charAt(0)) {
//                System.out.println("Length_in: " + turns.length());
                newDirection = turns.charAt(1);
            }

            path += newDirection;

            if (newDirection == EDirection.UP.getSymbol()) {
                y--;
            } else if (newDirection == EDirection.DOWN.getSymbol()) {
                y++;
            } else if (newDirection == EDirection.RIGHT.getSymbol()) {
                x++;
            } else if (newDirection == EDirection.LEFT.getSymbol()) {
                x--;
            } else {
                System.out.println("goUntilVertexOrDeadlock: WRONG DIRECTION");
                return null;
            }

            direction = newDirection;

            turns = possibleTurns(x, y);

            if ((turns.length() == 1)
                    && (!maze.getTileAt(x, y).equals(ETile.START))
                    && (!maze.getTileAt(x, y).equals(ETile.FINISH))) {
                // Deadlock
//                System.out.println("DEADLOCK_IN");
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

    private String possibleTurns(int x, int y) {
        String turns = "";

        if (x > 0) {
            if (!maze.getTileAt(x - 1, y).equals(ETile.WALL)) {
                turns += EDirection.LEFT.getSymbol();
            }
        }
        if (x < maze.getWidth() - 1) {
            if (!maze.getTileAt(x + 1, y).equals(ETile.WALL)) {
                turns += EDirection.RIGHT.getSymbol();
            }
        }
        if (y > 0) {
            if (!maze.getTileAt(x, y - 1).equals(ETile.WALL)) {
                turns += EDirection.UP.getSymbol();
            }
        }
        if (y < maze.getHeight() - 1) {
            if (!maze.getTileAt(x, y + 1).equals(ETile.WALL)) {
                turns += EDirection.DOWN.getSymbol();
            }
        }

        return turns;
    }

    private String invert(String path) {
        String invertedPath = "";

        for (int i = path.length() - 1; i >= 0; i--) {
            char symbol = path.charAt(i);

            if (symbol == EDirection.UP.getSymbol()) {
                invertedPath += EDirection.DOWN.getSymbol();
            } else if (symbol == EDirection.DOWN.getSymbol()) {
                invertedPath += EDirection.UP.getSymbol();
            } else if (symbol == EDirection.RIGHT.getSymbol()) {
                invertedPath += EDirection.LEFT.getSymbol();
            } else if (symbol == EDirection.LEFT.getSymbol()) {
                invertedPath += EDirection.RIGHT.getSymbol();
            } else {
                return null;
            }
        }

        return invertedPath;
    }
}
