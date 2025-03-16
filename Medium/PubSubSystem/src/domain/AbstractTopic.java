package Medium.PubSubSystem.src.domain;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Deque;
import java.util.Set;

import Medium.PubSubSystem.src.domain.interfaces.Observer;
import Medium.PubSubSystem.src.domain.interfaces.Topic;
import Medium.PubSubSystem.src.Util;

public abstract class AbstractTopic<T> implements Topic<T> {
    private static final int DEFAULT_HISTORY_TRACE_SIZE = 20;

    private final Set<Observer<T>> observers;
    private final ExecutorService executorService;
    private final Deque<T> history;
    private final String repoName;
    private T curr;

    public AbstractTopic(String repoName) {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.observers = ConcurrentHashMap.newKeySet();
        this.history = new ConcurrentLinkedDeque<>();
        this.repoName = repoName;
    }

    public Set<Observer<T>> getObservers() {
        return observers;
    }

    private void notifySubscribers() {
        executorService.submit(() -> {
            for (Observer<T> observer : observers) {
                observer.onNotify(curr);
            }
        });
    }

    @Override
    public String getName() {
        return repoName;
    }

    @Override
    public void subscribe(Observer<T> o) {
        observers.add(o);
    }

    @Override
    public void unsubscribe(Observer<T> o) {
        if (observers.contains(o)) {
            observers.remove(o);
        }
    }

    @Override
    public void setState(T val) {
        history.offer(val);
        if (history.size() > DEFAULT_HISTORY_TRACE_SIZE) {
            history.poll();
        }
        curr = val;
        Util.benchmark("Notify", () -> notifySubscribers());
    }

    @Override
    public String toString() {
        return "RepoTopic {observers=" + observers + ", history=" + history + ", repoName=" + repoName + ", curr="
                + curr + "}";
    }
}
