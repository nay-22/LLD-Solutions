package src.domain;

public interface Sink {

    public void log(Message message);

    public LogLevel getMinLevel();
}
