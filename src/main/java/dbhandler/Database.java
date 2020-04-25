package dbhandler;

import assets.Stadium;
import controllers.LeagueSeasonController;
import league.League;
import league.Referee;
import league.Season;
import pages.PersonalPage;
import team.Player;
import team.Team;
import users.FootballAssociationAgent;
import users.SystemManager;
import users.User;

import java.security.acl.Owner;
import java.util.LinkedList;

public class Database {
    private LinkedList<League> leagues;
    private LinkedList<LeagueSeasonController> leagueSeasonControllers;
    private LinkedList<Referee> referees;
    private LinkedList<Player> staff;
    private LinkedList<Owner> owners;
    private LinkedList<User> users;
    private LinkedList<Team> teams;
    private LinkedList<String> log;
    private LinkedList<Season> seasons;
    private LinkedList<PersonalPage> personalPages;
    private int numOfSystemManagers;
    private static Database db;

    private Database(){
        leagues = new LinkedList<>();
        leagueSeasonControllers = new LinkedList<>();
        referees = new LinkedList<>();
        users = new LinkedList<>();
        teams = new LinkedList<>();
        log=new LinkedList<>();
        numOfSystemManagers=1;
        seasons=new LinkedList<>();
        personalPages=new LinkedList<>();
        staff = new LinkedList<>();
        owners = new LinkedList<>();
    }

    public static Database getInstance(){
        if(db == null){
            db = new Database();
        }
        return db;
    }

    public int getNumOfTeams(){
        return teams.size();
    }

    public int getNumOfUsers(){
        return users.size();
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

    public boolean deleteLeague(Object user, League league) throws NoPrivileges{
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can delete leagues!");
        }
        return leagues.remove(league);
    }

    public boolean addReferee(Object user, Referee referee) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can add referees!");
        }
        if(referees.contains(referee))
            return false;
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

    public boolean addUser(User admin,User user) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can add users!");
        }
        if((user instanceof SystemManager))
            numOfSystemManagers++;
        return users.add(user);
    }

    public LinkedList<User> getUsers(User admin) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can get users!");
        }
        return users;
    }


    public boolean deleteUser(User admin, User user) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can delete users!");
        }
        if((user instanceof SystemManager)) {
            if(this.numOfSystemManagers==1){
                return false;
            }
            else{
                numOfSystemManagers--;
                return users.remove(user);
            }
        }
        return users.remove(user);
    }
    public boolean addTeam(User admin, Team team) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can add teams!");
        }
        return teams.add(team);
    }
    public boolean deleteTeam(User admin, Team team) throws Exception{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can delete teams!");
        }
        if(!teams.contains(team)){
            throw new NoTeamDetected("No team detected");
        }
        return teams.remove(team);
    }
    public LinkedList<Team> getTeam(User admin) throws NoPrivileges{
        if(!(admin instanceof SystemManager)){
            throw new NoPrivileges("Only System managers can עקא teams!");
        }
        return teams;
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
        this.seasons.clear();
        this.personalPages.clear();
        this.users.add(admin);
        this.staff.clear();
        this.owners.clear();
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
    public boolean deleteLeagueSeasonController(Object user,LeagueSeasonController lsc) throws NoPrivileges {
        if(!(user instanceof FootballAssociationAgent)){
            throw new NoPrivileges("Only football association agent can delete league season controller!");
        }
        return leagueSeasonControllers.remove(lsc);
    }

    public boolean addLeagueSeasonController(User user,League l,Season s) throws Exception {
        if (!(user instanceof FootballAssociationAgent)) {
            throw new NoPrivileges("Only football association agent can add league season controller!");
        }
        if (l == null || s == null)
            return false;
        try {
            LeagueSeasonController lsc = new LeagueSeasonController(s, l);
            if (leagueSeasonControllers.contains(lsc))
                return false;
            return leagueSeasonControllers.add(lsc);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addToLog(String l){
        this.log.add(l);
    }
}
