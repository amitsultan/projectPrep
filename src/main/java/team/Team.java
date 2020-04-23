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

;

public class Team {
    private String name;
    private Stadium stadium;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;
    private HashSet<Game> games;
    private HashSet<TeamOwner> owners;
    private HashMap<Season, TeamManager> manager;
    private HashMap<Season, Coach> coach;
    private HashMap<Season, LinkedList<Player>> players;
    //private HashSet<Asset> assets;
    private Status status;
    private TeamPage page;

    public Team(String name, Stadium stdm) throws Exception {
        if(name == null || name.isEmpty())
            throw new Exception("Name must be not null and not empty");
        if(stdm == null)
            throw new Exception("Stadium cannot be null");
        this.stadium = stdm;
        stdm.setTeam(this);
        this.name = name;
        this.status = Status.ACTIVE;
        players = new HashMap<>();
        coach = new HashMap<>();
        manager = new HashMap<>();
        owners = new HashSet<>();
        //assets = new HashSet<>();
        games = new HashSet<>();
        leagueSeasonController = new HashMap<>();
    }

    public void setPage(TeamPage page) {
        this.page = page;
    }

    public HashMap<Season, Coach> getCoach() {
        return coach;
    }

    public HashMap<Season, LinkedList<Player>> getPlayers() {
        return players;
    }

//    public HashSet<Asset> getAssets() {
//        return assets;
//    }



    public String getName(){
        return this.name;
    }

    public void addAsset(Asset asset, Season season){
        if(asset instanceof Coach){
            if(coach.containsKey(season)){
                coach.replace(season,(Coach)asset);
            }
            else coach.put(season,(Coach)asset);
            this.page.newAsset(asset);
        }
        else if(asset instanceof Player){
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
            this.page.newAsset(asset);
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
                else throw new removeAssetException("Can't remove current asset because player doesn't exist");
            }
            else throw new removeAssetException("Can't remove current asset because the season doesn't exist");
        }
        else {
            throw new removeAssetException("Can't remove current asset because team must have stadium");
        }
    }

    public void setManager(HashMap<Season, TeamManager> manager) {
        this.manager = manager;
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

    public void setStatus( Status status){
        if(!this.status.equals(status)) {
            this.status = status;
            page.statusChanged(status);
        }
    }

    public Status getStatus() {
        return status;
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

    public void updateAsset(Asset asset) {
        /* TODO */
        //see how we implement the update of an asset
    }
}
