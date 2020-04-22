package team;

import league.Season;
import users.User;

enum Permission {DEFAULT,OWNER,WRITE, READWRITE};

public class TeamManager extends Staff {
    private Permission permission;

    public TeamOwner getSupperior() {
        return supperior;
    }

    private TeamOwner supperior;

    public TeamManager(User user, Season season, Team team, Permission permission, TeamOwner supperior) {
        super(season, team, user);
        this.permission = permission;
        this.supperior = supperior;
    }


}
