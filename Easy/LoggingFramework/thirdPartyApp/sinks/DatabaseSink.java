package Easy.LoggingFramework.thirdPartyApp.sinks;

import Easy.LoggingFramework.src.domain.LogLevel;
import Easy.LoggingFramework.src.domain.Message;
import Easy.LoggingFramework.src.domain.Sink;

public class DatabaseSink implements Sink {

    private final LogLevel minLevel;

    public DatabaseSink() {
        this.minLevel = LogLevel.INFO;
    }

    @Override
    public void log(Message message) {
        try {
            Thread.sleep(6000);
            System.out.println(
                    "DBSink: [" + message.getLevel() + "] " + message.getValue() + " " + message.getLoggedAt());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LogLevel getMinLevel() {
        return minLevel;
    }

    @Override
    public String toString() {
        return "DatabaseSink {minLevel=" + minLevel + "}";
    }

}
