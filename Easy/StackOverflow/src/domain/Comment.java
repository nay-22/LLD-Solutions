package Easy.StackOverflow.src.domain;

public class Comment extends Post {

    public Comment(String value, String postedBy) {
        super(value, postedBy);
    }

    @Override
    public String toString() {
        return "COMMENT{postedBy: " + postedBy + ", modifiedAt: " + modifiedAt + ", createdAt: " + createdAt
                + ", value: "
                + value + "}";
    }

}
