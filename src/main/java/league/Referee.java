package league;

import users.User;

import java.util.Date;
import java.util.LinkedList;

public class Referee {

    private float salary;
    private RefereeType type;
    private LinkedList<Game> games;
    private User user;

    public Referee(User user, float salary, RefereeType type) {
        this.user = user;
        this.salary = salary;
        this.type = type;
        games = new LinkedList<>();
    }

    public LinkedList<Game> getGames() throws Exception {
        if(games.size() == 0)
            throw new Exception("No games");
        return games;
    }

    public boolean addEvent(Event event,Game game) throws Exception {
        if(event == null || game == null){
            throw new Exception("Event and game must'nt be null");
        }
        Date currentDate = new Date();
        int minutesSinceBeginningOfGame = currentDate.getMinutes() - game.getDate().getMinutes();
        if(minutesSinceBeginningOfGame > 90 || minutesSinceBeginningOfGame <= 0)
            throw new Exception("The game is not being played");
        if(!games.contains(game)) {
            addGame(game);
        }
        game.addEvent(event);
        return true;
    }

    public boolean addGame(Game game){
        if(!games.contains(game)){
            LinkedList<Event> events = new LinkedList<Event>();
            games.add(game);
            return true;
        }
        return false;
    }

    public boolean editEvent(Game game,int eventID,String newDetails,Date currentDate) throws Exception {
        if(!games.contains(game))
            return false;
        Date endOfGame = new Date(game.getDate().getTime() + 1000 * 60 * 90);
        int minutesSinceBeginningOfGame = currentDate.getHours() - endOfGame.getHours();
        if(minutesSinceBeginningOfGame > 5 || minutesSinceBeginningOfGame <= 0)
            throw new Exception("Can't edit events of game if the game did not end or if more than 5 hours passed since its end");
        LinkedList<Event> events = game.getGameEvents();
        for (Event event : events) {
            if(event.getID() == eventID){
                try {
                    boolean eventUpdated = event.setDetails(currentDate,newDetails);
                    if(eventUpdated)
                        game.editEvent(eventID, newDetails, currentDate);
                    return eventUpdated;
                } catch (TimeLimitPass timeLimitPass) {
                    timeLimitPass.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    public RefereeType getType(){
        return type;
    }

    public String getGameReport(Game game) throws NoGameFound {
        if(!games.contains(game)){
            throw new NoGameFound("No game found under the given referee history");
        }
        StringBuilder report = new StringBuilder();
        report.append(game.toString()).append('\n');
        for (Event event: game.getGameEvents()) {
            report.append("\t").append(event.toString()).append('\n');
        }
        return report.toString();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Referee))
            return false;
        return ((Referee)obj).user.getID() == user.getID();
    }
}
