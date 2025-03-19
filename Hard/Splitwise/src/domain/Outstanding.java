package Hard.Splitwise.src.domain;

public class Outstanding {
    private final String expenseId;
    private double amount;

    public Outstanding(String expenseId, double amount) {
        this.expenseId = expenseId;
        this.amount = amount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Outstanding [expenseId=" + expenseId + ", amount=" + amount + "]";
    }

}
