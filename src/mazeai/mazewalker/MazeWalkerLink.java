package mazeai.mazewalker;

/**
 * Created by Igor on 19.07.2014.
 */
public class MazeWalkerLink {
    private String path;
    private MazeWalkerVert vert;

    public MazeWalkerLink(MazeWalkerVert vert, String path) {
        this.path = path;
        this.vert = vert;
    }

    public String getPath() {
        return path;
    }

    public MazeWalkerVert getVert() {
        return vert;
    }
}
