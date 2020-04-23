package pages;

import team.Player;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPage that = (PlayerPage) o;
        return Objects.equals(player, that.player);
    }

}
