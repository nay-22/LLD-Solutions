package Hard.StockBroker.src.domain.interfaces;

public interface Observer<T> {
    void onNotify(T val);
}
