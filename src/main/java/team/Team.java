package team;
import assets.Asset;
import assets.Stadium;
import controllers.LeagueSeasonController;
import league.Game;
import users.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

enum status{ACTIVE, NOTACTIVE};

public class Team {
    private String name;
    private Stadium stadium;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;
    private HashSet<Game> games;
    private HashSet<TeamOwner> owners;
    private HashMap<LeagueSeasonController, TeamManager> manager;
    private HashMap<LeagueSeasonController, Coach> coach;
    private HashMap<LeagueSeasonController, LinkedList<Player>> players;
    private HashSet<Asset> assets;
    private status status;

    public Team(String name, Stadium stdm){
        this.status = team.status.ACTIVE;
    }

    public String getName(){
        return this.name;
    }

    public void addAsset(Asset asset){
        assets.add(asset);
    }

    //only team owner can add another team owner so all the checking whether the owner is OK implemented there/
    public void addTeamOwner(TeamOwner owner){
        owners.add(owner);
    }

    public void removeTeamOwner(TeamOwner owner){
        owners.remove(owner);
    }

    public HashSet<TeamOwner> getOwners() {
        return owners;
    }

    public HashMap<LeagueSeasonController, TeamManager> getManager() {
        return manager;
    }

    public void setStatus( status status){
        this.status = status;
    }
}
