package mazeai.mazewalker;

/**
 * Created by Igor on 26.07.2014 at 13:51.
 */
public class StartFinish {
    private Vertex start;
    private Vertex finish;

    public void setStart(Vertex vertex) {
        start = vertex;
    }

    public void setFinish(Vertex vertex) {
        finish = vertex;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getFinish() {
        return finish;
    }
}
