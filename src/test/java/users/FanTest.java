package users;

import assets.Stadium;
import league.Game;
import league.Referee;
import league.RefereeType;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.CoachPage;
import pages.PersonalPage;
import pages.PlayerPage;
import pages.TeamPage;
import team.Coach;
import team.CoachType;
import team.Player;
import team.Team;
import users.Fan;
import users.SystemManager;
import users.User;

import java.util.Date;

public class FanTest {

    private Fan fan;
    private Stadium std1;
    private Team home,guest;
    private Referee main;
    private Referee[] refs;
    private User user1;
    private Season season;

    @Before
    public void setUp() {
        try {
            fan = new Fan("fName","lName","root","root");
            std1 = new Stadium("stadium1","noWhere",2);
            Stadium std2 = new Stadium("stadium2", "noWhere",2);
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
            refs = new Referee[3];
            refs[0] = ref1;
            refs[1] = ref2;
            refs[2] = ref3;
            season = new Season(2014);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddGameTrack(){
        try {
            Assert.assertFalse(fan.addGameTracking(null));
            Game game = new Game(home,guest,std1,new Date(1995,8,3),main,refs);
            Assert.assertTrue(fan.addGameTracking(game));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testPersonalPageTracking() throws Exception {
        PersonalPage page1 = new CoachPage(new Coach(user1,season,home,CoachType.Assistant),"desc",new Date());
        PersonalPage page2 = new PlayerPage(new Player(15,15,new Team("he",std1),user1,season),"desc",new Date());
        PersonalPage page3 = new TeamPage(new Team("team",std1),"desc",new Date());
        try{
            fan.addPersonalPageTracking(null);
            Assert.fail();
        }catch (Exception e){

        }
        fan.addPersonalPageTracking(page1);
        Assert.assertEquals(1,page1.countObservers());

        fan.addPersonalPageTracking(page2);
        Assert.assertEquals(1,page2.countObservers());

        fan.addPersonalPageTracking(page3);
        Assert.assertEquals(1,page3.countObservers());
    }

    @Test
    public void testSubmitComplaints(){
        SystemManager manager = null;
        try {
            manager = new SystemManager("fName","lName","root","root");
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            fan.submitComplaint("Something went wrong");
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1,manager.totalNumberOfComplaints());
        try {
            fan.submitComplaint(null);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }
}
