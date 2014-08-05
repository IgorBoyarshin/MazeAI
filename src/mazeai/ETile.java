package mazeai;

/**
 * Created:  15.07.2014 19:30.
 */
public enum ETile {
    WALL('1'), SPACE('0'), START('S'), FINISH('F');

    private char symbol;

    private ETile(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
