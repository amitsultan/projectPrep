package league;

import users.User;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Referee {


    private float salary;
    private RefereeType type;
    private HashMap<Game,LinkedList<Event>> eventMap;
    private User user;

    public Referee(User user, float salary, RefereeType type) {
        this.user = user;
        this.salary = salary;
        this.type = type;
        eventMap = new HashMap<>();
    }

    public boolean addEvent(Event event,Game game) throws Exception {
        if(event == null || game == null){
            throw new Exception("Event and game must'nt be null");
        }
        if(!eventMap.containsKey(game)){
            addGame(game);
        }
        eventMap.get(game).add(event);
        game.notifyObservers();
        return true;
    }

    public boolean addGame(Game game){
        if(!eventMap.containsKey(game)){
            LinkedList<Event> events = new LinkedList<>();
            eventMap.put(game,events);
            return true;
        }
        return false;
    }

    public boolean editEvent(Game game,int eventID,String newDetails,Date currentDate) {
        if(!eventMap.containsKey(game))
            return false;
        LinkedList<Event> events = eventMap.get(game);
        for (Event event : events) {
            if(event.getID() == eventID){
                try {
                    boolean eventUpdated = event.setDetails(currentDate,newDetails);
                    if(eventUpdated)
                        game.notifyObservers();
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
        if(!eventMap.containsKey(game)){
            throw new NoGameFound("No game found under the given referee history");
        }
        StringBuilder report = new StringBuilder();
        report.append(game.toString()).append('\n');
        for (Event event: eventMap.get(game)) {
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


enum RefereeType {
    assistant,
    main
}