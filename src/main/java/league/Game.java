package league;

import assets.Stadium;
import team.Team;

import java.util.Arrays;
import java.util.Date;
import java.util.Observable;

public class Game extends Observable {
    private Team host;
    private Team guest;
    private Stadium stadium;
    private Date date;
    private Referee mainReferee;
    private Referee[] regularReferees;


    public Game(Team host, Team guest, Stadium stadium, Date date, Referee mainReferee, Referee[] regularReferees) throws Exception {
        if(host == null || guest == null || stadium == null || date == null || mainReferee == null || regularReferees.length != 2 || regularReferees[0] == null || regularReferees[1] == null){
            throw new Exception("Input can not be null");
        }
        if(regularReferees[0].equals(regularReferees[1])){
            throw new Exception("Regular referees must be different");
        }
        if(host.equals(guest)){
            throw new Exception("Host team must be different than guest team");
        }
        this.host = host;
        this.guest = guest;
        this.stadium = stadium;
        this.date = date;
        this.mainReferee = mainReferee;
        this.regularReferees = regularReferees;
    }

    public Team getHost() {
        return host;
    }

    public Team getGuest() {
        return guest;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Date getDate() {
        return date;
    }

    public Referee getMainReferee() {
        return mainReferee;
    }

    public Referee[] getRegularReferees() {
        return regularReferees;
    }

    @Override
    public String toString() {
        return "Game{" +
                "host=" + host +
                ", guest=" + guest +
                ", stadium=" + stadium +
                ", date=" + date +
                ", mainReferee=" + mainReferee +
                ", regularReferees=" + Arrays.toString(regularReferees) +
                '}';
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Game))
            return false;
        Game game = (Game) object;
        if(this.date != game.date)
            return false;
        if(!this.host.equals(game.host) || !this.guest.equals(game.guest))
            return false;
        if(!this.mainReferee.equals(game.mainReferee) || this.regularReferees[0].equals(game.regularReferees[0]) || this.regularReferees[1].equals(game.regularReferees[1]))
            return false;
        if(!this.stadium.equals(game.stadium))
            return false;
        return true;
    }
}
