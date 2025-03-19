package Hard.Splitwise.src.domain.interfaces;

public interface Observer<T> {
    void onMessage(T message);
}
