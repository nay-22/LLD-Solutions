package Hard.StockBroker.src.domain;

import Easy.LoggingFramework.src.logger.Logger;
import Easy.LoggingFramework.src.logger.LoggerFactory;
import Hard.StockBroker.src.config.Config;
import Hard.StockBroker.src.domain.interfaces.Observer;

public class StockMeta implements Observer<Stock> {
    private final Logger logger;;
    private final String stockId;
    private double quantity;
    private double averageOrderPrice;
    private double marketPrice;

    public StockMeta(String stockId, double quantity, double averageOrderPrice, double marketPrice) {
        this.logger = LoggerFactory.getInstance().getLogger(Config.loggerConfig);
        this.averageOrderPrice = averageOrderPrice;
        this.marketPrice = marketPrice;
        this.quantity = quantity;
        this.stockId = stockId;
    }

    @Override
    public void onNotify(Stock stock) {
        marketPrice = stock.getPrice();
        logger.info("\n" + this + "\n");
    }

    public String getStockId() {
        return stockId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getInvested() {
        return averageOrderPrice * quantity;
    }

    public double getCurrent() {
        return marketPrice * quantity;
    }

    public double getTotalProfitOrLossPerShare() {
        return marketPrice - averageOrderPrice;
    }

    public double getTotalProfitOrLoss() {
        return (marketPrice - averageOrderPrice) * quantity;
    }

    @Override
    public String toString() {
        return "StockMeta {\n\tstockId: " + stockId
                + ",\n\tquantity: " + quantity
                + ",\n\taverageOrderPrice: $" + averageOrderPrice
                + ",\n\tmarketPrice: $" + marketPrice
                + ",\n\tprofitOrLossPerShare: $" + getTotalProfitOrLossPerShare()
                + ",\n\tcurrent: $" + getCurrent()
                + ",\n\tinvested: $" + getInvested()
                + ",\n\tprofitOrLoss: $" + getTotalProfitOrLoss()
                + ",\n\tprofitOrLossPercent: "
                + String.format("%.2f", (((getCurrent() - getInvested()) / getInvested()) * 100)) + "%"
                + "\n}";
    }

    public double getAverageOrderPrice() {
        return averageOrderPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }
}
