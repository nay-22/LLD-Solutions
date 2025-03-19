package Hard.StockBroker.src.domain;

public class Wallet {
    private final String id;
    private final String userId;
    private double amount;

    public Wallet(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.amount = 0;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Wallet [id=" + id + ", userId=" + userId + ", amount=" + amount + "]";
    }

}
