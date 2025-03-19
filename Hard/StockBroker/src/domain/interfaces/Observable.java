package Hard.StockBroker.src.domain.interfaces;

public interface Observable<T> {
    void subscribe(Observer<T> o);

    void unsubscribe(Observer<T> o);

    void publish();
}
