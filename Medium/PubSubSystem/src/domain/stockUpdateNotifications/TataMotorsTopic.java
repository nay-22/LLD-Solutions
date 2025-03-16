package Medium.PubSubSystem.src.domain.stockUpdateNotifications;

import Medium.PubSubSystem.src.domain.AbstractTopic;

public class TataMotorsTopic extends AbstractTopic<StockMessage> {

    public TataMotorsTopic(String repoName) {
        super(repoName);
    }

}
