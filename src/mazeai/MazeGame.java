package mazeai;

import libs.Measure;
import mazeai.mazewalker.MazeWalker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created:  15.07.2014 20:37.
 */
public class MazeGame {

    // TODO:
    /*
        Make PixelMap able to load in and display mazes with paths.
        Include support for PixelMap files for mazes.
     */

    private final int defaultMazeWidth = 5;
    private final int defaultMazeHeight = 5;

    private Maze maze;
    private MazeWalker mazeWalker;

    public MazeGame() {
        maze = new Maze(defaultMazeWidth, defaultMazeHeight);
        generateMaze(defaultMazeWidth, defaultMazeHeight);
//        loadMazeFromString();
    }

    public MazeGame(final int width, final int height) {
        maze = new Maze(width, height);
//        generateMaze(width, height);
        loadMazeFromFile("lab.lab");
    }

    public MazeGame(String path) {
        loadMazeFromFile(path);

        if (maze == null) {
            System.out.println("INVALID INPUT FILE");
        }
    }

    public MazeGame(final int width, final int height, String pathToFile) {
        maze = new Maze(width, height);
        loadMazeFromFile(pathToFile);
    }

    private void loadMazeFromString(String source, int width, int height) {
        if (source.length() != width * height) {
            return;
        }

        maze = new Maze(width, height);

        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                int index = y * maze.getWidth() + x;
                Tile tile;
                switch (source.charAt(index)) {
                    case '1':
                        tile = Tile.WALL;
                        break;
                    case '0':
                        tile = Tile.SPACE;
                        break;
                    case 'S':
                        tile = Tile.START;
                        maze.setStart(x, y);
                        break;
                    case 'F':
                        tile = Tile.FINISH;
                        maze.setFinish(x, y);
                        break;
                    default:
                        tile = Tile.SPACE;
                }

                maze.setTileAt(x, y, tile);
            }
        }
    }

    private void loadMazeFromFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            int width = Integer.parseInt(in.readLine());
            int height = Integer.parseInt(in.readLine());

            if (width < 2 || height < 2) {
                in.close();
                return;
            }

            maze = new Maze(width, height);

            for (int i = 0; i < height; i++) {
                String line = in.readLine();
                for (int j = 0; j < width; j++) {
                    char c = line.charAt(j);
                    Tile tile;

                    switch (c) {
                        case '1':
                            tile = Tile.WALL;
                            break;
                        case '0':
                            tile = Tile.SPACE;
                            break;
                        case 'S':
                            tile = Tile.START;
                            maze.setStart(j, i);
                            break;
                        case 'F':
                            tile = Tile.FINISH;
                            maze.setFinish(j, i);
                            break;
                        default:
                            tile = Tile.SPACE;
                    }

                    maze.setTileAt(j, i, tile);
                }
            }

            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSolution() {
        if (maze != null) {
            mazeWalker = new MazeWalker(maze);

            String path = mazeWalker.generatePath();

            if (isPathValid(path)) {
                return path;
            } else {
                return path + "_NO SOLUTION WAS FOUND";
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

            switch (path.charAt(i)) {
                case 'U':
                    if (maze.getTileAt(x, y - 1).equals(Tile.WALL)) {
                        return false;
                    } else {
                        y--;
                    }
                    break;
                case 'D':
                    if (maze.getTileAt(x, y + 1).equals(Tile.WALL)) {
                        return false;
                    } else {
                        y++;
                    }
                    break;
                case 'R':
                    if (maze.getTileAt(x + 1, y).equals(Tile.WALL)) {
                        return false;
                    } else {
                        x++;
                    }
                    break;
                case 'L':
                    if (maze.getTileAt(x - 1, y).equals(Tile.WALL)) {
                        return false;
                    } else {
                        x--;
                    }
                    break;
                default:
                    return false;
            }

            i++;
        }

        if (i != path.length()) {
            return false;
        }

        // I believe we have successfully exited from the loop, which
        // means that the path is valid... I guess

        return true;
    }

    private void generateMaze(int width, int height) {
        if (maze != null) {
            for (int i = 0; i < width; i++) {
                maze.setTileAt(i, 0, Tile.WALL);
                maze.setTileAt(i, height - 1, Tile.WALL);
            }

            for (int i = 0; i < height; i++) {
                maze.setTileAt(0, i, Tile.WALL);
                maze.setTileAt(width - 1, i, Tile.WALL);
            }

            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    maze.setTileAt(i, j, Tile.SPACE);
                }
            }

            maze.setTileAt(2, 2, Tile.WALL);
//            maze.setTileAt(2, 3, Tile.WALL);
//            maze.setTileAt(3, 2, Tile.WALL);
//            maze.setTileAt(3, 3, Tile.WALL);

            maze.setStart(1, 1);
            maze.setTileAt(1, 1, Tile.START);
            maze.setFinish(width - 2, height - 2);
            maze.setTileAt(width - 2, height - 2, Tile.FINISH);
        }
    }

    public void show() {
        if (maze != null) {
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
