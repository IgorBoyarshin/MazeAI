package mazeai.mazewalker;

import java.util.List;

/**
 * Created by Igor on 19.07.2014 at 10:12.
 */
public class Graph {
    private Vertex start;
    private Vertex finish;

    // all verts
    // each vert has some links containing paths
    // when I find new way, I add a new link with path
    private List<Vertex> vertices;

    public Graph() {

    }

    public void setStartAndFinishVertex(Vertex start, Vertex finish) {
        if ((start != null) && (finish != null)) {
            this.start = start;
            this.finish = finish;
        }
    }
}
