package users;

import java.util.Observable;
import java.util.Observer;

public class Fan  implements Observer {
    private User user;

    public Fan(int ID, String firstName, String lastName, String userName, String password) {
        User user = new User(ID,firstName,lastName,userName,password);
        this.user = user;

    }

    public Fan(User user){
        this.user = user;
    }


    @Override
    public void update(Observable o, Object arg) {
        //TODO

    }
}
