package Hard.Splitwise.src.domain;

public class Split {
    private final String expenseId;
    private final String userEmail;
    private double share;

    public Split(String expenseId, String userEmail, double share) {
        this.expenseId = expenseId;
        this.userEmail = userEmail;
        this.share = share;
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

    @Override
    public String toString() {
        return "Split [expenseId=" + expenseId + ", userEmail=" + userEmail + ", share=" + share + "]";
    }

}
