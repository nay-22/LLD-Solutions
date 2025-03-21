package Easy.StackOverflow.src.managers;

import Easy.StackOverflow.src.domain.Action;
import Easy.StackOverflow.src.exceptions.ResourceNotFoundException;

public class ReputationManager {
    private final UserManager userManager;
    private final StrategyManager strategyManager;

    private static class SingletonExtractor {
        private static final ReputationManager INSTANCE = new ReputationManager();
    }

    private ReputationManager() {
        this.userManager = UserManager.getInstance();
        this.strategyManager = StrategyManager.getInstance();
    }

    public static ReputationManager getInstance() {
        return SingletonExtractor.INSTANCE;
    }

    public boolean onVote(String email, Action action) {
        try {
            userManager.getUserByEmail(email)
                    .computeReputation(strategyManager.getReputationStrategy(action));
            return true;
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
