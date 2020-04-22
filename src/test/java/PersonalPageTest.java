import assets.Stadium;
import league.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.*;
import team.*;
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        page.changeDescription("new Description test");
        Assert.assertEquals(2,fan.getNumberOfNotification());
        Assert.assertEquals("New description: new Description test", fan.getLastNotification());
    }

    @Test
    public void testFanNotifiedByCoach(){
        Coach coach = new Coach(user1,season,team,CoachType.Assistant);
        CoachPage page = new CoachPage(coach,"First description",new Date());
        fan.addPersonalPageTracking(page);
        Assert.assertEquals(1,page.countObservers());
        Assert.assertEquals(0,fan.getNumberOfNotification());
        coach.setType(CoachType.Main);
        Assert.assertEquals(1,fan.getNumberOfNotification());
        String lastNotification = fan.getLastNotification();
        Assert.assertEquals(lastNotification,"Coach type changed to: Main");
    }

    @Test
    public void testFanNotifiedByTeam(){
        TeamPage page = new TeamPage(team,"New Team created",new Date());
        fan.addPersonalPageTracking(page);
        Assert.assertEquals(1,page.countObservers());
        Assert.assertEquals(0,fan.getNumberOfNotification());
        team.setStatus(status.NOTACTIVE);
        Assert.assertEquals(1,fan.getNumberOfNotification());
        String lastNotification = fan.getLastNotification();
        Assert.assertEquals(lastNotification,team.getName()+" changed their status to: NOTACTIVE");
        Player player1 = new Player(15,15,team,user1,season);
        team.addAsset(player1,season);
        Assert.assertEquals(2,fan.getNumberOfNotification());
        lastNotification = fan.getLastNotification();
        Assert.assertEquals(lastNotification,"New player added to "+team.getName()+" : "+player1.toString());
        Coach coach = new Coach(user1,season,team,CoachType.Assistant);
        team.addAsset(coach,season);
        Assert.assertEquals(3,fan.getNumberOfNotification());
        lastNotification = fan.getLastNotification();
        Assert.assertEquals(lastNotification,"New coach added to "+team.getName()+" : "+coach.toString());
        System.out.println(lastNotification);
    }

}
