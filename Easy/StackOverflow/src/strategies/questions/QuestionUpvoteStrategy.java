package Easy.StackOverflow.src.strategies.questions;

import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;

public class QuestionUpvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return 5 + currentReputation;
    }

}
