package Medium.PubSubSystem.src.domain;

public abstract class AbstractUser {
    protected final String email;
    protected String fname;
    protected String lname;

    protected AbstractUser(String email, String fname, String lname) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        this.email = email;
        this.fname = fname;
        this.lname = lname;
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

}