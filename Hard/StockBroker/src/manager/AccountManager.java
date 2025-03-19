package Hard.StockBroker.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Easy.StackOverflow.src.exceptions.ResourceNotFoundException;
import Hard.StockBroker.src.domain.Payment;
import Hard.StockBroker.src.domain.User;
import Hard.StockBroker.src.domain.Wallet;
import Hard.StockBroker.src.exception.InsufficientPaymentMethodBalance;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;

public class AccountManager {
    private final Map<String, User> users;
    private final Map<String, Wallet> wallets;
    private final Map<String, Map<String, Payment>> paymentMethods;

    private static final class Singleton {
        private static final AccountManager INSTANCE = new AccountManager();
    }

    private AccountManager() {
        this.users = new ConcurrentHashMap<>();
        this.wallets = new ConcurrentHashMap<>();
        this.paymentMethods = new ConcurrentHashMap<>();
    }

    public static AccountManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(User user) throws InvalidOperationException {
        if (users.containsKey(user.getId())) {
            throw new InvalidOperationException("The user with specified Id: " + user.getId() + " already exists!");
        }
        users.put(user.getId(), user);
    }

    public void deregister(String userId) throws ResourceNotFoundException {
        if (!users.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        users.remove(userId);
    }

    public void addPayment(Payment payment) throws InvalidOperationException {
        if (!paymentMethods.containsKey(payment.getUserId())) {
            throw new InvalidOperationException(
                    "The user with specified Id: " + payment.getUserId() + " already exists!");
        } else {
            if (paymentMethods.get(payment.getUserId()).containsKey(payment.getId())) {
                throw new InvalidOperationException(
                        "The payment with specified Id: " + payment.getId() + " already exists!");
            }
            paymentMethods.get(payment.getUserId()).put(payment.getId(), payment);
        }
    }

    public void removePayment(String userId, String paymentId) throws ResourceNotFoundException {
        if (!paymentMethods.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            if (!paymentMethods.get(userId).containsKey(paymentId)) {
                throw new ResourceNotFoundException(
                        "The payment with specified Id: " + paymentId + " does not exists!");
            }
            paymentMethods.get(userId).remove(paymentId);
        }
    }

    public void addBalance(String userId, String paymentId, double amount)
            throws InsufficientPaymentMethodBalance, ResourceNotFoundException {
        if (!paymentMethods.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            if (!paymentMethods.get(userId).containsKey(paymentId)) {
                throw new ResourceNotFoundException(
                        "The payment with specified Id: " + paymentId + " does not exists!");
            } else {
                double walletBalance = wallets.get(userId).getAmount();
                double paymentBalance = paymentMethods.get(userId).get(paymentId).getBalance();
                if (paymentBalance < amount)
                    throw new InsufficientPaymentMethodBalance("The payment with specified Id: " + paymentId
                            + " has insufficient balance: " + paymentBalance + " for withdrawal of amount: " + amount
                            + "!");
                paymentMethods.get(userId).get(paymentId).setBalance(paymentBalance - amount);
                wallets.get(userId).setAmount(walletBalance + amount);
            }
        }
    }

    public void withdraw(String userId, String paymentId, double amount)
            throws InsufficientPaymentMethodBalance, ResourceNotFoundException, InsufficientWalletBalance {
        if (!paymentMethods.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            if (!paymentMethods.get(userId).containsKey(paymentId)) {
                throw new ResourceNotFoundException(
                        "The payment with specified Id: " + paymentId + " does not exists!");
            } else {
                double walletBalance = wallets.get(userId).getAmount();
                double paymentBalance = paymentMethods.get(userId).get(paymentId).getBalance();
                if (walletBalance < amount)
                    throw new InsufficientWalletBalance("The wallet has insufficient balance: " + walletBalance
                            + " for withdrawal of amount: " + amount + "!");
                paymentMethods.get(userId).get(paymentId).setBalance(paymentBalance + amount);
                wallets.get(userId).setAmount(walletBalance - amount);
            }
        }
    }

    public double getWalletBalance(String userId) throws ResourceNotFoundException {
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return wallets.get(userId).getAmount();
    }

    public List<Payment> getPaymentMethods(String userId) throws ResourceNotFoundException {
        if (!paymentMethods.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return List.copyOf(paymentMethods.get(userId).values());
    }

    public User getUserById(String userId) throws ResourceNotFoundException {
        if (!users.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }
}
