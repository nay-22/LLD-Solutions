package Hard.Splitwise.src.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.interfaces.Observer;

public class User implements Observer<Outstanding> {
    private final Map<String, Transaction> transactions;
    private final Map<String, Outstanding> pending;
    private final String email;
    private String fname;
    private String lname;
    private String phone;

    public User(Map<String, Transaction> transactions, Map<String, Outstanding> pending, String email, String fname,
            String lname, String phone) {
        this.transactions = transactions;
        this.pending = pending;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public User(String email, String fname) {
        this.transactions = new HashMap<>();
        this.pending = new HashMap<>();
        this.email = email;
        this.fname = fname;
    }

    @Override
    public void onMessage(Outstanding message) {
        pending.getOrDefault(message.getExpenseId(), message).setAmount(message.getAmount()); 
    }

    protected void addTransaction(String expenseId, Transaction transaction) {
        transactions.put(expenseId, transaction);
    }

    public Map<String, Transaction> getTransaction() {
        return transactions;
    }

    public Map<String, Outstanding> getPending() {
        return pending;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Outstanding> getOutstandings() {
        return List.copyOf(pending.values());
    }

    public List<Transaction> geTransactions() {
        return List.copyOf(transactions.values());
    }

    @Override
    public String toString() {
        return "User [transactions=" + transactions + ", pending=" + pending + ", email=" + email + ", fname=" + fname
                + ", lname=" + lname + ", phone=" + phone + "]";
    }

}
