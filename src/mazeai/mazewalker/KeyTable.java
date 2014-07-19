package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014 at 17:52.
 */
public class KeyTable {

    private List<Key> keys;

    public KeyTable() {
        keys = new ArrayList<Key>();
    }

    private Key getKey(Vertex a, Vertex b) {
        for (Key key : keys) {
            if (((key.getA().equals(a)) && (key.getB().equals(b)))
                    || ((key.getA().equals(b)) && (key.getB().equals(a)))) {

                return key;
            }
        }

        return null;
    }

    public void addKey(Vertex a, Vertex b, String value) {
        if (getKey(a, b) == null) {
            keys.add(new Key(a, b, value));
        }
    }

    public boolean keyExists(Vertex a, Vertex b) {
        return (getKey(a,b) != null);
    }

    public String getValueForKey(Vertex a, Vertex b) {
        Key key = getKey(a, b);
        if (key == null) {
            return null;
        } else {
            return key.getValue();
        }
    }
}
