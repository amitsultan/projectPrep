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
        if(type == null)
            return false;
        League league = new League(type);
        try {
            return db.addLeague(this, league);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeLeague(League league) {
        if(league == null)
            return false;
        try{
            return db.deleteLeague(this, league);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



    public boolean addSeasonToLeague(int leagueID,int year,Season season){
        if(year <= 0 || season == null)
            return false;
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
        if(firstName == null || lastName == null || userName == null || password == null)
            return false;
        if(userName.isEmpty() || password.isEmpty())
            return false;
        if(type == null)
            return false;
        if(salary <= 0)
            return false;
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
        if(referee == null)
            return false;
        try{
            return db.deleteReferee(this, referee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRefereeToLeagueSeason(League league, Season season, Referee referee){
        if(league == null || season == null || referee == null)
            return false;
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
    public boolean addLeagueSeasonController(Season s,League l){
        if(l == null || s == null)
            return false;
        try {
            return db.addLeagueSeasonController(this,l,s);
        } catch (Exception e) {
            return false;
        }
    }
}
