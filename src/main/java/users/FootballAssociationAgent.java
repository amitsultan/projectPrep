package users;

import league.League;
import league.LeagueType;
import league.Referee;
import league.Season;
import java.util.HashSet;

public class FootballAssociationAgent extends User{

    HashSet<League> leagues;

    public FootballAssociationAgent(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        leagues = new HashSet<>();
    }

    public void createLeague(LeagueType type){
        League league = new League(type);
        leagues.add(league);
    }

    public boolean addSeason(int leagueID,int year,Season season){
        for (League league : leagues) {
            if(league.getID() == leagueID) {
                league.addSeason(year, season);
                return true;
            }
        }
        return false;
    }

//    public boolean addReferee(){
//
//    }
//
//    public boolean deleteReferee(Referee ref){
//
//    }
//
//    public boolean addRefereeToLeagueSeason(League league, Season season, Referee referee){
//
//    }
}
