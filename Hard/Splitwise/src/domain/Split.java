package Hard.Splitwise.src.domain;

public class Split {
    private final String expenseId;
    private final String userEmail;
    private final SplitType type;
    private double share;

    public Split(String expenseId, String userEmail, double share, SplitType type) {
        this.expenseId = expenseId;
        this.userEmail = userEmail;
        this.share = share;
        this.type = type;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    public SplitType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Split [expenseId=" + expenseId + ", userEmail=" + userEmail + ", type=" + type + ", share=" + share
                + "]";
    }

}
