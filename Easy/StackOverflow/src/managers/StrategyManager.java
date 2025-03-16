package Easy.StackOverflow.src.managers;

import java.util.EnumMap;
import java.util.Map;

import Easy.StackOverflow.src.domain.Action;
import Easy.StackOverflow.src.strategies.answers.AnswerDownvoteStrategy;
import Easy.StackOverflow.src.strategies.answers.AnswerPostStrategy;
import Easy.StackOverflow.src.strategies.answers.AnswerUpvoteStrategy;
import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;
import Easy.StackOverflow.src.strategies.questions.QuestionPostStrategy;
import Easy.StackOverflow.src.strategies.questions.QuestionUpvoteStrategy;

public class StrategyManager {
    private final Map<Action, ReputationComputeStrategy> strategies = new EnumMap<>(Action.class);

    private static class SingletonExtractor {
        private static final StrategyManager INSTANCE = new StrategyManager();
    }

    private StrategyManager() {
        strategies.put(Action.ANSWER_DOWNVOTE, new AnswerDownvoteStrategy());
        strategies.put(Action.ANSWER_POST, new AnswerPostStrategy());
        strategies.put(Action.ANSWER_UPVOTE, new AnswerUpvoteStrategy());
        strategies.put(Action.QUESTION_POST, new QuestionPostStrategy());
        strategies.put(Action.QUESTION_UPVOTE, new QuestionUpvoteStrategy());
    }

    public static StrategyManager getInstance() {
        return SingletonExtractor.INSTANCE;
    }

    public ReputationComputeStrategy getReputationStrategy(Action action) {
        return strategies.getOrDefault(action, rep -> rep);
    }
}
