package team;

import assets.Asset;
import controllers.LeagueSeasonController;
import league.Season;
import users.User;
import java.util.HashMap;
import java.util.Objects;


abstract public class Staff extends Asset {

    // Variables
    protected User user;
    double salary;
    protected Team currentTeam;
    protected HashMap<Season,Team> teamHistory;

    public Staff(Season season,Team team,User user){
        if(team == null || season == null)
            throw new NullPointerException("team and season must be valid!");
        if(teamHistory == null){
            teamHistory = new HashMap<>();
        }
        currentTeam = team;
        this.user = user;
        teamHistory.put(season,team);
    }

    public double getSalary(){
        return salary;
    }

    public Team getTeamFromSeason(LeagueSeasonController season){
        return null;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(user, staff.user);
    }
}