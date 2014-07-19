package mazeai.mazewalker;

/**
 * Created by Igor on 19.07.2014.
 */
public class Link {
    private String path;
    private Vertex vertex;

    public Link(Vertex vertex, String path) {
        this.path = path;
        this.vertex = vertex;
    }

    public String getPath() {
        return path;
    }

    public Vertex getVertex() {
        return vertex;
    }
}
