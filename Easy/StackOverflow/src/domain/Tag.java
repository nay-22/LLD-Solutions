package src.domain;

import src.exceptions.InvalidResourceException;

public class Tag {
    private String value;

    public Tag(String value) throws InvalidResourceException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidResourceException("Tag value cannot be null or empty");
        }
        this.value = Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TAG{value: " + value + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Tag other = (Tag) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

}
