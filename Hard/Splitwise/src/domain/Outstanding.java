package Hard.Splitwise.src.domain;

public class Outstanding {
    private final String expenseId;
    private final String userEmail;
    private final double amount;

    public Outstanding(String expenseId, String userEmail, double amount) {
        this.expenseId = expenseId;
        this.userEmail = userEmail;
        this.amount = amount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Outstanding [expenseId=" + expenseId + ", userEmail=" + userEmail + ", amount=" + amount + "]";
    }

}
