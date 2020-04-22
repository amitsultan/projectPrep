package users;

import league.Event;
import league.Game;
import pages.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Fan  implements Observer {

    private User user;
    private LinkedList<String> notifications;
    public Fan(String firstName, String lastName, String userName, String password) {
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

    @Override
    public void update(Observable o, Object arg) {
        //TODO
        if(o instanceof Game) {
            Event event = (Event)arg;
            notifications.addLast(event.toString());
        }
        if(o instanceof PlayerPage){
            notifications.addLast((String)arg);
        }
    }
}
