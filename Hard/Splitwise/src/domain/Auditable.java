package Hard.Splitwise.src.domain;

import java.time.LocalDateTime;

public class Auditable implements Comparable<Auditable> {
    protected final LocalDateTime createdAt;
    protected final String createdBy;
    protected LocalDateTime lastModifiedAt;
    protected String lastModifiedBy;

    protected Auditable(String createdBy) {
        LocalDateTime now = LocalDateTime.now();
        this.lastModifiedBy = createdBy;
        this.createdBy = createdBy;
        this.lastModifiedAt = now;
        this.createdAt = now;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public int compareTo(Auditable o) {
        return this.createdAt.compareTo(o.createdAt);
    }

    @Override
    public String toString() {
        return "Auditable [createdAt=" + createdAt + ", createdBy=" + createdBy + ", lastModifiedAt=" + lastModifiedAt
                + ", lastModifiedBy=" + lastModifiedBy + "]";
    }

}
