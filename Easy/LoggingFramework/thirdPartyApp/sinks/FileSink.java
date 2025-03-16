package Easy.LoggingFramework.thirdPartyApp.sinks;

import Easy.LoggingFramework.src.domain.LogLevel;
import Easy.LoggingFramework.src.domain.Message;
import Easy.LoggingFramework.src.domain.Sink;

public class FileSink implements Sink {

    private final LogLevel minLevel;

    public FileSink() {
        this.minLevel = LogLevel.INFO;
    }

    @Override
    public void log(Message message) {
        try {
            Thread.sleep(3000);
            System.out.println(
                    "FileSink: [" + message.getLevel() + "] " + message.getValue() + " " + message.getLoggedAt());
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
        return "FileSink {minLevel=" + minLevel + "}";
    }

}
