package team;

import controllers.LeagueSeasonController;
import users.User;

import java.util.HashMap;

/**
 * Abstract class representing a staff inside a team
 * staff have basic attribute and can be further expends
 * using inheritance.
 */
abstract public class Staff extends User {

    // Variables
    protected HashMap<LeagueSeasonController, Team> teamHistory;
    double salary;

    public double getSalary(){
        return salary;
    }

    public Team getTeamFromSeason(LeagueSeasonController season){
        return null;
    }

}

