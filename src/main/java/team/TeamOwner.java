package team;

import assets.Asset;
import league.Season;
import users.User;
import java.util.HashSet;

public class TeamOwner{
    private HashSet<Team> teams;
    private HashSet<Object> subscribeHistory;
    private User user;

    public TeamOwner(Team team, User user) {
        if(team == null || user == null){
            throw new NullPointerException("all values must not be null");
        }
        teams = new HashSet<>();
        teams.add(team);
        team.addTeamOwner(this);
        this.user = user;
        subscribeHistory = new HashSet<>();
    }



    public void addAsset(Asset asset,Team team, Season season) throws notOwnerOfTeam {
        if(asset == null|| team == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(teams.contains(team)){
            team.addAsset(asset,season);
        }
        else{
            throw new notOwnerOfTeam("Can't add asset to a team that's not owned");
        }
    }

    public void updateAsset(Asset asset,Team team) throws notOwnerOfTeam, InvalidSubscription {
        if(asset == null|| team == null ){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't add team owner to a team you don't own ");
        }
        team.updateAsset(asset);
    }

    public void removeAsset(Asset asset, Team team, Season season) throws Exception {
        if(asset == null|| team == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(teams.contains(team)){
            team.removeAsset(asset,season);
        }
        else{
            throw new notOwnerOfTeam("Can't add asset to a team that's not owned");
        }
    }


    public TeamOwner addTeamOwner (User user,Team team) throws Exception {
        if(user == null|| team == null){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't add team owner to a team you don't own ");
        }
        for (TeamOwner owner:team.getOwners()) {
            if(owner.getUser().equals(user)) {
                throw new InvalidSubscription("this user is already owner of this team");
            }
        }
        for (Season controller :team.getManager().keySet()) {
            if(team.getManager().get(controller).getUser().equals(user)){
                throw new InvalidSubscription("this user is already manager of this team");
            }
        }
        TeamOwner newTeamOwner = new TeamOwner(team,user);
        this.subscribeHistory.add(newTeamOwner);
        return newTeamOwner;
    }

    public void removeTeamOwner(TeamOwner teamOwner,Team team) throws Exception{
        if(teamOwner == null|| team == null ){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't remove team owner to a team you not own ");
        }
        if(!team.getOwners().contains(teamOwner)){
            throw new InvalidSubscription("this owner is not an owner of this team");
        }
        team.removeTeamOwner(teamOwner);
    }

    public TeamManager addTeamManager( User user, Team team, Season season, ManagerPermission permissions) throws InvalidSubscription {
        if(user == null|| team == null || season == null || permissions == null){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't add team manager to a team you don't own ");
        }
        TeamManager teamManager;
        boolean canMakeManager = team.checkAvailability(user);
        if(canMakeManager) {
            teamManager = new TeamManager(user, season, team, permissions, this);
            team.addManager(teamManager,season);
        }
        else throw new InvalidSubscription("This user is already a manager or owner of this team");
        return teamManager;
    }

    //TODO add removal of all subscription of the owner we remove
    public void removeTeamManager (TeamManager manager,Team team, Season season) throws Exception {
        if(manager == null|| team == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't remove team manager from a team you don't own");
        }
        if(manager.getSuperior().getUser().equals(user)){
            team.removeTeamManager(manager,season);
        }
        else throw new InvalidSubscription("Can't remove manager you didn't subscribed");
    }

    public void removeTeam(Team team) throws InvalidSubscription {
        if( team == null){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't remove team you don't own");
        }
        if(team.getStatus() == Status.ACTIVE) {
            team.setStatus(Status.NOTACTIVE);
        }else throw new InvalidSubscription("This team is already closed");
    }

    public void reactivateTeam(Team team) throws InvalidSubscription {
        if( team == null ){
            throw new NullPointerException("all values must not be null");
        }
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't reactivate team you don't own");
        }
        if(team.getStatus() == Status.NOTACTIVE) {
            team.setStatus(Status.ACTIVE);
        }else throw new InvalidSubscription("This team is already opened");
    }


    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamOwner teamOwner = (TeamOwner) o;
        return teamOwner.getUser().equals(this.user);
    }

}