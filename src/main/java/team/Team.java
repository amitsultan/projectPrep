package team;
import assets.Asset;
import assets.Stadium;
import controllers.LeagueSeasonController;
import league.Game;
import league.Season;
import pages.TeamPage;
import users.User;

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
    private HashMap<Season, TeamManager> manager;
    private HashMap<Season, Coach> coach;
    private HashMap<Season, LinkedList<Player>> players;
    private HashSet<Asset> assets;
    private status status;
    private TeamPage page;

    public Team(String name, Stadium stdm){
        this.status = team.status.ACTIVE;
    }

    public String getName(){
        return this.name;
    }

    public void addAsset(Asset asset, Season season){
        if(asset instanceof Coach){
            if(coach.containsKey(season)){
                coach.replace(season,(Coach)asset);
            }
            else coach.put(season,(Coach)asset);
            this.page.newCoach((Coach)asset);
        }
        if(asset instanceof Player){
            if(this.players.containsKey(season)){
                LinkedList<Player> playersUpdate = this.players.get(season);
                playersUpdate.add((Player)asset);
                players.replace(season,playersUpdate);
            }
            else{
                LinkedList<Player> playersUpdate = new LinkedList<>();
                playersUpdate.add((Player)asset);
                players.put(season,playersUpdate);
            }
            this.page.newPlayer((Player) asset);
        }
        else {
            stadium = (Stadium)asset;
        }
    }
    public void removeAsset(Asset asset, Season season) throws Exception {
        if(asset instanceof Coach){
            if(coach.containsKey(season)){
                coach.remove(season,asset);
            }
            else throw new Exception("Can't remove current asset because the season doesn't exist");
        }
        if(asset instanceof Player){
            if(this.players.containsKey(season)){
                LinkedList<Player> playersUpdate = this.players.get(season);
                if(playersUpdate.contains(asset)){
                    playersUpdate.remove(asset);
                    players.replace(season,playersUpdate);
                }
                else throw new Exception("Can't remove current asset because player doesn't exist");
            }
            else throw new Exception("Can't remove current asset because the season doesn't exist");
        }
        else {
            throw new Exception("Can't remove current asset because team must have stadium");
        }
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

    public HashMap<Season, TeamManager> getManager() {
        return manager;
    }

    public void setStatus( status status){
        this.status = status;
    }


    public boolean checkAvailability(User user) {
        for (TeamOwner owner: owners) {
            if(owner.getUser().equals(user))
                return false;
        }
        for (Season season: manager.keySet()){
            if(manager.get(season).getUser().equals(user)){
                return false;
            }
        }
        return true;
    }

    public void addPersonalPage(TeamPage page){
        if(this.page == null && page != null)
            this.page = page;
    }

    public void addManager(TeamManager teamManager, Season season) {
        manager.put(season,teamManager);
    }

    public void removeTeamManager(TeamManager man, Season season) {
        manager.remove(man);
    }
}
