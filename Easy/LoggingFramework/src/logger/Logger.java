package Easy.LoggingFramework.src.logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Easy.LoggingFramework.src.domain.LoggerConfig;
import Easy.LoggingFramework.src.domain.LogLevel;
import Easy.LoggingFramework.src.domain.Message;
import Easy.LoggingFramework.src.domain.Sink;

public class Logger {
    private final LoggerConfig loggerConfig;
    private final ExecutorService executorService;

    Logger(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void shutdown() {
        executorService.shutdown();
    }

    private void log(String message, LogLevel level) {
        executorService.submit(() -> {
            try {
                for (Sink sink : loggerConfig.getSinks()) {
                    if (level.ordinal() >= sink.getMinLevel().ordinal()) {
                        sink.log(new Message(message, level));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public void warn(String message) {
        log(message, LogLevel.WARNING);
    }

    public void error(String message) {
        log(message, LogLevel.ERROR);
    }

    public void fatal(String message) {
        log(message, LogLevel.FATAL);
    }

    public LoggerConfig getLoggerConfig() {
        return loggerConfig;
    }

    @Override
    public String toString() {
        return "Logger {loggerConfig=" + loggerConfig + "}";
    }

}
