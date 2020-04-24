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

    /**
     * This method returns the list of games that the referee is assigned to
     * @return the list of games
     * @throws Exception if the referee is not assigned to any games
     */
    public LinkedList<Game> getGames() throws Exception {
        if(games.size() == 0)
            throw new Exception("No games");
        return games;
    }

    /**
     * This method allows the referee to add events to games
     * @param event is the event the referee wants to add
     * @param game is the game for which the referee wants to add a game
     * @throws Exception is raised either if the input is illegal,
     *         if the referee tries to add an event to a game that is not being played
     *         or if the event already exists in the game events
     */
    public void addEvent(Event event,Game game) throws Exception {
        if(event == null || game == null){
            throw new Exception("Event and game mustn't be null");
        }
        Date currentDate = new Date();
        float minutesSinceBeginningOfGame = (float)(currentDate.getTime() - game.getDate().getTime())/(1000*60);
        if(minutesSinceBeginningOfGame > 90 || minutesSinceBeginningOfGame < 0)
            throw new Exception("The game is not being played");
        if(!games.contains(game)) {
            addGame(game);
        }
        LinkedList<Event> events = game.getGameEvents();
        for (Event e : events) {
            if(e.getID() == event.getID()){
                throw new Exception("Event already exists");
            }
        }
        game.addEvent(event);
    }

    /**
     * This method assigns the referee to a game
     * @param game is the game
     * @return true if the referee was assigned and false if the referee is already assigned to this game
     */
    public boolean addGame(Game game){
        if(!games.contains(game)){
            games.add(game);
            return true;
        }
        return false;
    }

    /**
     * This method allows the referee to edit an event up to 5 hours after the game ended
     * @param game is the game
     * @param eventID is the id of the event to be edited
     * @param newDetails is the new details of the event
     * @param currentDate is the date in which the edited event occurred
     * @return true if the event was edited and false if the referee is not assigned to the game or if the game doesn't contain the event
     * @throws Exception is raised when the referee tries to edit the event before the game ended or more than 5 hours after it ended
     */
    public boolean editEvent(Game game,int eventID,String newDetails,Date currentDate) throws Exception {
        if(!games.contains(game))
            return false;
        long endOfGame = new Date(game.getDate().getTime() + 1000 * 60 * 90).getTime();
        float hoursSinceTheEndOfTheGame = (float)(currentDate.getTime() - endOfGame)/(1000*60*60);
        if(hoursSinceTheEndOfTheGame > 5 || hoursSinceTheEndOfTheGame < 0)
            throw new Exception("Can't edit events of game if the game did not end or if more than 5 hours passed since its end");
        LinkedList<Event> events = game.getGameEvents();
        for (Event event : events) {
            if(event.getID() == eventID){
                    boolean eventUpdated = event.setDetails(newDetails);
                    if(eventUpdated)
                        game.editEvent(eventID, newDetails, currentDate);
                    return eventUpdated;
            }
        }
        return false;
    }

    public RefereeType getType(){
        return type;
    }

    /**
     * This method generated a game report for the referee
     * @param game is the game for which the referee wants to generate a report
     * @return the generated game report as a String
     * @throws NoGameFound is raised if the referee was not assigned to the given game
     */
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
