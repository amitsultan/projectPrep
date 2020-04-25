package team;

import assets.Stadium;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.PlayerPage;
import users.User;

import java.util.Date;

public class PlayerTest {

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
    public void testChangeNumber(){
        Player p = null;
        try {
            p = new Player(10,10,team,user,season1);
        } catch (Exception e) {
            Assert.fail();
        }
        // first Assignment test
        Assert.assertEquals(10,p.getPlayerNumber());
        // negative number test
        p.changeNumber(-10);
        Assert.assertEquals(10,p.getPlayerNumber());
        // boundary test
        p.changeNumber(0);
        Assert.assertEquals(10,p.getPlayerNumber());
        // good input test
        p.changeNumber(14);
        Assert.assertEquals(14,p.getPlayerNumber());
    }

    @Test
    public void testAddPersonalPage(){
        Player p = null;
        try {
            p = new Player(10,10,team,user,season1);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertNull(p.page);
        p.addPersonalPage(null);
        Assert.assertNull(p.page);
        PlayerPage page1 = new PlayerPage(p,"page1",new Date());
        PlayerPage page2 = new PlayerPage(p,"page2",new Date());
        // we use hashcode to make sure the pages are different objects (playerPage check for same player)
        Assert.assertEquals(p.page.hashCode(),page1.hashCode());
        Assert.assertNotEquals(p.page.hashCode(),page2.hashCode());
    }
}
