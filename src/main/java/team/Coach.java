package team;


import league.Season;
import pages.CoachPage;
import pages.PersonalPage;
import pages.PlayerPage;
import users.User;

import java.util.Objects;

public class Coach extends Staff  {

    protected CoachType type;
    private CoachPage page;

    public Coach(User user, Season season, Team team, CoachType type) throws Exception {
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
        if(page != null)
            page.changeType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coach coach = (Coach) o;
        return user.equals(coach.user);
    }


    @Override
    public String toString() {
        return "[ " +user.getFirstName()+" "+user.getLastName()+
                ", type = " + this.type +
                ", currentTeam = " + currentTeam.getName() +
                " ]";
    }
}


