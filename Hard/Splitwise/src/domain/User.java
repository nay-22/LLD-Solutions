package Hard.Splitwise.src.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.interfaces.Observer;

public class User implements Observer<Outstanding> {
    private final Map<String, Transaction> transactions;
    private final Map<String, Outstanding> outstandings;
    private final String email;
    private String fname;
    private String lname;
    private String phone;

    private User(Map<String, Transaction> transactions, Map<String, Outstanding> outstandings, String email,
            String fname,
            String lname, String phone) {
        this.transactions = transactions;
        this.outstandings = outstandings;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public static class UserBuilder {
        private Map<String, Transaction> transactions;
        private Map<String, Outstanding> outstandings;
        private String email;
        private String fname;
        private String lname;
        private String phone;

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder fname(String fname) {
            this.fname = fname;
            return this;
        }

        public UserBuilder lname(String lname) {
            this.lname = lname;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            this.transactions = new ConcurrentHashMap<>();
            this.outstandings = new ConcurrentHashMap<>();
            return new User(transactions, outstandings, email, fname, lname, phone);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public void onMessage(Outstanding message) {
        if (message.getAmount() == 0.0) {
            outstandings.remove(message.getExpenseId());
        } else {
            outstandings.put(message.getExpenseId(), message);

        }
    }

    protected void addTransaction(String expenseId, Transaction transaction) {
        transactions.put(expenseId, transaction);
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
        return List.copyOf(outstandings.values());
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions.values());
    }

    public Outstanding getOutstandingByExpenseId(String expenseId) {
        return outstandings.get(expenseId);
    }

    @Override
    public String toString() {
        return "User [transactions=" + transactions + ", outstandings=" + outstandings + ", email=" + email + ", fname="
                + fname
                + ", lname=" + lname + ", phone=" + phone + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

}
