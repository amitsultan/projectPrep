package users;


public class User {

    private int ID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public User(int ID, String firstName, String lastName, String userName, String password) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getID() {
        return ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean canBeOwner(){
        return true;
    }
}
