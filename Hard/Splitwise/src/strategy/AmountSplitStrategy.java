package Hard.Splitwise.src.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.Outstanding;
import Hard.Splitwise.src.domain.Split;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;
import Hard.Splitwise.src.strategy.interfaces.SplitStrategy;

public class AmountSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Outstanding> getOutstandings(double amount, List<Split> splits)
            throws InvalidSplitsCombinationException {
        double calculated = 0;
        Map<String, Outstanding> map = new HashMap<>();
        for (Split split : splits) {
            calculated += split.getShare();
            map.put(split.getUserEmail(), new Outstanding(split.getExpenseId(), split.getUserEmail(), split.getShare()));
        }
        if (calculated != amount) {
            throw new InvalidSplitsCombinationException(
                    "The splits combined share value don't add up to the specified amount.");
        }
        return map;
    }

    @Override
    public boolean isSettlementValid(double amount, double settlement, Split split) {
        return settlement >= 0 && settlement <= split.getShare();
    }

    @Override
    public double getOutstandingRemainder(double amount, double settlement, Split split) {
        if (isSettlementValid(amount, settlement, split)) {
            return split.getShare() - settlement;
        }
        return -1;
    }

}
