package src.managers.interfaces;

import src.domain.Action;

public interface VoteObserver {
    boolean onVote(String email, Action action);
}
