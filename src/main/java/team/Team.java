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
        games = new HashSet<>();
        leagueSeasonController = new HashMap<>();
    }


    public Stadium getStadium() {
        return stadium;
    }

    public HashMap<Season, Coach> getCoach() {
        return coach;
    }

    public HashMap<Season, LinkedList<Player>> getPlayers() {
        return players;
    }

    public String getName(){
        return this.name;
    }

    public TeamPage getPage() {
        return page;
    }

    public void addAsset(Asset asset, Season season){
        if(asset == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(asset instanceof Coach){
            if(coach.containsKey(season)){
                coach.replace(season,(Coach)asset);
            }
            else coach.put(season,(Coach)asset);
            if(page != null)
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
            if(page != null)
                this.page.newAsset(asset);
        }
        else {
            stadium = (Stadium)asset;
        }
    }

    public void removeAsset(Asset asset, Season season) throws Exception {
        if(asset == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(asset instanceof Coach){
            if(coach.containsKey(season)){
                coach.remove(season,asset);
                return;
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
        if(manager != null) {
            this.manager = manager;
        }
    }

    //only team owner can add another team owner so all the checking whether the owner is OK implemented there/
    public void addTeamOwner(TeamOwner owner){
        if(owner != null)
        owners.add(owner);
    }

    public void removeTeamOwner(TeamOwner owner){
        if(owner != null) {
            if(owners.contains(owner) && owners.size()>1) {
                owners.remove(owner);
            }
        }
    }

    public HashSet<TeamOwner> getOwners() {
        return owners;
    }

    public HashMap<Season, TeamManager> getManager() {
        return manager;
    }

    public void setStatus( Status status){
        if(status!=null){
            if (!this.status.equals(status)) {
                this.status = status;
                if(page != null)
                    page.statusChanged(status);
            }
        } else throw new NullPointerException("all values must not be null");
    }

    public Status getStatus() {
        return status;
    }

    public boolean checkAvailability(User user) {
        if (user != null) {
            for (TeamOwner owner : owners) {
                if (owner.getUser().equals(user))
                    return false;
            }
            for (Season season : manager.keySet()) {
                if (manager.get(season).getUser().equals(user)) {
                    return false;
                }
            }
            return true;
        }
        else throw new NullPointerException("all values must not be null");
    }

    public void addPersonalPage(TeamPage page){
        if(this.page == null && page != null)
            this.page = page;
    }

    public void addManager(TeamManager teamManager, Season season) {
        if(teamManager!= null && season!= null)
            manager.put(season,teamManager);
        else throw new NullPointerException("all values must not be null");
    }

    public void removeTeamManager(TeamManager man, Season season) throws Exception {
        if(man!= null && season!= null)
            if(manager.containsKey(season) && manager.get(season).equals(man))
                manager.remove(season);
            else throw new Exception("This team manager is not on this season");
        else throw new NullPointerException("all values must not be null");
    }

    public void updateAsset(Asset asset) {
        /* TODO */
        //see how we implement the update of an asset
    }
}
