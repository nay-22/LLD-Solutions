package src.domain.repoPushNotifications;

import src.domain.interfaces.Observer;

public class UserObserver implements Observer<RepoMessage> {
    private final String email;
    private String fname;
    private String lname;

    private UserObserver(String email, String fname, String lname) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        this.email = email;
        this.fname = fname;
        this.lname = lname;
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

        public UserObserver build() {
            return new UserObserver(email, fname, lname);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public void onNotify(RepoMessage val) {
        System.out.println("[" + fname + "] " + val);
    }

    @Override
    public String toString() {
        return "UserObserver {email=" + email + ", fname=" + fname + ", lname=" + lname + "}";
    }

}
