package src.domain;

import java.util.Date;

public class Message {
    private final String value;
    private final LogLevel level;
    private final Date loggedAt;

    public Message(String value, LogLevel level) {
        this.value = value;
        this.level = level;
        this.loggedAt = new Date();
    }

    public String getValue() {
        return value;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Date getLoggedAt() {
        return loggedAt;
    }

    @Override
    public String toString() {
        return "Message {value=" + value + ", level=" + level + ", loggedAt=" + loggedAt + "}";
    }
}
