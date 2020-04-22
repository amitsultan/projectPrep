package users;

import league.Event;
import league.Game;
import pages.*;
import java.util.Observable;
import java.util.Observer;

public class Fan  implements Observer {

    private User user;

    public Fan(String firstName, String lastName, String userName, String password) {
        User user = new User(firstName,lastName,userName,password);
        this.user = user;

    }

    public Fan(User user){
        this.user = user;
    }

    public void addGameTracking(Game game){
        if(game == null){
            throw new NullPointerException("Cannot track a null game");
        }
        game.addObserver(this);
    }

    public void addPersonalPageTracking(PersonalPage page){
        if(page == null){
            throw new NullPointerException("Cannot track a null page");
        }
        page.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO
        if(o instanceof Game) {
            Event event = (Event)arg;
            System.out.println(event);
        }
        if(o instanceof PersonalPage){

        }
    }
}
