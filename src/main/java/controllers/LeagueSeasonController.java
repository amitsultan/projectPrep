package controllers;

import league.Game;
import league.League;
import league.Season;
import league.Referee;
import team.Staff;
import team.Team;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LeagueSeasonController {
    private Season season;
    private League league;

    private HashMap<Team,List<Staff>> teamMap;
    private LinkedList<Referee> referees;
    private LinkedList<Game> games;

    public LeagueSeasonController(Season season, League league) throws Exception {
        this.season = season;
        this.league = league;
        teamMap = new HashMap<>();
        referees = new LinkedList<>();
        games = new LinkedList<>();
        // add the season to league map
        if(!league.addSeason(season.getYear(),season))
            throw new Exception("Could'nt add the season to league");
        if(!league.addSeasonController(season.getYear(),this)){
            throw new Exception("Could'nt add the season controller to league");
        }
    }

    public boolean addGame(Game game){
        if(!games.contains(game)){
            games.add(game);
            return true;
        }
        return false;
    }

    public boolean addTeam(Team team){
        if(teamMap.containsKey(team))
            return false;
        teamMap.put(team,null);
        return true;
    }

    /**
     * Method assign staff members to the correct team
     * if no staff member found, all staff member given will be
     * added to the team.
     * if staff member already exists, add only those who are'nt found.
     * @param team - Team that hire the staff
     * @param members - staff members
     * @return - true if successfully added false otherwise
     */
    public boolean assignStaff(Team team,List<Staff> members){
        if(team == null)
            return false;
        if(!teamMap.containsKey(team)){
            addTeam(team);
        }
        if(members == null)
            return false;
        List<Staff> currentStaff = teamMap.get(team);
        if(currentStaff == null){
            teamMap.put(team,members);
            return true;
        }else{
            for (Staff newMember : members) {
                if(!currentStaff.contains(newMember))
                    currentStaff.add(newMember);
            }
            teamMap.put(team,currentStaff);
            return true;
        }
    }

    public List<Game> getGames(Team team) {
        List<Game> gamesWithTeam = new LinkedList<>();
        for (Game game : games) {
            if(game.getGuest().equals(team) || game.getHost().equals(team)){
                gamesWithTeam.add(game);
            }
        }
        return gamesWithTeam;
    }

    public boolean addReferee(Referee referee){
        if(referees.contains(referee))
            return false;
        referees.add(referee);
        return true;
    }

    public boolean leagueEquals(League league){
        return this.league.equals(league);
    }

    public boolean seasonEquals(Season season){
        return this.season.equals(season);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LeagueSeasonController))
            return false;
        LeagueSeasonController lsc = (LeagueSeasonController) obj;
        return lsc.league.equals(league) && lsc.season.equals(season);
    }

}
