package Hard.StockBroker;

import Easy.LoggingFramework.src.logger.Logger;
import Easy.LoggingFramework.src.logger.LoggerFactory;
import Easy.StackOverflow.src.exceptions.ResourceNotFoundException;
import Hard.StockBroker.src.config.Config;
import Hard.StockBroker.src.domain.Order;
import Hard.StockBroker.src.domain.OrderStatus;
import Hard.StockBroker.src.domain.OrderType;
import Hard.StockBroker.src.domain.Portfolio;
import Hard.StockBroker.src.domain.Stock;
import Hard.StockBroker.src.domain.User;
import Hard.StockBroker.src.exception.InsufficientPaymentMethodBalance;
import Hard.StockBroker.src.exception.InsufficientWalletBalance;
import Hard.StockBroker.src.exception.InvalidOperationException;
import Hard.StockBroker.src.manager.AccountManager;
import Hard.StockBroker.src.manager.ExchangeManager;
import Hard.StockBroker.src.manager.OrderManager;
import Hard.StockBroker.src.manager.PortfolioManager;

public class StockBroker {
    private static final Logger logger = LoggerFactory.getInstance().getLogger(Config.loggerConfig);

    public static void main(String[] args)
            throws InvalidOperationException, ResourceNotFoundException,
            InsufficientPaymentMethodBalance, InsufficientWalletBalance,
            Hard.StockBroker.src.exception.ResourceNotFoundException, InterruptedException {
        PortfolioManager portfolioManager = PortfolioManager.getInstance();
        ExchangeManager exchangeManager = ExchangeManager.getInstance();
        AccountManager accountManager = AccountManager.getInstance();
        OrderManager orderManager = OrderManager.getInstance();

        User nay = new User("nayan@gmail.com", "nay22", "Nayan", null, null);
        accountManager.register(nay);
        accountManager.addBalance(nay.getId(), "payment-1-nay22", 10000);
        Portfolio portfolio = portfolioManager.createPortfolio("nay22", "Tech");

        logger.info("Nayan User Info:\n" + nay + "\n");
        Thread.sleep(100);

        logger.info("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()) + "\n");
        Thread.sleep(100);

        Stock apple = new Stock("apple", "Apple");
        apple.setPrice(223.45);
        apple.setAvailableQty(200);
        exchangeManager.register(apple);

        logger.info("Stocks in ExchangeManager:\n" + exchangeManager.getStocks() + "\n");
        Thread.sleep(100);

        logger.info("SIMULATING BUY ORDER:\n");
        Thread.sleep(100);

        Order appleBuyRequest = Order.builder()
                .stockId(apple.getId())
                .userId(nay.getId())
                .portfolioId(portfolio.getId())
                .quantity(10)
                .orderPrice(210.75)
                .orderType(OrderType.BUY)
                .build();
        orderManager.createOrder(appleBuyRequest);

        logger.info(
                "Orders (before BUY exec) created by Nayan:\n" + orderManager.getOrdersByUserId(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("Apple Stock Observers before execution:\n"
                + exchangeManager.getStockById(apple.getId()).getObservers() + "\n");
        Thread.sleep(100);

        logger.info("Decreasing stock price using a loop, Observer(Order) logs the side effect:");
        Thread.sleep(100);

        while (orderManager.getOrderById(nay.getId(), appleBuyRequest.getId()).getStatus() == OrderStatus.ONGOING) {
            try {
                apple.setPrice(apple.getPrice() - 1);
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("Apple Stock Observers after execution: " + "\n");
        Thread.sleep(100);

        logger.info(exchangeManager.getStockById(apple.getId()).getObservers() + "\n");
        Thread.sleep(100);

        logger.info("Orders (after BUY exec) created by Nayan:" + "\n");
        Thread.sleep(100);

        logger.info(orderManager.getOrdersByUserId(nay.getId()));
        Thread.sleep(100);

        logger.info("SIMULATING PORTFOLIO WATCH:\n");
        Thread.sleep(100);

        while (true) {
            try {
                if (apple.getPrice() > 220) {
                    break;
                }
                apple.setPrice(apple.getPrice() + 1);
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread.sleep(100);

        logger.info("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()) + "\n");
        Thread.sleep(100);

        logger.info("SIMULATING SELL ORDER:\n");
        Thread.sleep(100);

        Order appleSellRequest = Order.builder()
                .stockId(apple.getId())
                .userId(nay.getId())
                .portfolioId(portfolio.getId())
                .quantity(5)
                .orderPrice(225.35)
                .orderType(OrderType.SELL)
                .build();
        orderManager.createOrder(appleSellRequest);

        logger.info("Orders (before SELL exec) created by Nayan:" + "\n");
        Thread.sleep(100);

        logger.info(orderManager.getOrdersByUserId(nay.getId()));
        Thread.sleep(100);

        logger.info("Apple Stock Observers before execution: " + "\n");
        Thread.sleep(100);

        logger.info(exchangeManager.getStockById(apple.getId()).getObservers() + "\n");
        Thread.sleep(100);

        logger.info("Increasing stock price using a loop, Observer(Order, StockMeta) logs the side effect:");
        Thread.sleep(100);

        int i = 0;
        while (i < 10) {
            try {
                if (orderManager.getOrderById(nay.getId(), appleSellRequest.getId())
                        .getStatus() == OrderStatus.SUCCESS) {
                    break;
                }
                apple.setPrice(apple.getPrice() + i++);
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("Apple Stock Observers after execution: " + "\n");
        Thread.sleep(100);

        logger.info(exchangeManager.getStockById(apple.getId()).getObservers() + "\n");
        Thread.sleep(100);

        logger.info("Orders (after SELL exec) created by Nayan:" + "\n");
        Thread.sleep(100);

        logger.info(orderManager.getOrdersByUserId(nay.getId()));
        Thread.sleep(100);

        logger.shutdown();

    }
}
