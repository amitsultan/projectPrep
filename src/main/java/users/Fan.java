package users;

import league.Event;
import league.Game;
import pages.*;

import java.util.*;

public class Fan  implements Observer {

    private User user;
    private LinkedList<String> notifications;
    public Fan(String firstName, String lastName, String userName, String password) throws Exception {
        User user = new User(firstName,lastName,userName,password);
        this.user = user;
        notifications = new LinkedList<>();
    }

    public Fan(User user){
        this.user = user;
    }

    public boolean addGameTracking(Game game){
        if(game == null){
            return false;
        }
        game.addObserver(this);
        return true;
    }

    public void addPersonalPageTracking(PersonalPage page){
        if(page == null){
            throw new NullPointerException("Cannot track a null page");
        }
        page.addObserver(this);
    }

    public String getLastNotification(){
        if(notifications.size() == 0)
            return "";
        return notifications.getLast();
    }

    public int getNumberOfNotification(){
        return notifications.size();
    }

    public void submitComplaint(String complaint){
        Complaint sumbit = new Complaint(complaint,new Date());
        SystemManager.addComplaint(sumbit);
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO
        if(o instanceof Game) {
            Event event = (Event)arg;
            notifications.addLast(event.toString());
        }
        if(o instanceof PersonalPage){
            notifications.addLast((String)arg);
        }
    }
}
