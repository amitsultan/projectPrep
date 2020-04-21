package Team;

import Team.LeagueSeasonController;
import Users.User;

import java.util.HashMap;
enum Type{
    MAIN=1, REGULAR=2;
}

public class Referee extends User {


    private double salary;
    private Type type;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;



    /**
     * default constructor
     */
    public Referee(){
        this.salary=0;
        this.type=null;
        this.leagueSeasonController=null;

    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, LeagueSeasonController> getLeagueSeasonController() {
        return leagueSeasonController;
    }

    public void setLeagueSeasonController(HashMap<String, LeagueSeasonController> leagueSeasonController) {
        this.leagueSeasonController = leagueSeasonController;
    }
}
