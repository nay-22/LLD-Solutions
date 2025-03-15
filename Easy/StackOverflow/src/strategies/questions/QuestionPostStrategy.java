package src.strategies.questions;

import src.strategies.interfaces.ReputationComputeStrategy;

public class QuestionPostStrategy implements ReputationComputeStrategy {

    @Override
    public int computeReputation(int currentReputation) {
        return currentReputation + 1;
    }
    
}
