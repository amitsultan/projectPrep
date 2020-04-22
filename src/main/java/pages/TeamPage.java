package pages;

import team.Coach;
import team.Player;
import team.Team;

import java.util.Date;

public class TeamPage extends PersonalPage {

    private Team team;

    public TeamPage(Team team,String description,Date birthDate) {
        super(birthDate,description);
        this.team = team;
        this.team.addPersonalPage(this);
    }

    public void newCoach(Coach coach){
        setChanged();
        notifyObservers(coach);
    }

    public void newPlayer(Player player){
        setChanged();
        notifyObservers(player);
    }

}
