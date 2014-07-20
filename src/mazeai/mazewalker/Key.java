package mazeai.mazewalker;

/**
 * Created by Igor on 19.07.2014 at 17:53.
 */
public class Key<S, T> {
    private S a;
    private S b;
    private T value;

    public Key(S a, S b, T value) {
        this.a = a;
        this.b = b;
        this.value = value;
    }

    public S getA() {
        return a;
    }

    public S getB() {
        return b;
    }

    public T getValue() {
        return value;
    }

    public void setNewValue(T newValue) {
        this.value = newValue;
    }
}
