package Hard.Splitwise.src.strategy.interfaces;

import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.Outstanding;
import Hard.Splitwise.src.domain.Split;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;

public interface SplitStrategy {
    Map<String, Outstanding> getOutstandings(double amount, List<Split> splits)
            throws InvalidSplitsCombinationException;

    boolean isSettlementValid(double amount, double settlement, Split split);

    double getOutstandingRemainder(double amount, double settlement, Split split);
}
