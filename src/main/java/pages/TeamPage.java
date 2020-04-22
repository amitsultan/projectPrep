package pages;

import team.Team;

import java.util.Date;

public class TeamPage extends PersonalPage {

    private Team team;

    public TeamPage(Team team,String description,Date birthDate) {
        super(birthDate,description);
        this.team = team;
        this.team.addPersonalPage(this);
    }


}
