package il.ac.sce.project_aleksandrkisliak.model;

public class User {
    private int userid;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private String password;
    private boolean isAdmin;
    private int countOfTicketsPurchased;

    public User() {
    }

    public User(String firstname, String lastname, String username, String phoneNumber, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(int userid, String firstname, String lastname, String username, String phoneNumber, String password, boolean isAdmin, int countOfTicketsPurchased) {
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isAdmin = isAdmin;
        this.countOfTicketsPurchased = countOfTicketsPurchased;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCountOfTicketsPurchased() {
        return countOfTicketsPurchased;
    }

    public void setCountOfTicketsPurchased(int countOfTicketsPurchased) {
        this.countOfTicketsPurchased = countOfTicketsPurchased;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userid == user.userid;
    }

    @Override
    public int hashCode() {
        return userid;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
