package Easy.StackOverflow.src.strategies.answers;

import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;

public class AnswerUpvoteStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return 13 + currentReputation;
    }

}