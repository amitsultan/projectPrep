package Team;
/**
 * Abstract class representing a staff inside a team
 * staff have basic attribute and can be further expends
 * using inheritance.
 */
abstract class Staff {

    // Variables
    Team team;
    int ID;
    String firstName;
    String lastName;
    double salary;


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
    public String getTeamName(){ return team.getName(); }
}

