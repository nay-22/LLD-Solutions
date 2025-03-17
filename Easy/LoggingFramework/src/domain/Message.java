package Easy.LoggingFramework.src.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
    private final String meta;
    private final String value;
    private final LogLevel level;
    private final Timestamp loggedAt;

    public Message(String value, LogLevel level) {
        this(value, null, level);
    }

    public Message(String value, String meta, LogLevel level) {
        this.meta = meta;
        this.value = value;
        this.level = level;
        this.loggedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getMeta() {
        return meta;
    }

    public String getValue() {
        return value;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Timestamp getLoggedAt() {
        return loggedAt;
    }

    public String getMessage() {
        return "[" + level + "] " + value + " " + loggedAt;
    }

    @Override
    public String toString() {
        return "Message {value=" + value + ", level=" + level + ", loggedAt=" + loggedAt + "}";
    }

}
