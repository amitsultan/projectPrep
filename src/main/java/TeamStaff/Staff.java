package TeamStaff;

/**
 * Abstract class representing a staff inside a team
 * staff have basic attribute and can be further expends
 * using inheritance.
 */
abstract class Staff {

    // Variables
    protected Team team;
    protected int ID;
    protected String firstName;
    protected String lastName;
    protected double salary;




    public int getID(){
        return ID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getSalary(){
        return salary;
    }
}

