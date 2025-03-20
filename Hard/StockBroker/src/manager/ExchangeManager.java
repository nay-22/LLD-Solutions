package Hard.StockBroker.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.domain.Stock;

public class ExchangeManager {
    private final Map<String, Stock> stocks;

    private static final class Singleton {
        private static final ExchangeManager INSTANCE = new ExchangeManager();
    }

    private ExchangeManager() {
        stocks = new ConcurrentHashMap<>();
    }

    public static ExchangeManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(Stock stock) {
        stocks.put(stock.getId(), stock);
    }

    public Stock getStockById(String stockId) {
        return stocks.get(stockId);
    }

    public List<Stock> getStocks() {
        return List.copyOf(stocks.values());
    }
}
