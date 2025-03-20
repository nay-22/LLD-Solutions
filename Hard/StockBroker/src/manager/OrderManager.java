package Hard.StockBroker.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.Order;
import Hard.StockBroker.src.domain.OrderStatus;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;

public class OrderManager {
    private final Map<String, Map<String, Order>> orders;

    private static final class Singleton {
        private static final OrderManager INSTANCE = new OrderManager();
    }

    private OrderManager() {
        this.orders = new ConcurrentHashMap<>();
    }

    public static OrderManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(String userId) {
        orders.put(userId, new ConcurrentHashMap<>());
    }

    public void createOrder(Order order) throws ResourceNotFoundException, InvalidOperationException {
        if (!orders.containsKey(order.getUserId())) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + order.getUserId() + " does not exists!");
        } else {
            if (orders.get(order.getUserId()).containsKey(order.getId())) {
                throw new InvalidOperationException(
                        "The order with specified Id: " + order.getId() + " already exists!");
            }
            orders.get(order.getUserId()).put(order.getId(), order);
        }
    }

    public void cancelOrder(String userId, String orderId)
            throws ResourceNotFoundException, InvalidOperationException, InsufficientWalletBalance {
        if (!orders.containsKey(userId)) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + userId + " does not exists!");
        } else {
            if (orders.get(userId).containsKey(orderId)) {
                throw new InvalidOperationException(
                        "The order with specified Id: " + orderId + " already exists!");
            }
            orders.get(userId).get(orderId).cancel();
        }
    }

    public List<Order> getOrdersByUserId(String userId) throws ResourceNotFoundException {
        if (!orders.containsKey(userId)) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + userId + " does not exists!");
        }
        return List.copyOf(orders.getOrDefault(userId, Map.of()).values());
    }

    public Order getOrderById(String userId, String orderId) throws ResourceNotFoundException {
        if (!orders.containsKey(userId)) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + userId + " does not exists!");
        }
        if (!orders.get(userId).containsKey(orderId)) {
            throw new ResourceNotFoundException(
                    "The order with specified Id: " + orderId + " does not exists!");
        }
        return orders.get(userId).get(orderId);
    }

    public List<Order> getFailedOrdersByUserId(String userId) throws ResourceNotFoundException {
        if (!orders.containsKey(userId)) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + userId + " does not exists!");
        }
        return orders.getOrDefault(userId, Map.of()).values().parallelStream()
                .filter(o -> o.getStatus() == OrderStatus.FAIL)
                .toList();
    }

    public List<Order> getSuccessfulOrdersByUserId(String userId) throws ResourceNotFoundException {
        if (!orders.containsKey(userId)) {
            throw new ResourceNotFoundException(
                    "The user with specified Id: " + userId + " does not exists!");
        }
        return orders.getOrDefault(userId, Map.of()).values().parallelStream()
                .filter(o -> o.getStatus() == OrderStatus.SUCCESS)
                .toList();
    }
}
