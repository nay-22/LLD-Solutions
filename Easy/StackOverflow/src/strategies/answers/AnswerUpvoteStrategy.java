package src.strategies.answers;

import src.strategies.interfaces.ReputationComputeStrategy;

public class AnswerUpvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return 13 + currentReputation;
    }

}
