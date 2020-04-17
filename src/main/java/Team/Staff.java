package Team;
/**
 * Abstract class representing a staff inside a team
 * staff have basic attribute and can be further expends
 * using inheritance.
 */
abstract class Staff {

    // Variables
    Team team;
    double salary;


    public double getSalary(){
        return salary;
    }
}

