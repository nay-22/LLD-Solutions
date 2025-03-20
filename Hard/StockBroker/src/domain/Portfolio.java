package Hard.StockBroker.src.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.StockBroker.src.manager.ExchangeManager;

public class Portfolio {
    private final Map<String, StockMeta> stockMetas;
    private final ExchangeManager exchangeManager;
    private final String userId;
    private String description;
    private final String id;
    private String name;

    public Portfolio(String id, String userId, String name) {
        this.exchangeManager = ExchangeManager.getInstance();
        this.stockMetas = new ConcurrentHashMap<>();
        this.userId = userId;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInvested() {
        return stockMetas.values().stream().mapToDouble(s -> s.getInvested()).sum();
    }

    public double getCurrent() {
        return stockMetas.values().stream().mapToDouble(s -> s.getCurrent()).sum();
    }

    public double getProfitOrLoss() {
        return getInvested() - getCurrent();
    }

    public List<StockMeta> getStockMetas() {
        return List.copyOf(stockMetas.values());
    }

    public StockMeta getStockMetaByStockId(String stockId) {
        return stockMetas.get(stockId);
    }

    public boolean hasStockMeta(String stockId) {
        return stockMetas.get(stockId) != null;
    }

    public void addStockMeta(StockMeta stockMeta) {
        stockMetas.putIfAbsent(stockMeta.getStockId(), stockMeta);
        exchangeManager.getStockById(stockMeta.getStockId()).subscribe(stockMeta);
    }

    public void updateStockMeta(StockMeta stockMeta) {
        exchangeManager.getStockById(stockMeta.getStockId()).unsubscribe(stockMetas.get(stockMeta.getStockId()));
        stockMetas.put(stockMeta.getStockId(), stockMeta);
        exchangeManager.getStockById(stockMeta.getStockId()).subscribe(stockMeta);
    }

    @Override
    public String toString() {
        return "Portfolio [id=" + id + ", description=" + description + ", name=" + name + ", stockMetas=" + stockMetas
                + ", current= " + getCurrent() + ", invested=" + getInvested() + ", profitOrLoss=" + getProfitOrLoss()
                + "]";
    }

}
