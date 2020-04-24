package league;

import controllers.LeagueSeasonController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SeasonTest {

    private League league;
    private Season season;

    @Before
    public void setUp(){
        league = new League(LeagueType.PremierLeague);
        season = new Season(2014);
    }

    @Test
    public void testControllersByLeague(){
        try {
            LeagueSeasonController lsc = new LeagueSeasonController(season,league);
            season.addLeague(league,lsc);
            League league2 = new League(LeagueType.LeumitA);
            LeagueSeasonController lsc2 = new LeagueSeasonController(season,league2);
            season.addLeague(league2,lsc2);
            Assert.assertEquals(2,league);
        } catch (Exception e) {
            Assert.fail();
        }

    }

}
