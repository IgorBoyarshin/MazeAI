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

    // TODO: finish if necessary
    /*
        Check if link exists.
        If so, return it's path.
        If such link doesn't exist, create it,
    */
    public String linkExists(Vertex a, Vertex b) {
        /*
            Make optimization:
            Assuming that one Vertex is always gonna be Finish, cash it.
        */
        if (a.equals(b)) {
            return "";
        }

        int index = 0;
        Vertex i = vertices.get(index);
        while (!i.equals(a)) {
            index++;
            if (index >= vertices.size()) {
                return null;
            }
            i = vertices.get(index);
        }
        // Found Vertex A
        index = 0;
        i = a.getVertex(index);
        while (!i.equals(b)) {
            index++;
            if (index >= a.getVerticesAmount()) {
                return null;
            }
            i = a.getVertex(index);
        }

        return "";
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getFinish() {
        return finish;
    }

    public void setStartAndFinishVertex(Vertex start, Vertex finish) {
        if ((start != null) && (finish != null)) {
            this.start = start;
            this.finish = finish;
        }
    }
}
