package team;

import controllers.PersonalPage;

public class Coach extends Staff{
    private PersonalPage personalPage;

    public Coach(String ID, String firstName, String LastName,PersonalPage personalPage) {
        this.personalPage = personalPage;
        super(ID,firstName,LastName);
    }

    public void addToPersonalPage (){

    }
}
