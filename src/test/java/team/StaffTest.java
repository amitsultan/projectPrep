package team;

import assets.Stadium;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import users.User;

import java.util.List;

public class StaffTest {


    private Season season1,season2;
    private Team team1,team2;
    private User user;

    @Before
    public void setUp(){
        try {
            season1 = new Season(2014);
            season2 = new Season(2015);
            Stadium stdm = new Stadium("name","place",10);
            team1 = new Team("swans",stdm);
            team2 = new Team("drors",stdm);
            user = new User("fName","lName","root","root");
        } catch (Exception chairsNumberNotValid) {
            chairsNumberNotValid.printStackTrace();
        }
    }
    @Test
    public void testChangeTeam(){
        Staff p = new Player(10,10,team1,user,season1);
        Assert.assertEquals(team1,p.currentTeam);
        p.changeTeam(team2,season2);
        Assert.assertEquals(team2,p.currentTeam);
        Assert.assertFalse(p.changeTeam(team1,season2));
    }

    @Test
    public void testGetSeasonByTeam(){
        Staff p = new Player(10,10,team1,user,season1);
        Assert.assertEquals(team1,p.currentTeam);
        List<Season> seasonList = p.getSeasonByTeam(team1);
        Assert.assertTrue(seasonList.contains(season1));

        //change player team
        p.changeTeam(team2,season2);
        // check if season2 not in team1 results
        seasonList = p.getSeasonByTeam(team1);
        Assert.assertTrue(!seasonList.contains(season2));
        // check for 2 history entries at player history
        Assert.assertEquals(2, p.teamHistory.size());
    }

    @Test
    public void testGetTeamBySeason(){
        Staff p = new Player(10,10,team1,user,season1);
        Assert.assertNull(p.getTeamBySeason(season2));
        Assert.assertEquals(team1,p.getTeamBySeason(season1));
    }

}
