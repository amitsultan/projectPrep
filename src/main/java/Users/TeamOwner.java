package Users;

import Team.Team;

public class TeamOwner extends User{
    Team team;

    public TeamOwner(Team team) {
        this.team = team;
        this.isOwner = true;
    }
}