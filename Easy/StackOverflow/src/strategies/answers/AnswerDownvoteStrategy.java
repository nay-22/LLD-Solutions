package Easy.StackOverflow.src.strategies.answers;

import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;

public class AnswerDownvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return currentReputation - 5;
    }

}
