package Hard.StockBroker.src.config.sink;

import Easy.LoggingFramework.src.domain.LogLevel;
import Easy.LoggingFramework.src.domain.Message;
import Easy.LoggingFramework.src.domain.Sink;

public class ConsoleSink implements Sink {

    private LogLevel minLevel = LogLevel.INFO;

    @Override
    public void log(Message message) {
        System.out.println(message.getMessage());
    }

    @Override
    public LogLevel getMinLevel() {
        return minLevel;
    }

}
