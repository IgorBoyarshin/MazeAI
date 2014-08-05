package mazeai;

import libs.Measure;
import mazeai.mazewalker.EDirection;
import mazeai.mazewalker.MazeWalker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created:  15.07.2014 20:37.
 */
public class MazeGame {
    private final int defaultMazeWidth = 5;
    private final int defaultMazeHeight = 5;
    private final String defaultPathToMaze = "C:/Root/maze.maz";

    private Maze maze;

    public MazeGame() {
        if (!loadMazeFromFile(defaultPathToMaze)) {
            System.out.println("MAZE_GAME: INVALID PATH TO DEFAULT MAZE. GENERATING RANDOM MAZE");
            generateMaze(defaultMazeWidth, defaultMazeHeight);
        }
    }

    public MazeGame(int width, int height, String code) {
        maze = new Maze(width, height, code);
    }

    public MazeGame(int width, int height) {
        generateMaze(width, height);
    }

    public MazeGame(String pathToMaze) {
        if (!loadMazeFromFile(pathToMaze)) {
            System.out.println("MAZE_GAME: INVALID PATH TO MAZE.");
        }
    }

    private boolean loadMazeFromFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            int width = Integer.parseInt(in.readLine());
            int height = Integer.parseInt(in.readLine());

            if (width < Maze.getMinWidth() || height < Maze.getMinHeight() || width > Maze.getMaxWidth() || height > Maze.getMaxHeight()) {
                in.close();
                return false;
            }

            String code = "";

            for (int y = 0; y < height; y++) {
                String line = in.readLine();
                for (int x = 0; x < width; x++) {
                    char symbol = line.charAt(x);

                    code += symbol;
                    if (!(symbol == ETile.FINISH.getSymbol() || symbol == ETile.SPACE.getSymbol()
                            || symbol == ETile.START.getSymbol() || symbol == ETile.WALL.getSymbol())) {
                        return false;
                    }
                }
            }

            maze = new Maze(width, height, code);

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getSolution() {
        if (maze != null) {
            MazeWalker mazeWalker = new MazeWalker(maze);

            String path = mazeWalker.generatePath();

            if (isPathValid(path)) {
                return path;
            } else {
//                return path + "_NO SOLUTION WAS FOUND";
                return "NO SOLUTION WAS FOUND";
            }
        } else {
            return null;
        }
    }

    public boolean isPathValid(String path) {
        if (maze == null) {
            return false;
        }

        int x = maze.getStartX();
        int y = maze.getStartY();

        int i = 0;
        while (!((x == maze.getFinishX()) && (y == maze.getFinishY()))) {
            if (i >= path.length()) {
                return false;
            }

            char c = path.charAt(i);
            if (c == EDirection.UP.getSymbol()) {
                if (maze.getTileAt(x, y - 1).equals(ETile.WALL)) {
                    return false;
                } else {
                    y--;
                }
            } else if (c == EDirection.DOWN.getSymbol()) {
                if (maze.getTileAt(x, y + 1).equals(ETile.WALL)) {
                    return false;
                } else {
                    y++;
                }
            } else if (c == EDirection.RIGHT.getSymbol()) {
                if (maze.getTileAt(x + 1, y).equals(ETile.WALL)) {
                    return false;
                } else {
                    x++;
                }
            } else if (c == EDirection.LEFT.getSymbol()) {
                if (maze.getTileAt(x - 1, y).equals(ETile.WALL)) {
                    return false;
                } else {
                    x--;
                }
            } else {
                return false;
            }

            i++;
        }

        if (i != path.length()) {
            return false;
        }

        return true;
    }

    private void generateMaze(int width, int height) {
        String code = "";

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    code += ETile.WALL.getSymbol();
                } else if (y == 1 && x == 1) {
                    code += ETile.START.getSymbol();
                } else if (y == height - 2 && (x == width - 2)) {
                    code += ETile.FINISH.getSymbol();
                } else {
                    code += ETile.SPACE.getSymbol();
                }
            }
        }

        maze = new Maze(width, height, code);
    }

    public void show() {
        if (maze != null) {
            System.out.println("Width:" + maze.getWidth() + "; Height:" + maze.getHeight());
            maze.show();
        }
    }

    public static void main(String[] args) {
        MazeGame mazeGame = new MazeGame("C:/Root/maze.maz");
        mazeGame.show();
        Measure measure = new Measure();

        System.out.println("Solution:");

        measure.start();
        String solution = mazeGame.getSolution();
        System.out.println(solution);
        measure.finish();

        System.out.println("Elapsed time:" + measure.getTime());
    }
}
