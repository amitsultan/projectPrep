package users;

import dbhandler.Database;
import league.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import users.FootballAssociationAgent;

import java.util.LinkedList;


public class FootballAssociationAgentTest {

    private FootballAssociationAgent agent;
    private Database db = Database.getInstance();


    @Before
    public void setUp(){
        try {
            agent = new FootballAssociationAgent("fName","lName","root","root");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testLeague() throws Exception {
        for (int i = 0; i < 4; i++) {
            Assert.assertTrue(agent.addLeague(LeagueType.PremierLeague));
        }
        Assert.assertFalse(agent.addLeague(null));
        try {
            LinkedList<League> list = db.getLeagues(agent);
            for (int i = 0; i < list.size(); i++) {
                Assert.assertTrue(agent.removeLeague(list.get(i)));
            }
        } catch (Exception noPrivileges) {
            noPrivileges.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testAddSeasonToLeague() throws Exception {
        agent.addLeague(LeagueType.PremierLeague);
        agent.addLeague(LeagueType.LeumitA);
        try {
            LinkedList<League> list = db.getLeagues(agent);
            for (League l : list) {
                for (int i = -10; i < 10; i++) {
                    Season s = null;
                    try {
                        s = new Season(i);
                    }catch (Exception e){
                        Assert.assertEquals("Year cannot be a non positive number",e.getMessage());
                    }
                    if(i <= 0)
                        Assert.assertFalse(agent.addSeasonToLeague(l.getID(),i,s));
                    else
                        Assert.assertTrue(agent.addSeasonToLeague(l.getID(),i,s));
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testReferee(){
        // test addReferee
        Assert.assertFalse(agent.addReferee("first","last","user","",-62,RefereeType.main));
        for (int i = 1; i < 6; i++) {
            Assert.assertTrue(agent.addReferee("first"+i,"last"+i,"user"+i,""+i,1000*i,RefereeType.main));
        }
        Assert.assertFalse(agent.addReferee("first","last","user","",-62,null));
        //test add referee to league season
        try {
            LinkedList<Referee> refs = db.getReferees(agent);
            Season s = new Season(2015);
            League l = new League(LeagueType.PremierLeague);
            Assert.assertFalse(agent.addRefereeToLeagueSeason(l,s,null));
            agent.addLeagueSeasonController(s,l);
            for (int i = 0; i < 5; i++) {
                Referee ref = refs.get(i);
                Assert.assertTrue(agent.addRefereeToLeagueSeason(l,s,ref));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        // test delete
        try {
            Assert.assertFalse(agent.deleteReferee(null));
            LinkedList<Referee> refs = db.getReferees(agent);
            Assert.assertEquals(5, refs.size());
            for (int i = 0; i < 5; i++) {
                Assert.assertTrue(agent.deleteReferee(refs.get(0)));
            }
        } catch (Exception noPrivileges) {
            noPrivileges.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testAddLeagueSeasonController(){
        try {
            Season s = new Season(2015);
            League l = new League(LeagueType.PremierLeague);
            // one param is null
            Assert.assertFalse(agent.addLeagueSeasonController(null,l));
            Assert.assertFalse(agent.addLeagueSeasonController(s,null));
            // success
            Assert.assertTrue(agent.addLeagueSeasonController(s,l));
            // cannot add same lsc twice
            Assert.assertFalse(agent.addLeagueSeasonController(s,l));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
