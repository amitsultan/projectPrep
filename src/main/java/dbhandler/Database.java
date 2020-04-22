package dbhandler;

import controllers.LeagueSeasonController;
import league.League;
import league.Referee;
import league.Season;
import team.Team;
import users.FootballAssociationAgent;
import users.SystemManager;
import users.User;

import java.util.HashMap;
import java.util.LinkedList;

public class Database {
    private LinkedList<League> leagues;
    private LinkedList<LeagueSeasonController> leagueSeasonControllers;
    private LinkedList<Referee> referees;
    private LinkedList<User> users;
    private LinkedList<Team> teams;
    private LinkedList<String> log;
    private static Database db;

    private Database(){
        leagues = new LinkedList<>();
        leagueSeasonControllers = new LinkedList<>();
        referees = new LinkedList<>();
        users = new LinkedList<>();
        teams = new LinkedList<>();
        log=new LinkedList<>();
    }

    public static Database getInstance(){
        if(db == null){
            db = new Database();
        }
        return db;
    }

    public boolean addLeague(Object user, League league) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can add leagues!");
        }
        leagues.add(league);
        return true;
    }

    public LinkedList<League> getLeagues(Object user) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can get leagues!");
        }
        return leagues;
    }

    public boolean addReferee(Object user, Referee referee) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can add referees!");
        }
        referees.add(referee);
        return true;
    }

    public LinkedList<Referee> getReferees(Object user) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can get referees!");
        }
        return referees;
    }

    public boolean deleteReferee(Object user, Referee referee) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can delete referees!");
        }
        return referees.remove(referee);
    }

    public boolean deleteUser(User admin, User user) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can delete users!");
        }
        return users.remove(user);
    }

    public boolean deleteTeam(User admin, Team team) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can delete teams!");
        }
        return teams.remove(team);
    }

    public LinkedList<String> getLog(User admin) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can see system log!");
        }
        return this.log;
    }

    public void initialize(User admin) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can initialize the db!");
        }
        this.leagues.clear();
        this.leagueSeasonControllers.clear();
        this.referees.clear();
        this.users.clear();
        this.teams.clear();
        this.log.clear();
    }

    public LeagueSeasonController getLeagueSeasonController(Object user, League league, Season season) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can get league season controller!");
        }
        for(LeagueSeasonController controller : leagueSeasonControllers) {
            if(controller.leagueEquals(league) && controller.seasonEquals(season))
                return controller;
        }
        return null;
    }
}
