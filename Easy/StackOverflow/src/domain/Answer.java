package src.domain;

import src.domain.interfaces.Votable;
import src.managers.ReputationManager;

public class Answer extends Post implements Votable {

    private final ReputationManager reputationManager;
    private int votes;

    public Answer(String value, String postedBy) {
        super(value, postedBy);
        this.reputationManager = ReputationManager.getInstance();
        reputationManager.onVote(postedBy, Action.ANSWER_POST);
        this.votes = 0;
    }

    @Override
    public int getVotes() {
        return votes;
    }

    @Override
    public void upvote() {
        votes += 1;
        reputationManager.onVote(postedBy, Action.ANSWER_UPVOTE);
    }

    @Override
    public void downvote() {
        votes -= 1;
        reputationManager.onVote(postedBy, Action.ANSWER_DOWNVOTE);
    }

    @Override
    public String toString() {
        return "ANSWER{postedBy: " + postedBy + ", modifiedAt: " + modifiedAt + ", createdAt: " + createdAt
                + ", value: "
                + value + ", votes: " + votes + "}";
    }

}
