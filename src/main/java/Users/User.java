package Users;
import Team.TeamManager;


public abstract class User {
    protected int ID;
    protected String firstName;
    protected String lastName;

    public User(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * default constructor
     */
    public User() {
        this.ID = 0;
        this.firstName = null;
        this.lastName = null;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
