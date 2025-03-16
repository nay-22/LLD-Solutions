package Medium.PubSubSystem.src.domain.stockUpdateNotifications;

public class StockMessage {
    private final String name;
    private final double price;

    public StockMessage(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{" + name.toUpperCase() + ": " + price + "}";
    }

}
