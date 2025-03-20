package Hard.StockBroker.src.domain;

import java.util.concurrent.atomic.AtomicInteger;

import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceCreationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;
import Hard.StockBroker.src.manager.AccountManager;
import Hard.StockBroker.src.manager.ExchangeManager;
import Hard.StockBroker.src.manager.PortfolioManager;

public class OrderBuilder {
    private final PortfolioManager portfolioManager;
    private final ExchangeManager exchangeManager;
    private static final AtomicInteger key = new AtomicInteger(0);
    private final AccountManager accountManager;
    private String portfolioId;
    private double orderPrice;
    private OrderType orderType;
    private String stockId;
    private String userId;
    private int quantity;

    OrderBuilder() {
        portfolioManager = PortfolioManager.getInstance();
        exchangeManager = ExchangeManager.getInstance();
        accountManager = AccountManager.getInstance();
    }

    public OrderBuilder stockId(String stockId) {
        this.stockId = stockId;
        return this;
    }

    public OrderBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder portfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
        return this;
    }

    public OrderBuilder orderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    public OrderBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder orderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public Order build() throws InsufficientWalletBalance, ResourceNotFoundException, ResourceNotFoundException {
        try {
            if (orderType == null || stockId == null || userId == null || portfolioId == null || quantity == 0) {
                throw new ResourceCreationException(
                        "All mentioned fields must be non-null: orderType, stockId, userId, portfolioId, quantity (> 0)");
            }
            if (orderType == OrderType.BUY) {
                accountManager.blockAmount(userId, orderPrice * quantity);
            } else {
                StockMeta meta = portfolioManager.getPortfolio(userId, portfolioId).getStockMetaByStockId(stockId);
                if (meta == null) {
                    throw new InvalidOperationException(
                            "You don't possess any stocks specified by stockId: " + stockId);
                } else if (meta.getQuantity() < quantity) {
                    throw new InvalidOperationException(
                            "Owned Quantity: " + meta.getQuantity() + ", Request Quantity: " + quantity);
                }
            }
        } catch (Exception e) {
            throw new InsufficientWalletBalance("Wallet Balance: " + accountManager.getWalletBalance(userId)
                    + ", Request Amount: " + orderPrice * quantity);
        }
        Order order = new Order(buildId(), userId, portfolioId, stockId, orderPrice,
                exchangeManager.getStockById(stockId).getPrice(), quantity,
                orderType);
        exchangeManager.getStockById(stockId).subscribe(order);
        return order;
    }

    private String buildId() {
        return "order-build-" + key.getAndIncrement() + "-" + userId + "-" + stockId + "-" + portfolioId;
    }

}
