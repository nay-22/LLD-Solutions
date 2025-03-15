package src.domain;

import java.util.Date;
import java.util.List;

public class Post {
    protected final String postedBy;
    protected Date modifiedAt;
    protected Date createdAt;
    protected String value;

    public Post(String value, String postedBy) {
        this.postedBy = postedBy;
        this.value = value;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    protected Post() {
        this.postedBy = "";

    }

    public Post(String value, String postedBy, List<Tag> tags) {
        this(value, postedBy);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.modifiedAt = new Date();
    }

    public String getOwnerId() {
        return postedBy;
    }

    @Override
    public String toString() {
        return "POST {postedBy: " + postedBy + ", modifiedAt: " + modifiedAt + ", createdAt: " + createdAt + ", value: "
                + value + "}";
    }

}
