package Hard.StockBroker.src.domain;

import java.time.LocalDateTime;

import Hard.StockBroker.src.domain.interfaces.Observer;
import Hard.StockBroker.src.domain.interfaces.OrderExecutionStrategy;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;
import Hard.StockBroker.src.manager.ExchangeManager;
import Hard.StockBroker.src.manager.PortfolioManager;
import Hard.StockBroker.src.manager.StrategyManager;

public class Order implements Observer<Stock> {
    private final OrderExecutionStrategy executionStrategy;
    final PortfolioManager portfolioManager;
    final ExchangeManager exchangeManager;
    final LocalDateTime createdAt;
    final String portfolioId;
    LocalDateTime executedAt;
    final String stockId;
    final OrderType type;
    double executedPrice;
    final String userId;
    OrderStatus status;
    double marketPrice;
    double orderPrice;
    final String id;
    int quantity;

    Order(String id, String userId, String portfolioId, String stockId, double orderPrice, double marketPrice,
            int quantity,
            OrderType type) {
        this.executionStrategy = StrategyManager.getInstance().getOrderExecutionStrategy(type);
        this.portfolioManager = PortfolioManager.getInstance();
        this.exchangeManager = ExchangeManager.getInstance();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.ONGOING;
        this.marketPrice = marketPrice;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.stockId = stockId;
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.type = type;
        this.id = id;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    @Override
    public void onNotify(Stock stock) {
        if (status == OrderStatus.ONGOING) {
            this.marketPrice = stock.getPrice();
            executionStrategy.execute(stock, this);
            System.out.println(this);
        }
    }

    void setStatus(OrderStatus status) throws InvalidOperationException {
        if (this.status != OrderStatus.ONGOING) {
            throw new InvalidOperationException("The order has already been executed!");
        } else {
            this.status = status;
            this.executedAt = LocalDateTime.now();
        }
    }

    public void cancel() throws InvalidOperationException, InsufficientWalletBalance, ResourceNotFoundException {
        if (this.status != OrderStatus.ONGOING) {
            throw new InvalidOperationException("The order has already been executed!");
        } else {
            this.status = OrderStatus.CANCELLED;
            exchangeManager.getStockById(stockId).unsubscribe(this);
        }
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStockId() {
        return stockId;
    }

    public OrderType getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        if (status != OrderStatus.ONGOING) {
            return;
        }
        this.marketPrice = marketPrice;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        if (status != OrderStatus.ONGOING) {
            return;
        }
        this.orderPrice = orderPrice;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (status != OrderStatus.ONGOING) {
            return;
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order {\n\tid: " + id
                + ",\n\tcreatedAt: " + createdAt
                + ",\n\texecutedAt: " + executedAt
                + ",\n\tportfolioId: " + portfolioId
                + ",\n\tstockId: " + stockId
                + ",\n\tuserId: " + userId
                + ",\n\ttype: " + type
                + ",\n\tstatus: " + status
                + ",\n\texecutedPrice: $" + executedPrice
                + ",\n\tmarketPrice: $" + marketPrice
                + ",\n\torderPrice: $" + orderPrice
                + ",\n\tquantity: " + quantity
                + "\n}";
    }

    public double getExecutedPrice() {
        return executedPrice;
    }

}
