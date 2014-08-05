package mazeai;

/**
 * Created:  15.07.2014 19:28.
 */
public class Maze {
    private ETile[][] maze;

    private final int width;
    private final int height;

    private static final int minWidth = 4;
    private static final int minHeight = 4;
    private static final int maxWidth = 40;
    private static final int maxHeight = 40;

    private final boolean mazeFilled;

    private int startX = -1;
    private int startY = -1;
    private int finishX = -1;
    private int finishY = -1;

    public Maze(final int width, final int height, String code) {
        if (!inputValid(width, height, code)) {
            System.out.println("--MAZE: INVALID INITIALIZATION INPUT");
            mazeFilled = false;
            this.width = -1;
            this.height = -1;
        } else {
            this.width = width;
            this.height = height;
            maze = new ETile[width][height];

            if (fillMazeUsingCode(code)) {
                mazeFilled = true;
            } else {
                mazeFilled = false;
                System.out.println("--MAZE: INVALID INITIALIZATION INPUT");
            }
        }
    }

    private boolean inputValid(final int width, final int height, String code) {
        if (width < minWidth || height < minHeight
                || width > maxWidth || height > maxHeight
                || code.length() < minWidth * minHeight || code.length() > maxWidth * maxHeight) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Fills the -maze- field using the -code- string.
     * Returns TRUE if filled successfully. Otherwise returns FALSE.
     */
    private boolean fillMazeUsingCode(String code) {
        if (maze.equals(null)) {
            return false;
        }
        if (code.length() != width * height) {
            return false;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char symbol = code.charAt(y * width + x);
                ETile tile;

                if (symbol == ETile.FINISH.getSymbol()) {
                    tile = ETile.FINISH;
                    if (finishX != -1 || finishY != -1) {
                        return false;
                    } else {
                        finishX = x;
                        finishY = y;
                    }
                } else if (symbol == ETile.START.getSymbol()) {
                    tile = ETile.START;
                    if (startX != -1 || startY != -1) {
                        return false;
                    } else {
                        startX = x;
                        startY = y;
                    }
                } else if (symbol == ETile.SPACE.getSymbol()) {
                    tile = ETile.SPACE;
                } else if (symbol == ETile.WALL.getSymbol()) {
                    tile = ETile.WALL;
                } else {
                    return false;
                }

                setTileAt(x, y, tile);
            }
        }

        return true;
    }

    private void setTileAt(int x, int y, ETile ETile) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height) && (!ETile.equals(null))) {
            maze[x][y] = ETile;
        }
    }

    public ETile getTileAt(int x, int y) {
        if (mazeFilled) {
            if ((x >= 0) && (x < width) && (y >= 0) && (y < height)) {
                return maze[x][y];
            } else {
                return null;
            }
        } else {
            System.out.println("MAZE: UNABLE TO PERFORM THE -getTileAt(int, int)- METHOD BECAUSE MAZE IS NOT INITIALIZED");
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int getMinWidth() {
        return minWidth;
    }

    public static int getMinHeight() {
        return minHeight;
    }

    public static int getMaxWidth() {
        return maxWidth;
    }

    public static int getMaxHeight() {
        return maxHeight;
    }

    public void show() {
        if (mazeFilled) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (maze[j][i].equals(ETile.WALL)) {
                        System.out.print(ETile.WALL.getSymbol());
                    } else if (maze[j][i].equals(ETile.SPACE)) {
                        System.out.print(ETile.SPACE.getSymbol());
                    } else if (maze[j][i].equals(ETile.START)) {
                        System.out.print(ETile.START.getSymbol());
                    } else if (maze[j][i].equals(ETile.FINISH)) {
                        System.out.print(ETile.FINISH.getSymbol());
                    } else {
                        System.out.print("-");
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
        } else {
            System.out.println("MAZE: UNABLE TO PERFORM THE -show()- METHOD BECAUSE MAZE IS NOT INITIALIZED");
        }
    }

    public int getFinishY() {
        return finishY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getFinishX() {
        return finishX;
    }
}
