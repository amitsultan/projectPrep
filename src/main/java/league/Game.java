package league;

import assets.Stadium;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Observable;
import team.Team;

public class Game extends Observable {
    private Team host;
    private Team guest;
    private Stadium stadium;
    private Date date;
    private Referee mainReferee;
    private Referee[] regularReferees;
    private LinkedList<Event> gameEvents;


    public Game(Team host, Team guest, Stadium stadium, Date date, Referee mainReferee, Referee[] regularReferees) throws Exception {
        if(host == null || guest == null || stadium == null || date == null || mainReferee == null || regularReferees.length != 3 || regularReferees[0] == null || regularReferees[1] == null || regularReferees[2] == null){
            throw new Exception("Input can not be null");
        }
        if(regularReferees[0].equals(regularReferees[1]) || regularReferees[2].equals(regularReferees[1]) || regularReferees[0].equals(regularReferees[2])){
            throw new Exception("Regular referees must be different");
        }
        if(host.equals(guest)){
            throw new Exception("Host team must be different than guest team");
        }
        if(mainReferee.getType()!= RefereeType.main){
            throw new Exception("main referee must be type main");
        }
        for(int i=0; i<regularReferees.length; i++){
            if(regularReferees[i].getType()!= RefereeType.assistant){
                throw new Exception("regular referee must be type assistant");
            }
        }
        this.host = host;
        this.guest = guest;
        this.stadium = stadium;
        this.date = date;
        this.mainReferee = mainReferee;
        this.regularReferees = regularReferees;
        gameEvents = new LinkedList<>();
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

    public LinkedList<Event> getGameEvents() {
        return gameEvents;
    }

    public void addEvent(Event event) throws Exception {
        if(event == null){
            throw new Exception("Event can't be null");
        }
        for(Event e: gameEvents) {
            if(e.equals(event))
                throw new Exception("Event already exists");
        }
        gameEvents.add(event);
        setChanged();
        notifyObservers(event);
    }

    public void editEvent(int eventID,String newDetails,Date currentDate){
        for (Event event : gameEvents) {
            if(event.getID() == eventID){
                try {
                    boolean eventUpdated = event.setDetails(currentDate,newDetails);
                    if(eventUpdated) {
                        setChanged();
                        notifyObservers(event);
                    }
                } catch (TimeLimitPass timeLimitPass) {
                    timeLimitPass.printStackTrace();
                }
            }
        }
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
        if(!this.mainReferee.equals(game.mainReferee) || !this.regularReferees[0].equals(game.regularReferees[0]) || !this.regularReferees[1].equals(game.regularReferees[1]) || !this.regularReferees[2].equals(game.regularReferees[2]))
            return false;
        if(!this.stadium.equals(game.stadium))
            return false;
        return true;
    }
}
