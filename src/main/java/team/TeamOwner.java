package team;


import assets.Asset;

public class TeamOwner extends Staff {
    Team team;

    public TeamOwner(Team team) {
        this.team = team;
    }

    public void addAsset(Asset asset){
        team.addAsset(asset);
    }


}