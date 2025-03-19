package Hard.Splitwise.src.domain;

import java.time.LocalDate;

public class Transaction extends Auditable {
    private final String transactionId;
    private final String expenseId;
    private final String senderEmail;
    private final String receiverEmail;
    private final double amount;

    public Transaction(String expenseId, String senderEmail, String receiverEmail, double amount) {
        super(senderEmail);
        this.transactionId = expenseId + "-" + senderEmail + "-" + receiverEmail + "-" + LocalDate.now();
        this.expenseId = expenseId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.amount = amount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecieverEmail() {
        return receiverEmail;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", createdAt=" + createdAt + ", expenseId=" + expenseId
                + ", createdBy=" + createdBy + ", senderEmail=" + senderEmail + ", lastModifiedAt=" + lastModifiedAt
                + ", receiverEmail=" + receiverEmail + ", lastModifiedBy=" + lastModifiedBy + ", amount=" + amount
                + "]";
    }

}
