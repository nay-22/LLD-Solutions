package Hard.StockBroker.src.domain;

public class Wallet {
    private final String id;
    private final String userId;
    private double balance;
    private double blockAmount;

    public Wallet(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.balance = 0;
        this.blockAmount = 0;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBlockAmount() {
        return blockAmount;
    }

    public void setBlockAmount(double blockAmount) {
        this.blockAmount = blockAmount;
    }

    @Override
    public String toString() {
        return "Wallet [id=" + id + ", userId=" + userId + ", balance=" + balance + ", blockAmount=" + blockAmount + "]";
    }

}
