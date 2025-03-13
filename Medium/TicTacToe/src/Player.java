package src;

public class Player {
    private String name;
    private Symbol symbol;

    public static Player createPlayer() {
        return new Player();
    }

    public Player name(String name) {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.name = name;
        return this;
    }

    public Player symbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    @Override
    public String toString() {
        return name + " [" + symbol.name() + "]";
    }
}
