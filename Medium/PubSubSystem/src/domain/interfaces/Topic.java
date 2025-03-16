package Medium.PubSubSystem.src.domain.interfaces;

public interface Topic<T> {

    String getName();

    void subscribe(Observer<T> o);

    void unsubscribe(Observer<T> o);

    void setState(T val);
}
