package Hard.StockBroker.src.domain.interfaces;

import Hard.StockBroker.src.domain.Order;
import Hard.StockBroker.src.domain.Stock;

public interface OrderExecutionStrategy {
    void execute(Stock stock, Order order);
}
