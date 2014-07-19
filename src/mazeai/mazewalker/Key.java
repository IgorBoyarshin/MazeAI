package mazeai.mazewalker;

/**
 * Created by Igor on 19.07.2014 at 17:53.
 */
public class Key {
    private Vertex a;
    private Vertex b;

    private String value;

    public Key(Vertex a, Vertex b, String value) {
        this.a = a;
        this.b = b;
        this.value = value;
    }

    public Vertex getA() {
        return a;
    }

    public Vertex getB() {
        return b;
    }

    public String getValue() {
        return value;
    }
}
