package Easy.LoggingFramework.thirdPartyApp.configurations;

import java.util.List;

import Easy.LoggingFramework.thirdPartyApp.sinks.FileSink;
import Easy.LoggingFramework.src.domain.LoggerConfig;

public class FileLoggerConfig {
    public static LoggerConfig get() {
        return new LoggerConfig("fileLogger", List.of(new FileSink()));
    }
}
