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

    public Key<S, T> getKey(S a, S b) {
        for (Key<S, T> key : keys) {
            if ((key.getA().equals(a)) && (key.getB().equals(b))) {
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

    public void addKey(Key<S, T> newKey) {
        if (getKey(newKey.getA(), newKey.getB()) == null) {
            keys.add(newKey);
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

//    public Key<S, T> getKey(S a, S b) {
//        return getK
//    }

    public int size() {
        return keys.size();
    }

    public Key<S, T> getKey(int index) {
        if ((index >= 0) && (index < keys.size())) {
            return keys.get(index);
        }
        System.out.println("KEY_TABLE: WRONG INDEX");
        return null;
    }
}
