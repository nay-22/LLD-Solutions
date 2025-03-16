package thirdPartyApp.configurations;

import java.util.List;

import src.domain.LoggerConfig;
import thirdPartyApp.sinks.FileSink;

public class FileLoggerConfig {
    public static LoggerConfig get() {
        return new LoggerConfig("fileLogger", List.of(new FileSink()));
    }
}
