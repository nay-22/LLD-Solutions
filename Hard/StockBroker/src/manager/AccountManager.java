package Hard.StockBroker.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.Payment;
import Hard.StockBroker.src.domain.User;
import Hard.StockBroker.src.domain.Wallet;
import Hard.StockBroker.src.exception.InsufficientPaymentMethodBalance;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;

public class AccountManager {
    private final Map<String, User> users;
    private final OrderManager orderManager;
    private final Map<String, Wallet> wallets;
    private final PortfolioManager portfolioManager;
    private final Map<String, Map<String, Payment>> paymentMethods;

    private static final class Singleton {
        private static final AccountManager INSTANCE = new AccountManager();
    }

    private AccountManager() {
        this.users = new ConcurrentHashMap<>();
        this.wallets = new ConcurrentHashMap<>();
        this.orderManager = OrderManager.getInstance();
        this.paymentMethods = new ConcurrentHashMap<>();
        this.portfolioManager = PortfolioManager.getInstance();
    }

    public static AccountManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(User user) throws InvalidOperationException {
        if (users.containsKey(user.getId())) {
            throw new InvalidOperationException("The user with specified Id: " + user.getId() + " already exists!");
        }
        users.put(user.getId(), user);
        portfolioManager.register(user.getId());
        wallets.put(user.getId(), new Wallet("wallet-" + user.getId(), user.getId()));
        paymentMethods.put(user.getId(), Map.of("payment-1-" + user.getId(),
                new Payment(user.getId(), "payment-1-" + user.getId(), "BHIM UPI")));
        orderManager.register(user.getId());
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
                double walletBalance = wallets.get(userId).getBalance();
                double paymentBalance = paymentMethods.get(userId).get(paymentId).getBalance();
                if (paymentBalance < amount)
                    throw new InsufficientPaymentMethodBalance("The payment with specified Id: " + paymentId
                            + " has insufficient balance: " + paymentBalance + " for withdrawal of amount: " + amount
                            + "!");
                paymentMethods.get(userId).get(paymentId).setBalance(paymentBalance - amount);
                wallets.get(userId).setBalance(walletBalance + amount);
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
                double walletBalance = wallets.get(userId).getBalance();
                double paymentBalance = paymentMethods.get(userId).get(paymentId).getBalance();
                if (walletBalance < amount)
                    throw new InsufficientWalletBalance("The wallet has insufficient balance: " + walletBalance
                            + " for withdrawal of amount: " + amount + "!");
                paymentMethods.get(userId).get(paymentId).setBalance(paymentBalance + amount);
                wallets.get(userId).setBalance(walletBalance - amount);
            }
        }
    }

    public void purchase(String userId, double amount)
            throws InsufficientWalletBalance, ResourceNotFoundException, InvalidOperationException {
        if (amount < 0) {
            throw new InvalidOperationException("The amount must be greater than zero!");
        }
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            double balance = wallets.get(userId).getBalance();
            if (balance < amount) {
                throw new InsufficientWalletBalance(
                        "The wallet has insufficient balance: " + balance
                                + " for purchase of amount: " + amount + "!");
            }
            wallets.get(userId).setBalance(balance - amount);
        }
    }

    public void recieve(String userId, double amount)
            throws ResourceNotFoundException, InvalidOperationException {
        if (amount < 0) {
            throw new InvalidOperationException("The amount must be greater than zero!");
        }
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            double balance = wallets.get(userId).getBalance();
            wallets.get(userId).setBalance(balance + amount);
        }
    }

    public void blockAmount(String userId, double amount)
            throws InsufficientWalletBalance, ResourceNotFoundException, InvalidOperationException {
        if (amount < 0) {
            throw new InvalidOperationException("The amount must be greater than zero!");
        }
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            double balance = wallets.get(userId).getBalance();
            double blockAmount = wallets.get(userId).getBlockAmount();
            double remainder = balance - blockAmount;
            if (remainder < amount) {
                throw new InsufficientWalletBalance(
                        "The wallet has insufficient remainder balance: " + remainder
                                + " (" + blockAmount + " blocked for order purchases" + ")" + "!");
            }
            wallets.get(userId).setBlockAmount(blockAmount + amount);
        }
    }

    public void unblockAmount(String userId, double amount)
            throws InsufficientWalletBalance, ResourceNotFoundException, InvalidOperationException {
        if (amount < 0) {
            throw new InvalidOperationException("The amount must be greater than zero!");
        }
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        } else {
            double blockAmount = wallets.get(userId).getBlockAmount();
            if (amount > blockAmount) {
                throw new InsufficientWalletBalance(
                        "Unblock amount: " + amount + ", greater than current blocked amount: " + blockAmount);
            }
            wallets.get(userId).setBlockAmount(blockAmount - amount);
        }
    }

    public double getWalletBalance(String userId) throws ResourceNotFoundException {
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return wallets.get(userId).getBalance();
    }

    public double getWalletBlockAmount(String userId) throws ResourceNotFoundException {
        if (!wallets.containsKey(userId)) {
            throw new ResourceNotFoundException("The user with specified Id: " + userId + " does not exists!");
        }
        return wallets.get(userId).getBlockAmount();
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

    public void printUserDetails(String userId) {
        System.out.println(users.get(userId));
        System.out.println(wallets.get(userId));
        System.out.println(paymentMethods.get(userId));
    }
}
