package Hard.StockBroker;

import Easy.StackOverflow.src.exceptions.ResourceNotFoundException;
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
    public static void main(String[] args)
            throws InvalidOperationException, ResourceNotFoundException,
            InsufficientPaymentMethodBalance, InsufficientWalletBalance,
            Hard.StockBroker.src.exception.ResourceNotFoundException {
        PortfolioManager portfolioManager = PortfolioManager.getInstance();
        ExchangeManager exchangeManager = ExchangeManager.getInstance();
        AccountManager accountManager = AccountManager.getInstance();
        OrderManager orderManager = OrderManager.getInstance();

        User nay = new User("nayan@gmail.com", "nay22", "Nayan", null, null);
        accountManager.register(nay);
        accountManager.addBalance(nay.getId(), "payment-1-nay22", 10000);
        Portfolio portfolio = portfolioManager.createPortfolio("nay22", "Tech");

        System.out.println("Nayan User Info:");
        System.out.println(nay);
        System.out.println();

        System.out.println("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()));
        System.out.println("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()));
        System.out.println();

        Stock apple = new Stock("apple", "Apple");
        apple.setPrice(223.45);
        apple.setAvailableQty(200);
        exchangeManager.register(apple);

        System.out.println("Stocks in ExchangeManager:");
        System.out.println(exchangeManager.getStocks());
        System.out.println();

        System.out.println("-".repeat(40) + " SIMULATING BUY ORDER " + "-".repeat(40));

        Order appleBuyRequest = Order.builder()
                .stockId(apple.getId())
                .userId(nay.getId())
                .portfolioId(portfolio.getId())
                .quantity(10)
                .orderPrice(210.75)
                .orderType(OrderType.BUY)
                .build();
        orderManager.createOrder(appleBuyRequest);

        System.out.println("Orders (before BUY exec) created by Nayan:");
        orderManager.getOrdersByUserId(nay.getId()).forEach(System.out::println);
        System.out.println();

        System.out.println("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()));
        System.out.println("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()));
        System.out.println();

        System.out.println("Apple Stock Observers before execution: ");
        System.out.println(exchangeManager.getStockById(apple.getId()).getObservers());
        System.out.println();

        while (orderManager.getOrderById(nay.getId(), appleBuyRequest.getId()).getStatus() == OrderStatus.ONGOING) {
            try {
                System.out.println("-".repeat(80));
                apple.setPrice(apple.getPrice() - 1);
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("Apple Stock Observers after execution: ");
        System.out.println(exchangeManager.getStockById(apple.getId()).getObservers());
        System.out.println();

        System.out.println("Orders (after BUY exec) created by Nayan:");
        orderManager.getOrdersByUserId(nay.getId()).forEach(System.out::println);
        System.out.println();

        System.out.println("-".repeat(40) + " SIMULATING PORTFOLIO WATCH " + "-".repeat(40));

        while (true) {
            try {
                if (apple.getPrice() > 220) {
                    break;
                }
                System.out.println("-".repeat(80));
                apple.setPrice(apple.getPrice() + 1);
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Wallet Blocked: " + accountManager.getWalletBlockAmount(nay.getId()));
        System.out.println("Wallet Balance: " + accountManager.getWalletBalance(nay.getId()));
        System.out.println();

        System.out.println("-".repeat(40) + " SIMULATING SELL ORDER " + "-".repeat(40));

        Order appleSellRequest = Order.builder()
                .stockId(apple.getId())
                .userId(nay.getId())
                .portfolioId(portfolio.getId())
                .quantity(5)
                .orderPrice(225.35)
                .orderType(OrderType.SELL)
                .build();
        orderManager.createOrder(appleSellRequest);

        System.out.println("Orders (before SELL exec) created by Nayan:");
        orderManager.getOrdersByUserId(nay.getId()).forEach(System.out::println);
        System.out.println();

        System.out.println("Apple Stock Observers before execution: ");
        System.out.println(exchangeManager.getStockById(apple.getId()).getObservers());
        System.out.println();

        int i = 0;
        while (i < 10) {
            try {
                if (orderManager.getOrderById(nay.getId(), appleSellRequest.getId())
                        .getStatus() == OrderStatus.SUCCESS) {
                    break;
                }
                System.out.println("-".repeat(80));
                apple.setPrice(apple.getPrice() + i++);
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("Apple Stock Observers after execution: ");
        System.out.println(exchangeManager.getStockById(apple.getId()).getObservers());
        System.out.println();

        System.out.println("Orders (after SELL exec) created by Nayan:");
        orderManager.getOrdersByUserId(nay.getId()).forEach(System.out::println);
        System.out.println();

    }
}
