package src.domain.repoPushNotifications;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Deque;
import java.util.Set;

import src.Util;
import src.domain.interfaces.Observer;
import src.domain.interfaces.Topic;

public class RepoTopic implements Topic<RepoMessage> {

    private static final int DEFAULT_HISTORY_TRACE_SIZE = 20;

    private final Set<Observer<RepoMessage>> observers;
    private final ExecutorService executorService;
    private final Deque<RepoMessage> history;
    private final String repoName;
    private RepoMessage curr;

    public RepoTopic(String repoName) {
        this.executorService = Executors.newCachedThreadPool();
        this.observers = ConcurrentHashMap.newKeySet();
        this.history = new ConcurrentLinkedDeque<>();
        this.repoName = repoName;
    }

    public Set<Observer<RepoMessage>> getObservers() {
        return observers;
    }

    private void notifySubscribers() {
        executorService.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Observer<RepoMessage> observer : observers) {
                observer.onNotify(curr);
            }
        });
    }

    @Override
    public String getName() {
        return repoName;
    }

    @Override
    public void subscribe(Observer<RepoMessage> o) {
        observers.add(o);
    }

    @Override
    public void unsubscribe(Observer<RepoMessage> o) {
        if (observers.contains(o)) {
            observers.remove(o);
        }
    }

    @Override
    public void setState(RepoMessage val) {
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
