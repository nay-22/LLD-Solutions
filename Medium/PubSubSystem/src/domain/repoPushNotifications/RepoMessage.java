package Medium.PubSubSystem.src.domain.repoPushNotifications;

public class RepoMessage {
    private final String repoName;
    private final String username;
    private final String commit;

    public RepoMessage(String repoName, String username, String commit) {
        this.repoName = repoName;
        this.username = username;
        this.commit = commit;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getUsername() {
        return username;
    }

    public String getCommit() {
        return commit;
    }

    @Override
    public String toString() {
        return "RepoMessage {repoName=" + repoName + ", username=" + username + ", commit=" + commit + "}";
    }

}
