package Easy.StackOverflow.src.domain;

import Easy.StackOverflow.src.domain.interfaces.Votable;
import Easy.StackOverflow.src.managers.ReputationManager;

public class Question extends Post implements Votable {
    private final ReputationManager reputationManager;
    private int votes;

    public Question(String value, String postedBy) {
        super(value, postedBy);
        this.reputationManager = ReputationManager.getInstance();
        reputationManager.onVote(postedBy, Action.QUESTION_POST);
        this.votes = 0;
    }

    @Override
    public int getVotes() {
        return votes;
    }

    @Override
    public void upvote() {
        votes += 1;
        reputationManager.onVote(postedBy, Action.QUESTION_UPVOTE);
    }

    @Override
    public void downvote() {
        votes -= 1;
    }

    @Override
    public String toString() {
        return "QUESTION{postedBy: " + postedBy + ", modifiedAt: " + modifiedAt + ", createdAt: "
                + createdAt + ", value: " + value + ", votes: " + votes + "}";
    }

}