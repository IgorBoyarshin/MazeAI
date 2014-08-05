package mazeai.mazewalker;

/**
 * Created by Igor on 03.08.2014 at 18:47.
 */
public enum EDirection {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');

    private final char symbol;

    private EDirection(char symbol) {
        this.symbol = symbol;
    }

    public final char getSymbol() {
        return symbol;
    }
}
