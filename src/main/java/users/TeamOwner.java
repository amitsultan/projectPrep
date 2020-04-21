package users;

import assets.Asset;
import team.Team;


public class TeamOwner extends User{
    Team team;

    public TeamOwner(Team team) {
        this.team = team;
    }

    public void addAsset(Asset asset){

    }
}