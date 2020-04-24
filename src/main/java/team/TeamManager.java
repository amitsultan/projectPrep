package team;

import league.Season;
import users.User;

;

public class TeamManager extends Staff {

    private ManagerPermission permission;
    private TeamOwner superior;

    public TeamManager(User user, Season season, Team team, ManagerPermission permission, TeamOwner superior) {
        super(season, team, user);
        this.permission = permission;
        this.superior = superior;
    }


    public TeamOwner getSuperior() {
        return superior;
    }



}
