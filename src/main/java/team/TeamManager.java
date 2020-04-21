package team;

import league.Season;

enum permission{DEFAULT,OWNER,WRITE, READWRITE};

public class TeamManager extends Staff {
    private permission permission;

    public TeamManager(Season season, Team team,permission permission) {
        super(season, team);
        this.permission = permission;
    }
}
