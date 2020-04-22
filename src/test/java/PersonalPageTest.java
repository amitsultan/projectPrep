import assets.Stadium;
import league.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.*;
import team.Player;
import team.Team;
import users.Fan;
import users.User;

import java.util.Date;

public class PersonalPageTest {

    private Stadium std1;
    private Team home,guest;
    private Referee main;
    private User user1;
    private Referee[] refs;
    private Fan fan;
    private Team team;
    private  Season season;

    @Before
    public void setUp(){
        std1 = new Stadium("stadium1","noWhere");
        Stadium std2 = new Stadium("stadium2", "noWhere");
        home = new Team("home",std1);
        guest = new Team("guest", std2);
        user1 = new User("ref1", "", "hello", "1234");
        User user2 = new User("ref2", "", "hello", "1234");
        User user3 = new User("ref2", "", "hello", "1234");
        User user4 = new User("ref2", "", "hello", "1234");
        main = new Referee(user1,12345,RefereeType.main);
        Referee ref1 = new Referee(user2, 12345, RefereeType.assistant);
        Referee ref2 = new Referee(user3, 12345, RefereeType.assistant);
        Referee ref3 = new Referee(user4, 12345, RefereeType.assistant);
        fan = new Fan("Tester","level1","root","root");
        refs = new Referee[3];
        refs[0] = ref1;
        refs[1] = ref2;
        refs[2] = ref3;

        team = new Team("Swans",std1);
        season = new Season(2020);
    }

    @Test
    public void testFanNotifiedByGame(){
        try {
            Game game = new Game(home,guest,std1,new Date(1995,8,3),main,refs);
            Assert.assertTrue(fan.addGameTracking(game));
            game.addEvent(new Event(new Date(),game,"something that happend",main));
            Assert.assertEquals(1,game.countObservers());
            String lastNotification = fan.getLastNotification();
            boolean fanNotificationAssertion = lastNotification.contains("eventID = 1") && lastNotification.contains("something that happend");
            Assert.assertTrue(fanNotificationAssertion);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testFanNotifiedByPlayer(){
        Player player = new Player(11,10000,team,user1,season);
        PlayerPage page = new PlayerPage(player,"New player in the team",new Date());
        fan.addPersonalPageTracking(page);
        Assert.assertEquals(1,page.countObservers());
        String lastNotification = fan.getLastNotification();
        Assert.assertTrue(lastNotification.equals(""));
        player.changeNumber(21);
        lastNotification = fan.getLastNotification();
        Assert.assertEquals("Player assigned a new number! new number: 21",lastNotification);
    }



}
