package Medium.PubSubSystem.src.domain;

import java.util.concurrent.PriorityBlockingQueue;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Queue;

import Easy.LoggingFramework.src.domain.LogLevel;
import Easy.LoggingFramework.src.domain.Message;
import Easy.LoggingFramework.src.domain.Sink;

public class OrderedFileSink implements Sink {
    private LogLevel minLevel = LogLevel.INFO;
    private final Queue<Message> queue;
    private final Thread writerThread;
    private volatile boolean running = true;
    private static final String LOG_FILE_DIR = "C:\\Users\\Nayan\\Desktop\\LLDV2\\Medium\\PubSubSystem";

    public OrderedFileSink() {
        this.queue = new PriorityBlockingQueue<>(50,
                (a, b) -> Long.compare(a.getLoggedAt().getTime(), b.getLoggedAt().getTime()));

        this.writerThread = new Thread(() -> {
            while (running || !queue.isEmpty()) {
                Message message = queue.poll();
                String path = LOG_FILE_DIR + "\\commons.txt";
                if (message != null) {
                    if (message.getMeta() != null) {
                        path = LOG_FILE_DIR + "\\" + message.getMeta() + ".txt";
                    }
                }
                try (FileWriter writer = new FileWriter(path, true)) {
                    if (message != null) {
                        writer.write(message.getMessage() + "\n");
                        writer.flush();
                    } else {
                        Thread.sleep(50);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
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
