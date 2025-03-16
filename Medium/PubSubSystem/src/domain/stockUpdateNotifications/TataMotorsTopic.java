package src.domain.stockUpdateNotifications;

import src.domain.interfaces.AbstractTopic;

public class TataMotorsTopic extends AbstractTopic<StockMessage> {

    public TataMotorsTopic(String repoName) {
        super(repoName);
    }

}
