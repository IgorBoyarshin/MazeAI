package mazeai;

import mazeai.mazewalker.MazeWalker;

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
    }

    public MazeGame(final int width, final int height) {
        maze = new Maze(width, height);
        generateMaze(width, height);
    }

    public MazeGame(final int width, final int height, String pathToFile) {
        maze = new Maze(width, height);
        loadMazeFromFile(pathToFile);
    }

    private void loadMazeFromFile(String path) {
        String code;
        code = "1111111" +
                "111S111" +
                "1110111" +
                "1000001" +
                "1011101" +
                "1011101" +
                "1000001" +
                "1110111" +
                "111F111" +
                "1111111";
        if (maze != null) {

        }
    }

    public String getSolution() {
        mazeWalker = new MazeWalker(maze);
        String path = mazeWalker.generatePath();

        if (isPathValid(path)) {
            return path;
        } else {
            return "NO SOLUTION WAS FOUND";
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
            if ((i) >= path.length()) {
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

    private void generateMaze(int defaultMazeWidth, int defaultMazeHeight) {
        if (maze != null) {
            for (int i = 0; i < defaultMazeWidth; i++) {
                maze.setTileAt(i, 0, Tile.WALL);
                maze.setTileAt(i, defaultMazeHeight - 1, Tile.WALL);
            }

            for (int i = 0; i < defaultMazeHeight; i++) {
                maze.setTileAt(0, i, Tile.WALL);
                maze.setTileAt(defaultMazeWidth - 1, i, Tile.WALL);
            }

            for (int i = 1; i < defaultMazeWidth - 1; i++) {
                for (int j = 1; j < defaultMazeHeight - 1; j++) {
                    maze.setTileAt(i, j, Tile.SPACE);
                }
            }

            maze.setTileAt(2, 2, Tile.WALL);

            maze.setStart(1, 1);
            maze.setTileAt(1, 1, Tile.START);
            maze.setFinish(defaultMazeWidth - 2, defaultMazeHeight - 2);
            maze.setTileAt(defaultMazeWidth - 2, defaultMazeHeight - 2, Tile.FINISH);
        }
    }

    public void show() {
        maze.show();
    }

    public static void main(String[] args) {
        MazeGame mazeGame = new MazeGame();
        mazeGame.show();
//        System.out.println("Solution:");
//        System.out.println(mazeGame.getSolution());

//        if (mazeGame.isPathValid("DRRD")) {
//            System.out.println("YES");
//        } else {
//            System.out.println("NO");
//        }
    }
}
