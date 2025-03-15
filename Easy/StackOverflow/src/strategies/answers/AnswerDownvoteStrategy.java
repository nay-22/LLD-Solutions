package src.strategies.answers;

import src.strategies.interfaces.ReputationComputeStrategy;

public class AnswerDownvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return currentReputation - 5;
    }

}
