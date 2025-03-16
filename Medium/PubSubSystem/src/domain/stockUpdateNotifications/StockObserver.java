package Medium.PubSubSystem.src.domain.stockUpdateNotifications;

import Medium.PubSubSystem.src.domain.interfaces.Observer;
import Medium.PubSubSystem.src.domain.AbstractUser;

public class StockObserver extends AbstractUser implements Observer<StockMessage> {

    private StockObserver(String email, String fname, String lname) {
        super(email, fname, lname);
    }

    public static class UserBuilder {
        private String fname;
        private String lname;
        private String email;

        public UserBuilder fname(String fname) {
            this.fname = fname;
            return this;
        }

        public UserBuilder lname(String lname) {
            this.lname = lname;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public StockObserver build() {
            return new StockObserver(email, fname, lname);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public void onNotify(StockMessage val) {
        System.out.println("[" + email + "] recieved update: " + val);
    }

}
