package Easy.LoggingFramework.src.logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import Easy.LoggingFramework.src.domain.LoggerConfig;

public class LoggerFactory {

    private final Map<LoggerConfig, Logger> loggers;

    private static class Singleton {
        private static final LoggerFactory FACTORY = new LoggerFactory();
    }

    private LoggerFactory() {
        this.loggers = new ConcurrentHashMap<>();
    }

    public static LoggerFactory getInstance() {
        return Singleton.FACTORY;
    }

    public Logger getLogger(LoggerConfig loggerConfig) {
        return loggers.computeIfAbsent(loggerConfig, _ -> new Logger(loggerConfig));
    }
}
