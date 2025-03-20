package Hard.StockBroker.src.domain;

import java.time.LocalDateTime;

import Hard.StockBroker.src.domain.interfaces.OrderExecutionStrategy;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.exception.ResourceNotFoundException;
import Hard.StockBroker.src.manager.AccountManager;

public class BuyOrderExecutionStrategy implements OrderExecutionStrategy {

    private static final AccountManager accountManager = AccountManager.getInstance();

    @Override
    public void execute(Stock stock, Order order) {
        if (stock.getPrice() <= order.orderPrice) {
            try {
                order.executedPrice = stock.getPrice();
                order.executedAt = LocalDateTime.now();
                if (!order.portfolioManager.getPortfolio(order.userId, order.portfolioId).hasStockMeta(stock.getId())) {
                    order.portfolioManager.getPortfolio(order.userId, order.portfolioId)
                            .addStockMeta(
                                    new StockMeta(order.stockId, order.quantity, order.executedPrice,
                                            order.marketPrice));
                } else {
                    StockMeta oldMeta = order.portfolioManager.getPortfolio(order.userId, order.portfolioId)
                            .getStockMetaByStockId(stock.getId());
                    order.portfolioManager.getPortfolio(order.userId, order.portfolioId)
                            .updateStockMeta(
                                    new StockMeta(order.stockId, oldMeta.getQuantity() + order.quantity,
                                            reconciledAverage(oldMeta, order),
                                            order.marketPrice));
                }
                order.exchangeManager.getStockById(order.stockId).unsubscribe(order);
                accountManager.unblockAmount(order.userId, order.orderPrice * order.quantity);
                accountManager.purchase(order.userId, order.executedPrice * order.quantity);
                order.setStatus(OrderStatus.SUCCESS);
            } catch (ResourceNotFoundException | InvalidOperationException | InsufficientWalletBalance e) {
                e.printStackTrace();
            }
        }
    }

    private double reconciledAverage(StockMeta oldMeta, Order order) {
        return (oldMeta.getInvested() + (order.executedPrice * order.quantity))
                / (oldMeta.getQuantity() + order.quantity);
    }

}
