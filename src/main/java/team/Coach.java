package team;


import league.Season;

public class Coach extends Staff {

    protected CoachType type;
    public Coach(Season season, Team team,CoachType type) {
        super(season, team);
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

