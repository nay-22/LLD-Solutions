package Easy.StackOverflow.src.strategies.questions;

import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;

public class QuestionPostStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return currentReputation + 1;
    }
    
}
