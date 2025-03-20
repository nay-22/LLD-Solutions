package Hard.StockBroker.src.domain;

import Hard.StockBroker.src.domain.interfaces.Observer;

public class StockMeta implements Observer<Stock> {
    private final String stockId;
    private double quantity;
    private double averageOrderPrice;
    private double marketPrice;

    public StockMeta(String stockId, double quantity, double averageOrderPrice, double marketPrice) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.averageOrderPrice = averageOrderPrice;
        this.marketPrice = marketPrice;
    }

    @Override
    public void onNotify(Stock stock) {
        marketPrice = stock.getPrice();
        System.out.println(this);
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
