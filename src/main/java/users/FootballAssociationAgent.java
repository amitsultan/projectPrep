package users;

import controllers.LeagueSeasonController;
import dbhandler.Database;
import league.*;

import java.util.LinkedList;

public class FootballAssociationAgent extends User{

    private Database db;

    public FootballAssociationAgent(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        db = Database.getInstance();
    }

    public boolean addLeague(LeagueType type) {
        League league = new League(type);
        try {
            return db.addLeague(this, league);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSeasonToLeague(int leagueID,int year,Season season){
        try {
            LinkedList<League> leagues = db.getLeagues(this);
            for (League league : leagues) {
                if (league.getID() == leagueID) {
                    return league.addSeason(year, season);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addReferee(String firstName, String lastName, String userName, String password, float salary, RefereeType type){
        Referee referee = new Referee(new User(firstName, lastName, userName, password), salary, type);
        try {
            return db.addReferee(this, referee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteReferee(Referee referee){
        try{
            return db.deleteReferee(this, referee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRefereeToLeagueSeason(League league, Season season, Referee referee){
        try{
            LeagueSeasonController leagueSeasonController = db.getLeagueSeasonController(this, league, season);
            if(leagueSeasonController == null){
                return false;
            }
            return leagueSeasonController.addReferee(referee);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
