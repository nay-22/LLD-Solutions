package Hard.Splitwise.src.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Group {
    private final Map<String, Expense> expenses;
    private final Map<String, User> members;
    private final String groupId;
    private String description;
    private String name;

    private Group(Map<String, Expense> expenses, Map<String, User> members, String groupId, String description,
            String name) {
        this.expenses = expenses;
        this.members = members;
        this.groupId = groupId;
        this.description = description;
        this.name = name;
    }

    public static class GroupBuilder {
        private Map<String, Expense> expenses;
        private Map<String, User> members;
        private String groupId;
        private String description;
        private String name;

        private GroupBuilder() {
            this.members = new ConcurrentHashMap<>();
            this.expenses = new ConcurrentHashMap<>();
        }

        public GroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GroupBuilder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public GroupBuilder member(User member) {
            members.put(member.getEmail(), member);
            return this;
        }

        public GroupBuilder members(List<User> members) {
            members.forEach(m -> this.members.put(m.getEmail(), m));
            return this;
        }

        public GroupBuilder expenses(List<Expense> expenses) {
            expenses.forEach(e -> this.expenses.put(e.getExpenseId(), e));
            return this;
        }

        public Group build() {
            return new Group(expenses, members, groupId, description, name);
        }
    }

    public static GroupBuilder builder() {
        return new GroupBuilder();
    }

    public void addExpense(Expense expense) {
        expenses.put(expense.getExpenseId(), expense);
    }

    public Expense removeExpense(String expenseId) {
        return expenses.remove(expenseId);
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses.values());
    }

    public void addMember(User user) {
        members.put(user.getEmail(), user);
    }

    public User removeUser(String email) {
        return members.remove(email);
    }

    public List<User> getMembers() {
        return List.copyOf(members.values());
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group [expenses=" + expenses + ", members=" + members + ", groupId=" + groupId + ", description="
                + description + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
        Group other = (Group) obj;
        if (groupId == null) {
            if (other.groupId != null)
                return false;
        } else if (!groupId.equals(other.groupId))
            return false;
        return true;
    }

}
