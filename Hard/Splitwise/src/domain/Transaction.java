package Hard.Splitwise.src.domain;

public class Transaction {
    private final String expenseId;
    private final String senderEmail;
    private final String recieverEmail;
    private final double amount;

    public Transaction(String expenseId, String senderEmail, String recieverEmail, double amount) {
        this.expenseId = expenseId;
        this.senderEmail = senderEmail;
        this.recieverEmail = recieverEmail;
        this.amount = amount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecieverEmail() {
        return recieverEmail;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction [expenseId=" + expenseId + ", senderEmail=" + senderEmail + ", recieverEmail="
                + recieverEmail + ", amount=" + amount + "]";
    }

}
