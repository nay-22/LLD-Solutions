package Hard.Splitwise.src.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.Outstanding;
import Hard.Splitwise.src.domain.Split;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;
import Hard.Splitwise.src.strategy.interfaces.SplitStrategy;

public class PercentSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Outstanding> getOutstandings(double amount, List<Split> splits)
            throws InvalidSplitsCombinationException {
        Map<String, Outstanding> map = new HashMap<>();
        double calculated = 0;
        for (Split split : splits) {
            double val = (split.getShare() / 100) * amount;
            calculated += val;
            map.put(split.getUserEmail(), new Outstanding(split.getExpenseId(), split.getUserEmail(), val));
        }
        if (calculated != amount)
            throw new InvalidSplitsCombinationException(
                    "The splits combined share value don't add up to the specified amount.");
        return map;
    }

    @Override
    public boolean isSettlementValid(double amount, double settlement, Split split) {
        return settlement >= 0 && settlement <= (split.getShare() / 100) * amount;
    }

    @Override
    public double getOutstandingRemainder(double amount, double settlement, Split split) {
        if (isSettlementValid(amount, settlement, split)) {
            return (split.getShare() / 100) * amount - settlement;
        }
        return -1;
    }

}
