package Hard.Splitwise.src.domain;

import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.interfaces.Observable;
import Hard.Splitwise.src.domain.interfaces.Observer;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;
import Hard.Splitwise.src.strategy.interfaces.SplitStrategy;

public class Expense implements Observable<Outstanding> {

    private Map<String, Outstanding> outstandings;
    private final Map<String, User> participants;
    private SplitStrategy splitStrategy;
    private Map<String, Split> splits;
    private final String expenseId;
    private String description;
    private double amount;
    private String name;
    private User paidBy;

    public Expense(Map<String, User> participants, Map<String, Split> splits, SplitStrategy splitStrategy,
            String expenseId, String description, double amount, String name, User paidBy) {
        this.participants = participants;
        this.splits = splits;
        this.splitStrategy = splitStrategy;
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.name = name;
        this.paidBy = paidBy;
    }

    // TODO: create a builder

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
        participants.values().forEach(u -> u.onMessage(outstandings.get(u.getEmail())));
    }

    public List<User> getParticipants() {
        return List.copyOf(participants.values());
    }

    public List<Split> getSplits() {
        return List.copyOf(splits.values());
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

    public void setAmount(double amount, List<Split> splits) throws InvalidSplitsCombinationException {
        this.outstandings = splitStrategy.getOutstandings(amount, splits);
        this.amount = amount;
        publish();
    }

    public void setSplitStrategy(SplitStrategy splitStrategy) throws InvalidSplitsCombinationException {
        this.outstandings = splitStrategy.getOutstandings(amount, getSplits());
        this.splitStrategy = splitStrategy;
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

}
