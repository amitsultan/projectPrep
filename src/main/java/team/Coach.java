package team;


import league.Season;
import users.User;

public class Coach extends Staff  {

    protected CoachType type;
    public Coach(User user, Season season, Team team, CoachType type) {
        super(season, team, user);
        this.type = type;
    }


    public CoachType getType() {
        return type;
    }

    public void setType(CoachType type) {
        this.type = type;
    }
}


enum CoachType{
    Main,
    Assistant,
    GoalKeeper,
    Mental,
    Fitness
}

