package mazeai;

/**
 * Created:  15.07.2014 19:28.
 */
public class Maze {
    private Tile[][] maze;

    private final int width;
    private final int height;

    private int startX;
    private int startY;
    private int finishX;
    private int finishY;

    public Maze(final int width, final int height) {
        this.width = width;
        this.height = height;
        maze = new Tile[width][height];
    }

    public void setTileAt(int x, int y, Tile tile) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height)) {
            maze[x][y] = tile;
        }
    }

    public Tile getTileAt(int x, int y) {
        if ((x >= 0) && (x < width) && (y >= 0) && (y < height)) {
            return maze[x][y];
        } else {
            return null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void show() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[j][i].equals(Tile.WALL)) {
                    System.out.print("1");
                } else if (maze[j][i].equals(Tile.SPACE)) {
                    System.out.print("0");
                } else if (maze[j][i].equals(Tile.START)) {
                    System.out.print("S");
                } else if (maze[j][i].equals(Tile.FINISH)) {
                    System.out.print("F");
                } else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void setStart(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public int getFinishY() {
        return finishY;
    }

    public void setFinish(int finishX, int finishY) {
        this.finishX = finishX;
        this.finishY = finishY;
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
