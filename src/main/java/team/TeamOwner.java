package team;

import assets.Asset;
import controllers.LeagueSeasonController;
import users.User;
import java.util.HashSet;

public class TeamOwner{
    private HashSet<Team> teams;
    private HashSet<Object> subscribeHistory;
    private User user;

    public TeamOwner(Team team, User user) {
        teams = new HashSet<Team>();
        teams.add(team);
        this.user = user;
        subscribeHistory = new HashSet<>();
    }

    public void addAsset(Asset asset,Team team) throws notOwnerOfTeam {
        if(teams.contains(team)){
            team.addAsset(asset);
        }
        else{
            throw new notOwnerOfTeam("Can't add asset to a team that's not owned");
        }
    }

    public void addTeamOwner (User user,Team team) throws Exception {
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't add team owner to a team you not own ");
        }
        TeamOwner newTeamOwner = new TeamOwner(team,user);
        if(team.getOwners().contains(newTeamOwner)){
            throw new InvalidSubscription("this user is already owner of this team");
        }
        for (LeagueSeasonController controller :team.getManager().keySet()) {
            if(team.getManager().get(controller).getUser().equals(user)){
                throw new InvalidSubscription("this user is already manager of this team");
            }
        }
        team.addTeamOwner(newTeamOwner);
        this.subscribeHistory.add(newTeamOwner);
    }

    public void removeTeamOwner(TeamOwner teamOwner,Team team) throws Exception{
        if(!teams.contains(team)) {
            throw new InvalidSubscription("You can't remove team owner to a team you not own ");
        }
        if(!team.getOwners().contains(teamOwner)){
            throw new InvalidSubscription("this owner is not an owner of this team");
        }
        team.removeTeamOwner(teamOwner);
    }

    ////////TO DO ///////
    public void addTeamManager( User user){

    }

    public void removeTeamManager (TeamManager manager){

    }

    public void removeTeam(Team team){
        team.setStatus(status.NOTACTIVE);
    }

    public void reactivateTeam(Team team){
        team.setStatus(status.ACTIVE);
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