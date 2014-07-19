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

    private String invert(String path) {
        String invertedPath = "";

        for (int i = 0; i < path.length(); i++) {
            switch (path.charAt(i)) {
                case 'U':
                    invertedPath += "D";
                    break;
                case 'D':
                    invertedPath += "U";
                    break;
                case 'L':
                    invertedPath += "R";
                    break;
                case 'R':
                    invertedPath += "L";
                    break;
                default:
                    return null;
            }
        }
        return invertedPath;
    }

    public void addKey(Vertex a, Vertex b, String value) {
        if (getKey(a, b) == null) {
            keys.add(new Key(a, b, value));
        }
    }

    public boolean keyExists(Vertex a, Vertex b) {
        return (getKey(a, b) != null);
    }

    public String getValueForKey(Vertex a, Vertex b) {
        Key key = getKey(a, b);
        if (key == null) {
            return null;
        } else {
            if (key.getA().equals(a)) {
                return key.getValue();
            }
            return invert(key.getValue());
        }
    }

    public void setNewValueForKey(Vertex a, Vertex b, String newValue) {
        Key key = getKey(a, b);
        if (key != null) {
            key.setNewValue(newValue);
        }
    }
}
