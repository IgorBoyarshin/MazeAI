package mazeai.mazewalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 19.07.2014 at 17:52.
 */
public class KeyTable<S, T> {

    private List<Key<S, T>> keys;

    public KeyTable() {
        keys = new ArrayList<Key<S, T>>();
    }

    private Key<S, T> getKey(S a, S b) {
        for (Key<S, T> key : keys) {
            if (((key.getA().equals(a)) && (key.getB().equals(b)))
                    || ((key.getA().equals(b)) && (key.getB().equals(a)))) {

                return key;
            }
        }

        return null;
    }

    public void addKey(S a, S b, T value) {
        if (getKey(a, b) == null) {
            keys.add(new Key(a, b, value));
        }
    }

    public boolean keyExists(S a, S b) {
        return (getKey(a, b) != null);
    }

    public T getValueForKey(S a, S b) {
        Key<S, T> key = getKey(a, b);
        if (key == null) {
            return null;
        } else {
            return key.getValue();
        }
    }

    public void setNewValueForKey(S a, S b, T newValue) {
        Key<S, T> key = getKey(a, b);
        if (key != null) {
            key.setNewValue(newValue);
        }
    }
}
