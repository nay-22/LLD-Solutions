package src.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class OrderedFileSink implements Sink {
    private LogLevel minLevel = LogLevel.INFO;
    private final Queue<Message> queue;
    private final Thread writerThread;
    private volatile boolean running = true;
    private static final String LOG_FILE_PATH = "C:\\Users\\Nayan\\Desktop\\LLD\\Medium\\PubSubSystem\\logs.txt";

    public OrderedFileSink() {
        this.queue = new PriorityBlockingQueue<>(50,
                (a, b) -> Long.compare(a.getLoggedAt().getTime(), b.getLoggedAt().getTime()));

        this.writerThread = new Thread(() -> {
            try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
                while (running || !queue.isEmpty()) {
                    Message message = queue.poll();
                    if (message != null) {
                        writer.write(message.getMessage() + "\n");
                        writer.flush();
                    } else {
                        Thread.sleep(50);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        writerThread.setDaemon(true);
        writerThread.start();
    }

    @Override
    public void log(Message message) {
        queue.offer(message);
    }

    @Override
    public LogLevel getMinLevel() {
        return minLevel;
    }

    public void shutdown() {
        running = false;
        writerThread.interrupt();
        try {
            writerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
