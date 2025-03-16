package src.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
    private final String value;
    private final LogLevel level;
    private final Timestamp loggedAt;

    public Message(String value, LogLevel level) {
        this.value = value;
        this.level = level;
        this.loggedAt = Timestamp.valueOf(LocalDateTime.now());
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
