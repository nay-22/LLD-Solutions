package Easy.StackOverflow.src.domain;

import Easy.StackOverflow.src.strategies.interfaces.ReputationComputeStrategy;

public class User {
    private String fname;
    private String lname;
    private String phone;
    private int reputation;

    private final String email;
    private final String username;

    private User(String fname, String lname, String email, String username, String phone) {
        this.username = username;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public static class UserBuilder {
        private String fname;
        private String lname;
        private String email;
        private String phone;
        private String username;

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

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            if (email == null || username == null || fname == null) {
                throw new IllegalArgumentException("These fields are mandatory: email, username, and fname");
            }
            return new User(fname, lname, email, username, phone);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getReputation() {
        return reputation;
    }

    public void computeReputation(ReputationComputeStrategy strategy) {
        reputation = strategy.computeReputation(reputation);
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

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "USER{fname: " + fname + ", lname: " + lname + ", phone: " + phone + ", email: " + email + ", username: "
                + username + ", reputation: " + reputation + "}";
    }

}
