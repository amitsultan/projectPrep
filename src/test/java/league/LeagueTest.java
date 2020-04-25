package league;

import controllers.LeagueSeasonController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeagueTest {
    private League league;


    @Before
    public void constructorTest(){
        try {
            league = new League(null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("type must not be null",e.getMessage());
        }
        try {
            league = new League(LeagueType.LeumitA);
        }catch (Exception e){
            Assert.fail();
        }

    }
    @Test
    public void addSeason() {
        Season season=null;
        try {
            season= new Season(2000);
            league.addSeason(2000,null);
        }catch (Exception e){
            Assert.assertEquals("Season cannot be null",e.getMessage());
        }
        try{
            Assert.assertTrue(league.addSeason(2000,season));
            Assert.assertFalse(league.addSeason(2000,season));
        }catch (Exception e){
            Assert.fail();
        }
        try{
            league.addSeason(2005,season);
        }catch (Exception e){
            Assert.assertEquals("year must be equal to season year",e.getMessage());
        }
    }

    @Test
    public void addSeasonController() {
        try{
            league.addSeasonController(2000,null);
            Assert.fail();
        }catch (Exception e){
            Assert.assertEquals("Season Controller cannot be null",e.getMessage());
        }
        try{
            Season season = new Season(2000);
            LeagueSeasonController controller = new LeagueSeasonController(season,league);
            Assert.assertTrue(league.addSeasonController(2010,controller));
            Assert.assertFalse(league.addSeasonController(2010,controller));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testEquals() throws Exception {
        League league1 = new League(LeagueType.LeumitA);
        Assert.assertTrue(league.equals(league));
        Assert.assertFalse(league.equals(league1));
        Assert.assertFalse(league1.equals(null));
    }
}