package team;


import league.Season;
import pages.CoachPage;
import pages.PersonalPage;
import pages.PlayerPage;
import users.User;

public class Coach extends Staff  {

    protected CoachType type;
    private CoachPage page;

    public Coach(User user, Season season, Team team, CoachType type) {
        super(season, team, user);
        this.type = type;
    }


    public CoachType getType() {
        return type;
    }

    public void addPersonalPage(CoachPage page){
        if(this.page == null && page != null)
            this.page = page;
    }

    public void setType(CoachType type) {
        if(this.type == type)
            return;
        this.type = type;
        page.changeType();
    }
}


enum CoachType{
    Main,
    Assistant,
    GoalKeeper,
    Mental,
    Fitness
}

