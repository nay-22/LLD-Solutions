package src.managers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import src.logger.LoggerFactory;
import src.domain.LoggerConfig;
import src.domain.OrderedFileSink;
import src.domain.Sink;
import src.logger.Logger;
import src.domain.interfaces.Topic;

public class NotificationManager {

    private final Map<String, Topic<?>> topics;
    private final Logger logger;

    private static class Singleton {
        private static final NotificationManager INSTANCE = new NotificationManager();
    }

    private NotificationManager() {
        LoggerFactory loggerFactory = LoggerFactory.getInstance();
        Sink consoleSink = new OrderedFileSink();
        LoggerConfig config = new LoggerConfig("pub-sub-system", List.of(consoleSink));
        this.logger = loggerFactory.getLogger(config);
        topics = new ConcurrentHashMap<>();
    }

    public static NotificationManager getInstance() {
        return Singleton.INSTANCE;
    }

    public <T> void addTopic(Topic<T> topic) {
        logger.info("Attempting to add topic: " + topic.getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        topics.computeIfAbsent(topic.getName(), _ -> topic);
        logger.info("Successfully added topic: " + topic.getName());
    }

    public Topic<?> removeTopic(String name) {
        logger.info("Attempting to remove topic: " + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Topic<?> topic = topics.computeIfPresent(name, null);
        logger.info("Successfully removed topic: " + name);
        return topic;
    }

    public Topic<?> getTopic(String name) {
        logger.info("Attempting to retrieve topic: " + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Topic<?> topic = topics.get(name);
        logger.info("Successfully retrieved topic: " + name);
        return topic;
    }

    public List<Topic<?>> getAllTopics() {
        logger.info("Attempting to retrieve all topics");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Topic<?>> list = List.copyOf(topics.values());
        logger.info("Successfully retrieved all topics");
        return list;
    }

    public <T> boolean publish(String name, T val) {
        logger.info("Attempting to publish value:" + val + " to topic: " + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Topic<?> topic = getTopic(name);
        if (topic == null) {
            return false;
        }
        try {
            @SuppressWarnings("unchecked")
            Topic<T> castTopic = (Topic<T>) topic;
            castTopic.setState(val);
            logger.info("Successfully published value:" + val + " to topic: " + name);
            return true;
        } catch (ClassCastException e) {
            logger.error("Failed to publish value:" + val + " to topic: " + name);
            e.printStackTrace();
            return false;
        }
    }

    public void shutdown() {
        logger.shutdown();
    }

}
