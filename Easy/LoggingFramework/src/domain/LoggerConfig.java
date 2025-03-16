package src.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoggerConfig {
    private final String identifier;
    private final Set<Sink> sinks;

    public LoggerConfig(String identifier, List<Sink> sinks) {
        this.identifier = identifier;
        this.sinks = Collections.unmodifiableSet(new HashSet<>(sinks));
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<Sink> getSinks() {
        return sinks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + ((sinks == null) ? 0 : sinks.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoggerConfig other = (LoggerConfig) obj;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        if (sinks == null) {
            if (other.sinks != null)
                return false;
        } else if (!sinks.equals(other.sinks))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LoggerConfig {identifier=" + identifier + ", sinks=" + sinks + "}";
    }

}
