package Hard.Splitwise.src.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Hard.Splitwise.src.domain.Outstanding;
import Hard.Splitwise.src.domain.Split;
import Hard.Splitwise.src.strategy.interfaces.SplitStrategy;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Outstanding> getOutstandings(double amount, List<Split> splits) {
        Map<String, Outstanding> map = new HashMap<>();
        for (Split split : splits) {
            map.put(split.getUserEmail(),
                    new Outstanding(split.getExpenseId(), split.getUserEmail(), amount / splits.size()));
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
