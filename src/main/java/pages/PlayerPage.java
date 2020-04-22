package pages;

import team.Player;

import java.util.Date;

public class PlayerPage extends PersonalPage {

    private Player player;
    private Date birthDate;
    private int playerNumber;

    public PlayerPage(Player p,String description,Date birthDate){
        super(new Date(),description);
        this.player = p;
        this.birthDate = birthDate;
        this.playerNumber = p.getPlayerNumber();
        player.addPersonalPage(this);
    }


    public void changePlayerNumber(int newNumber){
        this.playerNumber = newNumber;
        setChanged();
        notifyObservers("Player assigned a new number! new number: "+newNumber);
    }
}
