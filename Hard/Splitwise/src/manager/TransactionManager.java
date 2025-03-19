package Hard.Splitwise.src.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Hard.Splitwise.src.domain.Transaction;

public class TransactionManager {
    private final Map<String, Transaction> transactions;
    private final Map<String, Set<Transaction>> transactionsBySender;
    private final Map<String, Set<Transaction>> transactionsByReceiver;

    private static class Singleton {
        private final static TransactionManager INSTANCE = new TransactionManager();
    }

    private TransactionManager() {
        transactions = new ConcurrentHashMap<>();
        transactionsBySender = new ConcurrentHashMap<>();
        transactionsByReceiver = new ConcurrentHashMap<>();
    }

    public static TransactionManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void addTransaction(Transaction transaction) {
        transactions.putIfAbsent(transaction.getTransactionId(), transaction);
        transactionsBySender.computeIfAbsent(transaction.getSenderEmail(), _ -> ConcurrentHashMap.newKeySet())
                .add(transaction);
        transactionsByReceiver.computeIfAbsent(transaction.getRecieverEmail(), _ -> ConcurrentHashMap.newKeySet())
                .add(transaction);
    }

    public List<Transaction> getTransactionsBySender(String senderEmail) {
        return new ArrayList<>(transactionsBySender.getOrDefault(senderEmail, new HashSet<>()));
    }

    public List<Transaction> getTransactionsByReceiver(String receiverEmail) {
        return new ArrayList<>(transactionsByReceiver.getOrDefault(receiverEmail, new HashSet<>()));
    }

    public List<Transaction> getAllTransactionsInvolving(String email) {
        Queue<Transaction> queue = new PriorityQueue<>(getTransactionsByReceiver(email));
        queue.addAll(getTransactionsBySender(email));
        return List.copyOf(queue);
    }

    public Transaction getById(String expenseId) {
        return transactions.get(expenseId);
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(transactions.values());
    }

    @Override
    public String toString() {
        return "TransactionManager [transactions=" + transactions.values() + "]";
    }
}
