package Hard.StockBroker.src.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.BuyOrderExecutionStrategy;
import Hard.StockBroker.src.domain.OrderType;
import Hard.StockBroker.src.domain.SellOrderExecutionStrategy;
import Hard.StockBroker.src.domain.interfaces.OrderExecutionStrategy;

public class StrategyManager {

    private final Map<OrderType, OrderExecutionStrategy> orderExecutionStrategies;

    private static final class Singleton {
        private static final StrategyManager INSTANCE = new StrategyManager();
    }

    private StrategyManager() {
        orderExecutionStrategies = new ConcurrentHashMap<>(Map.of(OrderType.BUY, new BuyOrderExecutionStrategy(),
                OrderType.SELL, new SellOrderExecutionStrategy()));

    }

    public static StrategyManager getInstance() {
        return Singleton.INSTANCE;
    }

    public OrderExecutionStrategy getOrderExecutionStrategy(OrderType type) {
        return orderExecutionStrategies.get(type);
    }
}
