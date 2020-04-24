package team;

import league.Season;
import pages.PersonalPage;
import pages.PlayerPage;
import users.User;

public class Player extends Staff {

    protected int number;
    protected PlayerPage page;

    public Player(int number, int salary, Team team, User user, Season season) throws NullPointerException {
        super(season,team,user);
        if(user == null)
            throw new NullPointerException("User cannot be null!");
        this.user = user;
        this.number = number;
        this.salary = salary;
    }

    public Team getCurrentTeam(){
        return currentTeam;
    }


    public void changeNumber(int newNumber){
        if(newNumber <=0)
            return;
        this.number = newNumber;
        if(page != null)
            page.changePlayerNumber(newNumber);
    }
    public void addPersonalPage(PlayerPage page){
        if(this.page == null && page != null)
            this.page = page;
    }
    public int getPlayerNumber(){
        return number;
    }


    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Player))
            return false;
        Player p = (Player)obj;
        return p.number == this.number && p.currentTeam.equals(currentTeam);
    }

    @Override
    public String toString() {
        return "[ " +user.getFirstName()+" "+user.getLastName()+
                ", number = " + number +
                ", currentTeam = " + currentTeam.getName() +
                " ]";
    }
}
