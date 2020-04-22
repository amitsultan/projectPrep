package pages;

import league.RefereeType;
import team.Coach;

import java.util.Date;

public class CoachPage extends PersonalPage{

    private Coach coach;

    public CoachPage(Coach coach,String description,Date birthDate){
        super(birthDate,description);
        this.coach = coach;
        coach.addPersonalPage(this);
    }

    public void changeType(){
        setChanged();
        notifyObservers(coach.getType());
    }

}
