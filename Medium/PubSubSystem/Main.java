import java.util.List;

import src.domain.interfaces.Observer;
import src.domain.interfaces.Topic;
import src.domain.repoPushNotifications.RepoMessage;
import src.domain.repoPushNotifications.RepoTopic;
import src.domain.repoPushNotifications.UserObserver;
import src.domain.stockUpdateNotifications.StockMessage;
import src.domain.stockUpdateNotifications.TataMotorsTopic;
import src.managers.NotificationManager;

public class Main {
        public static void main(String[] args) {
                initTopics();
                testRepoTopic();
        }

        private static void testTataMotorsTopic() {
                NotificationManager notificationManager = NotificationManager.getInstance();
        }

        private static void testRepoTopic() {
                NotificationManager notificationManager = NotificationManager.getInstance();
                RepoTopic strawHatRepo = (RepoTopic) notificationManager.getTopic("Straw Hats");

                RepoMessage msg1 = new RepoMessage("StrawHatPirates-Repo", "Luffy", "Added a new adventure log.");
                RepoMessage msg2 = new RepoMessage("StrawHatPirates-Repo", "Zoro", "Fixed sword techniques.");
                RepoMessage msg3 = new RepoMessage("StrawHatPirates-Repo", "Nami",
                                "Updated map with new navigation routes.");
                RepoMessage msg4 = new RepoMessage("StrawHatPirates-Repo", "Sanji",
                                "Added new recipes to the crew's menu.");
                RepoMessage msg5 = new RepoMessage("StrawHatPirates-Repo", "Usopp",
                                "Enhanced long-range attack strategies.");

                List<RepoMessage> messages = List.of(msg1, msg2, msg3, msg4, msg5);

                List<Observer<RepoMessage>> crew = init();

                crew.forEach(strawHatRepo::subscribe);

                messages.forEach(m -> notificationManager.publish("Straw Hats", m));

                notificationManager.shutdown();
        }

        private static void initTopics() {
                NotificationManager notificationManager = NotificationManager.getInstance();
                RepoTopic strawHatRepo = new RepoTopic("Straw Hats");
                Topic<StockMessage> tataMotorsTopic = new TataMotorsTopic("Tata Motors");

                notificationManager.addTopic(strawHatRepo);
                try {
                        Thread.sleep(100);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                notificationManager.addTopic(tataMotorsTopic);

        }

        private static List<Observer<RepoMessage>> init() {
                UserObserver luffy = UserObserver.builder()
                                .fname("Luffy")
                                .email("luffy@onepiece.com")
                                .build();
                UserObserver zoro = UserObserver.builder()
                                .fname("Zoro")
                                .email("zoro@onepiece.com")
                                .build();
                UserObserver nami = UserObserver.builder()
                                .fname("Nami")
                                .email("nami@onepiece.com")
                                .build();
                UserObserver usopp = UserObserver.builder()
                                .fname("Usopp")
                                .email("usopp@onepiece.com")
                                .build();
                UserObserver sanji = UserObserver.builder()
                                .fname("Sanji")
                                .email("sanji@onepiece.com")
                                .build();
                UserObserver chopper = UserObserver.builder()
                                .fname("Chopper")
                                .email("chopper@onepiece.com")
                                .build();
                UserObserver robin = UserObserver.builder()
                                .fname("Robin")
                                .email("robin@onepiece.com")
                                .build();
                UserObserver franky = UserObserver.builder()
                                .fname("Franky")
                                .email("franky@onepiece.com")
                                .build();
                UserObserver brook = UserObserver.builder()
                                .fname("Brook")
                                .email("brook@onepiece.com")
                                .build();
                UserObserver jinbe = UserObserver.builder()
                                .fname("Jinbe")
                                .email("jinbe@onepiece.com")
                                .build();

                return List.of(luffy, zoro, sanji, nami, brook, chopper, franky, robin, jinbe,
                                usopp);
        }
}
