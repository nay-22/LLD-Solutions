package Hard.Splitwise.src.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.Splitwise.src.domain.interfaces.Observable;
import Hard.Splitwise.src.domain.interfaces.Observer;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;
import Hard.Splitwise.src.exception.ResourceNotFoundException;
import Hard.Splitwise.src.manager.TransactionManager;
import Hard.Splitwise.src.strategy.EqualSplitStrategy;
import Hard.Splitwise.src.strategy.interfaces.SplitStrategy;

public class Expense implements Observable<Outstanding> {

    private final TransactionManager transactionManager;
    private Map<String, Outstanding> outstandings;
    private final Map<String, User> participants;
    private SplitStrategy splitStrategy;
    private Map<String, Split> splits;
    private final String expenseId;
    private String description;
    private double amount;
    private String name;
    private User paidBy;

    public Expense(Map<String, User> participants, Map<String, Split> splits, Map<String, Outstanding> outstandings,
            SplitStrategy splitStrategy,
            String expenseId, String description, double amount, String name, User paidBy) {
        this.transactionManager = TransactionManager.getInstance();
        this.splitStrategy = splitStrategy;
        this.outstandings = outstandings;
        this.participants = participants;
        this.description = description;
        this.expenseId = expenseId;
        this.paidBy = paidBy;
        this.splits = splits;
        this.amount = amount;
        this.name = name;
    }

    public static class ExpenseBuilder {
        private Map<String, Outstanding> outstandings;
        private Map<String, User> participants;
        private SplitStrategy splitStrategy;
        private Map<String, Split> splits;
        private String description;
        private String expenseId;
        private double amount;
        private String name;
        private User paidBy;

        private ExpenseBuilder() {
            this.outstandings = new ConcurrentHashMap<>();
            this.participants = new ConcurrentHashMap<>();
            this.splits = new ConcurrentHashMap<>();
        }

        public ExpenseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ExpenseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ExpenseBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public ExpenseBuilder paidBy(User paidBy) {
            this.paidBy = paidBy;
            return this;
        }

        public ExpenseBuilder expenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public ExpenseBuilder splits(List<Split> splits) {
            splits.forEach(s -> this.splits.put(s.getUserEmail(), s));
            return this;
        }

        public ExpenseBuilder outstandings(List<Outstanding> outstandings) {
            outstandings.forEach(o -> this.outstandings.put(o.getUserEmail(), o));
            return this;
        }

        public ExpenseBuilder participants(List<User> participants) {
            participants.forEach(p -> this.participants.put(p.getEmail(), p));
            return this;
        }

        public ExpenseBuilder splitStrategy(SplitStrategy splitStrategy) {
            this.splitStrategy = splitStrategy;
            return this;
        }

        public Expense build() throws InvalidSplitsCombinationException, ResourceNotFoundException {
            if (paidBy == null) {
                throw new ResourceNotFoundException("Paid by cannot be null when creating a new expense.");
            }
            if (splitStrategy == null) {
                splitStrategy = new EqualSplitStrategy();
                if (participants != null) {
                    participants.values().forEach(p -> {
                        splits.put(p.getEmail(), new Split(expenseId, p.getEmail(), amount / participants.size(),
                                SplitType.AMOUNT));
                    });
                    outstandings = splitStrategy.getOutstandings(amount, List.copyOf(splits.values()));
                    outstandings.remove(paidBy.getEmail());
                }
            }
            Expense expense = new Expense(participants, splits, outstandings, splitStrategy, expenseId, description,
                    amount, name,
                    paidBy);
            expense.publish();
            return expense;
        }
    }

    public static ExpenseBuilder builder() {
        return new ExpenseBuilder();
    }

    @Override
    public void subscribe(Observer<Outstanding> observer) {
        if (observer instanceof User user) {
            participants.put(user.getEmail(), user);
        } else {
            throw new ClassCastException("Expected User object");
        }
    }

    @Override
    public void unsubscribe(Observer<Outstanding> observer) {
        if (observer instanceof User user) {
            participants.remove(user.getEmail());
        } else {
            throw new ClassCastException("Expected User object");
        }
    }

    @Override
    public void publish() {
        participants.values().forEach(u -> {
            if (u != paidBy) {
                u.onMessage(outstandings.get(u.getEmail()));
            }
        });
    }

    public void settle(User user) throws ResourceNotFoundException {
        settle(user, splitStrategy.getOutstandingRemainder(amount, 0, splits.get(user.getEmail())));
    }

    public void settle(User user, double amount) throws ResourceNotFoundException {
        if (!participants.containsKey(user.getEmail())) {
            throw new ResourceNotFoundException("The specified user is not listed to the expense.");
        }
        if (splitStrategy.isSettlementValid(this.amount, amount, splits.get(user.getEmail()))) {
            // create transaction => then add to the corresponding user
            transactionManager.addTransaction(new Transaction(expenseId, user.getEmail(), paidBy.getEmail(), amount));
            user.addTransaction(expenseId,
                    new Transaction(expenseId, user.getEmail(), paidBy.getEmail(), amount));
            // update corresponding user's outstandings
            user.onMessage(new Outstanding(expenseId, user.getEmail(),
                    splitStrategy.getOutstandingRemainder(this.amount, amount, splits.get(user.getEmail()))));
        } else {
            System.err.println("Settlement Invalid");
        }

    }

    public List<User> getParticipants() {
        return List.copyOf(participants.values());
    }

    public List<Split> getSplits() {
        return List.copyOf(splits.values());
    }

    public List<Outstanding> getOutstanding() {
        return List.copyOf(outstandings.values());
    }

    public SplitStrategy getSplitStrategy() {
        return splitStrategy;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount, SplitStrategy splitStrategy, List<Split> splits)
            throws InvalidSplitsCombinationException {
        this.splitStrategy = splitStrategy;
        this.outstandings = splitStrategy.getOutstandings(amount, splits);
        this.amount = amount;
        publish();
    }

    public void setSplits(Map<String, Split> splits) throws InvalidSplitsCombinationException {
        this.outstandings = splitStrategy.getOutstandings(amount, List.copyOf(splits.values()));
        this.splits = splits;
        publish();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    @Override
    public String toString() {
        return "Expense [participants=" + participants + ", splitStrategy=" + splitStrategy + ", splits=" + splits
                + ", expenseId=" + expenseId + ", description=" + description + ", amount=" + amount + ", name=" + name
                + ", paidBy=" + paidBy + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
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
        Expense other = (Expense) obj;
        if (expenseId == null) {
            if (other.expenseId != null)
                return false;
        } else if (!expenseId.equals(other.expenseId))
            return false;
        return true;
    }

}
