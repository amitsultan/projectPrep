package team;

import controllers.LeagueSeasonController;
import league.Season;
import users.User;
import java.util.HashMap;


abstract public class Staff {

    // Variables
    protected User user;
    double salary;
    protected Team currentTeam;
    protected HashMap<Season,Team> teamHistory;

    public Staff(Season season,Team team){
        if(team == null || season == null)
            throw new NullPointerException("team and season must be valid!");
        if(teamHistory == null){
            teamHistory = new HashMap<>();
        }
        currentTeam = team;
        teamHistory.put(season,team);
    }

    public double getSalary(){
        return salary;
    }

    public Team getTeamFromSeason(LeagueSeasonController season){
        return null;
    }

}

