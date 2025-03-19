package Hard.StockBroker.src.domain;

public class Payment {
    private final String userId;
    private final String id;
    private double balance;
    private String name;

    public Payment(String userId, String id, String name) {
        this.userId = userId;
        this.name = name;
        this.balance = 0;
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Payment [userId=" + userId + ", id=" + id + ", balance=" + balance + ", name=" + name + "]";
    }

}
