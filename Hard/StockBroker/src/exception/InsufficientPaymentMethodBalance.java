package Hard.StockBroker.src.exception;

public class InsufficientPaymentMethodBalance extends Exception {
    public InsufficientPaymentMethodBalance(String message) {
        super(message);
    }
}
