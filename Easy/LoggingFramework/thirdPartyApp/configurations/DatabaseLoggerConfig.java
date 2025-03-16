package thirdPartyApp.configurations;

import java.util.List;

import src.domain.LoggerConfig;
import thirdPartyApp.sinks.DatabaseSink;

public class DatabaseLoggerConfig {
    public static LoggerConfig get() {
        return new LoggerConfig("dbLogger", List.of(new DatabaseSink()));
    }
}
