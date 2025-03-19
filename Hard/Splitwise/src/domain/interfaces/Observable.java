package Hard.Splitwise.src.domain.interfaces;

public interface Observable<T> {
    void subscribe(Observer<T> observer);

    void unsubscribe(Observer<T> observer);

    void publish();
}
