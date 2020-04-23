package pages;

import assets.Asset;
import team.*;
import java.util.Date;

public class TeamPage extends PersonalPage {

    private Team team;

    public TeamPage(Team team,String description,Date birthDate) {
        super(birthDate,description);
        this.team = team;
        this.team.addPersonalPage(this);
    }

    public void newAsset(Asset asset){
        setChanged();
        String assetName = asset instanceof Player ? "player" : "coach";
        notifyObservers("New "+ assetName+" added to "+team.getName()+" : "+asset.toString());
    }

    public void statusChanged(Status newTeamStatus){
        setChanged();
        notifyObservers(team.getName()+" changed their status to: "+newTeamStatus);
    }
}
