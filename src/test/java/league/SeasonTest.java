package league;

import controllers.LeagueSeasonController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SeasonTest {

    private League league;
    private Season season;

    @Before
    public void setUp() throws Exception {
        league = new League(LeagueType.PremierLeague);
        try {
            season = new Season(2014);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAddLeague(){
        try {
            season.addLeague(null,null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        LeagueSeasonController lsc = null;
        try {
            lsc = new LeagueSeasonController(season,league);
            season.addLeague(league,lsc);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1,season.leagueMap.size());
        try{
            LeagueSeasonController lsc2 = new LeagueSeasonController(season,league);
            season.addLeague(league,lsc2);
            Assert.fail();
        }catch (Exception e){
            Assert.assertTrue(true);
        }
    }
    @Test
    public void testControllersByType(){
        try {
            LeagueSeasonController lsc = new LeagueSeasonController(season,league);
            season.addLeague(league,lsc);
            League league2 = new League(LeagueType.PremierLeague);
            LeagueSeasonController lsc2 = new LeagueSeasonController(season,league2);

            season.addLeague(league2,lsc2);
            Assert.assertEquals(2,season.getControllersByType(LeagueType.PremierLeague).size());

            League league3 = new League(LeagueType.LeumitA);
            LeagueSeasonController lsc3 = new LeagueSeasonController(season,league3);
            season.addLeague(league3,lsc3);
            Assert.assertEquals(2,season.getControllersByType(LeagueType.PremierLeague).size());
            Assert.assertEquals(1,season.getControllersByType(LeagueType.LeumitA).size());
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void testEquals(){
        try {
            Season season2 = new Season(2014);
            Assert.assertNotEquals(season, season2);
            Season season3 = new Season(2015);
            Assert.assertNotEquals(season, season3);
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
