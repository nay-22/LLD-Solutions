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

    private void log(String message, String meta, LogLevel level) {
        executorService.submit(() -> {
            try {
                for (Sink sink : loggerConfig.getSinks()) {
                    if (level.ordinal() >= sink.getMinLevel().ordinal()) {
                        sink.log(new Message(message, meta, level));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void info(String message, String meta) {
        log(message, meta, LogLevel.INFO);
    }

    public void debug(String message, String meta) {
        log(message, meta, LogLevel.DEBUG);
    }

    public void warn(String message, String meta) {
        log(message, meta, LogLevel.WARNING);
    }

    public void error(String message, String meta) {
        log(message, meta, LogLevel.ERROR);
    }

    public void fatal(String message, String meta) {
        log(message, meta, LogLevel.FATAL);
    }

    private void log(String message, LogLevel level) {
        executorService.submit(() -> {
            try {
                for (Sink sink : loggerConfig.getSinks()) {
                    if (level.ordinal() >= sink.getMinLevel().ordinal()) {
                        sink.log(new Message(message, loggerConfig.getMeta(), level));
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

    public void info(Object message) {
        log(message.toString(), LogLevel.INFO);
    }

    public void debug(Object message) {
        log(message.toString(), LogLevel.INFO);
    }

    public void warn(Object message) {
        log(message.toString(), LogLevel.INFO);
    }

    public void error(Object message) {
        log(message.toString(), LogLevel.INFO);
    }

    public void fatal(Object message) {
        log(message.toString(), LogLevel.INFO);
    }

    @Override
    public String toString() {
        return "Logger {loggerConfig=" + loggerConfig + "}";
    }

}
