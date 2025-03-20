package Hard.StockBroker.src.domain;

import java.time.LocalDateTime;

import Hard.StockBroker.src.domain.interfaces.OrderExecutionStrategy;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;
import Hard.StockBroker.src.manager.AccountManager;

public class SellOrderExecutionStrategy implements OrderExecutionStrategy {

    private static final AccountManager accountManager = AccountManager.getInstance();

    @Override
    public void execute(Stock stock, Order order) {
        if (stock.getPrice() >= order.orderPrice) {
            System.out.println("ORDER QUANTITY: " + order.quantity);
            try {
                order.executedPrice = stock.getPrice();
                order.executedAt = LocalDateTime.now();
                StockMeta oldMeta = order.portfolioManager.getPortfolio(order.userId, order.portfolioId)
                        .getStockMetaByStockId(stock.getId());
                order.portfolioManager.getPortfolio(order.userId, order.portfolioId)
                        .updateStockMeta(new StockMeta(stock.getId(), order.quantity, oldMeta.getAverageOrderPrice(),
                                order.marketPrice));
                order.exchangeManager.getStockById(order.stockId).unsubscribe(order);
                accountManager.recieve(order.userId, order.executedPrice * order.quantity);
                order.setStatus(OrderStatus.SUCCESS);
            } catch (ResourceNotFoundException | InvalidOperationException e) {
                e.printStackTrace();
            }
        }
    }
}
