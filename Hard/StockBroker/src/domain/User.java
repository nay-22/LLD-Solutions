package Hard.StockBroker.src.domain;

public class User {
    private final String email;
    private final String id;
    private String fname;
    private String lname;
    private String phone;

    public User(String email, String id, String fname, String lname, String phone) {
        this.email = email;
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User {\n\temail: " + email
                + ",\n\tid: " + id
                + ",\n\tfname: " + fname
                + ",\n\tlname: " + lname
                + ",\n\tphone: " + phone
                + "\n}";
    }

}
