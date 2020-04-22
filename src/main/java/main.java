import assets.Stadium;
import league.Event;
import league.Game;
import league.Referee;
import league.RefereeType;
import team.Team;
import users.Fan;
import users.User;

import java.util.Date;

public class main {

    public static void main(String args[]){
        Stadium std1 = new Stadium("stadium1","noWhere");
        Stadium std2 = new Stadium("stadium2","noWhere");
        Team home = null;
        try {
            home = new Team("home",std1);
            Team guest = new Team("guest",std2);
        User user1 = new User("ref1","","hello","1234");
        User user2 = new User("ref2","","hello","1234");
        User user3 = new User("ref2","","hello","1234");
        User user4 = new User("ref2","","hello","1234");
        Referee main = new Referee(user1,12345,RefereeType.main);
        Referee ass1 = new Referee(user2,12345,RefereeType.assistant);
        Referee ass2 = new Referee(user3,12345,RefereeType.assistant);
        Referee ass3 = new Referee(user4,12345,RefereeType.assistant);
            Referee[] refs = new Referee[3];
            refs[0] = ass1;
            refs[1] = ass2;
            refs[2] = ass3;
            Game game = new Game(home,guest,std1,new Date(1995,8,3),main,refs);
            Fan fan = new Fan(user1);
            Event event = new Event(new Date(1,1,1),game,"troll",main);
            fan.addGameTracking(game);
            game.addEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
