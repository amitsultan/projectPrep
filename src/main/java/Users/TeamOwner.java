package Users;

import Budjet.Asset;
import Team.Team;


public class TeamOwner extends User{
    Team team;

    public TeamOwner(Team team) {
        this.team = team;
        this.isOwner = true;
    }

    public void addAsset(Asset asset){

    }
}