package team;

import assets.Asset;
import league.Season;
import users.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


abstract public class Staff extends Asset {

    // Variables
    protected User user;
    double salary;
    protected Team currentTeam;
    protected HashMap<Season,Team> teamHistory;
    protected Season season;

    public Staff(Season season,Team team,User user){
        if(team == null || season == null)
            throw new NullPointerException("team and season must be valid!");
        if(teamHistory == null){
            teamHistory = new HashMap<>();
        }
        currentTeam = team;
        this.user = user;
        this.season = season;
        teamHistory.put(season,team);
    }

    public double getSalary(){
        return salary;
    }

    public boolean changeTeam(Team team, Season season){
        if(team == null || team.equals(currentTeam))
            return false;
        if(season == null)
            return false;
        if(teamHistory.containsKey(season))
            return false;
        currentTeam = team;
        teamHistory.put(season,team);
        return true;
    }

    public Team getTeamBySeason(Season season){
        if(!teamHistory.containsKey(season))
            return null;
        return teamHistory.get(season);
    }

    public List<Season> getSeasonByTeam(Team team){
        List<Season> seasons = new LinkedList<>();
        if(team == null)
            return seasons;
        for (Season season : teamHistory.keySet()) {
            if(teamHistory.get(season).equals(team))
                seasons.add(season);
        }
        return seasons;
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