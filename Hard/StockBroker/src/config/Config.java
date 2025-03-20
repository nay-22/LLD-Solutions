package Hard.StockBroker.src.config;

import java.util.List;

import Easy.LoggingFramework.src.domain.LoggerConfig;

import Hard.StockBroker.src.config.sink.ConsoleSink;
import Hard.StockBroker.src.config.sink.FileSink;

public class Config {
    public static LoggerConfig loggerConfig = new LoggerConfig("stock-logger",
            List.of(new FileSink(), new ConsoleSink()));
}
