package Team;

import Team.LeagueSeasonController;
import Users.User;

import java.util.HashMap;

public class Referee extends User {


    double salary;
    private String type;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;

    public Referee(int ID, String firstName, String lastName, double salary, HashMap<String, Game> games, String type, HashMap<String, LeagueSeasonController> leagueSeasonController) {

        this.salary = salary;
        this.type = type;
        this.leagueSeasonController = leagueSeasonController;
    }

    /**
     * default constructor
     */
    public Referee(){
        this.ID= 0;
        this.firstName=null;
        this.lastName=null;

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
