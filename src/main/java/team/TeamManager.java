package team;

import assets.Asset;
import league.Season;
import users.User;

;import java.util.HashSet;

import static team.ManagerPermission.DEFAULT;
import static team.ManagerPermission.OWNER;

public class TeamManager extends Staff {

    private ManagerPermission permission;
    private TeamOwner superior;
    private HashSet<Object> subscrisbeHistory;

    public TeamManager(User user, Season season, Team team, ManagerPermission permission, TeamOwner superior) throws Exception {
        super(season, team, user);
        this.permission = permission;
        this.superior = superior;
        subscrisbeHistory = new HashSet<>();
    }


    public TeamOwner getSuperior() {
        return superior;
    }

    public boolean setPremission(ManagerPermission pr, User owner){
        if(!owner.equals(this.superior.getUser())){
            return false;
        }
        this.permission=pr;
        return true;
    }

    public boolean addAsset(Asset asset, Season season) throws notOwnerOfTeam {
        if(asset == null|| season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission==DEFAULT)
            return false;
        super.currentTeam.addAsset(asset,season);
        return true;
    }

    public boolean updateAsset(Asset asset) throws notOwnerOfTeam, InvalidSubscription {
        if(asset == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission==DEFAULT)
            return false;
        super.currentTeam.updateAsset(asset);
        return true;
    }

    public boolean removeAsset(Asset asset, Season season) throws Exception {
        if(asset == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission==DEFAULT)
            return false;
        super.currentTeam.removeAsset(asset,season);
        return true;
    }

    public TeamOwner addTeamOwner (User user) throws Exception {
        if(user == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission!=OWNER)
            throw new authorizationException("Manager is not autherized to make this action!");
        for (TeamOwner owner:super.currentTeam.getOwners()) {
            if(owner.getUser().equals(user)) {
                throw new InvalidSubscription("this user is already owner of this team");
            }
        }
        for (Season controller :super.currentTeam.getManager().keySet()) {
            if(super.currentTeam.getManager().get(controller).getUser().equals(user)){
                throw new InvalidSubscription("this user is already manager of this team");
            }
        }
        TeamOwner newTeamOwner = new TeamOwner(super.currentTeam,user);
        this.subscrisbeHistory.add(newTeamOwner);
        return newTeamOwner;
    }

    public void removeTeamOwner(TeamOwner teamOwner) throws Exception{
        if(teamOwner == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission!=OWNER)
            throw new authorizationException("Manager is not autherized to make this action!");
        if(!super.currentTeam.getOwners().contains(teamOwner)){
            throw new InvalidSubscription("this owner is not an owner of this team");
        }
        super.currentTeam.removeTeamOwner(teamOwner);
    }

    public TeamManager addTeamManager( User user, Season season, ManagerPermission permissions) throws Exception {
        if(user == null || season == null || permissions == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission!=OWNER)
            throw new authorizationException("Manager is not autherized to make this action!");
        TeamManager teamManager;
        boolean canMakeManager = super.currentTeam.checkAvailability(user);
        if(canMakeManager) {
            teamManager = new TeamManager(user, season, super.currentTeam, permissions, this.superior);
            super.currentTeam.addManager(teamManager,season);
        }
        else throw new InvalidSubscription("This user is already a manager or owner of this team");
        return teamManager;
    }

    public void removeTeamManager (TeamManager manager, Season season) throws Exception {
        if(manager == null || season == null){
            throw new NullPointerException("all values must not be null");
        }
        if(permission!=OWNER)
            throw new authorizationException("Manager is not autherized to make this action!");
        super.currentTeam.removeTeamManager(manager,season);
    }
}
