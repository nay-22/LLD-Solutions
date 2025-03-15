package src.managers;

import src.domain.Action;
import src.strategies.answers.AnswerDownvoteStrategy;
import src.strategies.answers.AnswerPostStrategy;
import src.strategies.answers.AnswerUpvoteStrategy;
import src.strategies.interfaces.ReputationComputeStrategy;
import src.strategies.questions.QuestionPostStrategy;
import src.strategies.questions.QuestionUpvoteStrategy;

public class StrategyManager {
    private static class SingletonExtractor {
        private static final StrategyManager INSTANCE = new StrategyManager();
    }

    private StrategyManager() {

    }

    public static StrategyManager getInstance() {
        return SingletonExtractor.INSTANCE;
    }

    public ReputationComputeStrategy getReputationStrategy(Action action) {
        return switch (action) {
            case ANSWER_DOWNVOTE -> new AnswerDownvoteStrategy();
            case ANSWER_POST -> new AnswerPostStrategy();
            case ANSWER_UPVOTE -> new AnswerUpvoteStrategy();
            case QUESTION_POST -> new QuestionPostStrategy();
            case QUESTION_UPVOTE -> new QuestionUpvoteStrategy();
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        };
    }
}
