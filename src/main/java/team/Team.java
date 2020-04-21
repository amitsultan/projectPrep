package team;
import assets.Asset;
import assets.Stadium;
import controllers.LeagueSeasonController;
import controllers.PersonalPage;
import league.Game;
import users.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Team {
    private String name;
    private Stadium stdm;
    private PersonalPage page;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;
    private HashMap<String, Game> Games;
    private TeamOwner owner;
    private HashMap<LeagueSeasonController, TeamManager> manager;
    private HashMap<LeagueSeasonController, Coach> coach;
    private HashMap<LeagueSeasonController, LinkedList<Player>> players;
    private HashSet<Asset> assets;

    public Team(String name, Stadium stdm){

    }

    public String getName(){
        return this.name;
    }

    public void addAsset(Asset asset){
        assets.add(asset);
    }

}
