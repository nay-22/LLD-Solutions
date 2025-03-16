package Easy.LoggingFramework.thirdPartyApp.configurations;

import java.util.List;

import Easy.LoggingFramework.thirdPartyApp.sinks.DatabaseSink;
import Easy.LoggingFramework.src.domain.LoggerConfig;

public class DatabaseLoggerConfig {
    public static LoggerConfig get() {
        return new LoggerConfig("dbLogger", List.of(new DatabaseSink()));
    }
}
