package team;

import assets.Stadium;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import users.User;

public class CoachTest {

    private Season season1;
    private Team team;
    private User user;

    @Before
    public void setUp(){
        try {
            season1 = new Season(2014);
            Stadium stdm = new Stadium("name","place",10);
            team = new Team("swans",stdm);
            user = new User("fName","lName","root","root");
        } catch (Exception chairsNumberNotValid) {
            chairsNumberNotValid.printStackTrace();
        }
    }

    @Test
    public void testSetType(){
        Coach coach = null;
        try {
            coach = new Coach(user,season1,team, CoachType.Fitness);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(CoachType.Fitness,coach.getType());
        coach.setType(CoachType.Main);
        Assert.assertEquals(CoachType.Main,coach.getType());
    }

    @Test
    public void testEquals(){
        Coach coach1 = null;
        try {
            coach1 = new Coach(user,season1,team, CoachType.Fitness);
        } catch (Exception e) {
            Assert.fail();
        }
        User user2 = null;
        try {
            user2 = new User("fName","lName","root","root");
        } catch (Exception e) {
            Assert.fail();
        }
        Coach coach2 = null;
        try {
            coach2 = new Coach(user2,season1,team, CoachType.Main);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNotEquals(coach1, coach2);
    }
}
