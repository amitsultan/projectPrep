package Team;

import Users.User;

import java.util.HashMap;

/**
 * Abstract class representing a staff inside a team
 * staff have basic attribute and can be further expends
 * using inheritance.
 */
abstract class Staff extends User {

    // Variables
    private HashMap<LeagueSeasonController, Team> teamHistory;
    double salary;

    public double getSalary(){
        return salary;
    }

    public Team getTeamFromSeason(LeagueSeasonController season){

    }

}

