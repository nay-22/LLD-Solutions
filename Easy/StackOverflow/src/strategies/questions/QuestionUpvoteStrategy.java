package src.strategies.questions;

import src.strategies.interfaces.ReputationComputeStrategy;

public class QuestionUpvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return 5 + currentReputation;
    }

}
