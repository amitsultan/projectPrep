package dbhandler;

import assets.Stadium;
import controllers.LeagueSeasonController;
import league.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team.Player;
import team.Team;
import users.FootballAssociationAgent;
import users.SystemManager;
import users.User;

public class DataBaseTest {

    private Database db = Database.getInstance();
    private Season season1;
    private Team team;
    private User user;
    private SystemManager manager;

    @Before
    public void setUp(){
        try {
            manager = new SystemManager("fName","lName","root","root");
            season1 = new Season(2014);
            Stadium stdm = new Stadium("name","place",10);
            team = new Team("swans",stdm);
            user = new User("fName","lName","root","root");
        } catch (Exception chairsNumberNotValid) {
            chairsNumberNotValid.printStackTrace();
        }
    }

    @Test
    public void testLeague(){
        Player p = new Player(10,10,team,user,season1);
        // Check that only football agent can temper with league
        try {
            db.addLeague(p,new League(LeagueType.PremierLeague));
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.getLeagues(p);
            Assert.fail("NoPrivileges Exception expected");
        }catch (Exception e){ }
        try {
            db.deleteLeague(p,new League(LeagueType.PremierLeague));
            Assert.fail("NoPrivileges Exception expected");
        } catch (NoPrivileges noPrivileges) { } catch (Exception e) {
            e.printStackTrace();
        }
        // check methods work properly with the correct agent
        try{
            FootballAssociationAgent agent = new FootballAssociationAgent("fName","lName","root","root");
            League l = new League(LeagueType.PremierLeague);
            Assert.assertTrue(db.addLeague(agent,l));
            Assert.assertEquals(1,db.getLeagues(agent).size());
            Assert.assertTrue(db.deleteLeague(agent,l));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testReferee(){
        Player p = new Player(10,10,team,user,season1);
        Referee ref = new Referee(user,125,RefereeType.main);
        // Check that only football agent can temper with referees
        try {
            db.addReferee(p,ref);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.getReferees(p);
            Assert.fail("NoPrivileges Exception expected");
        }catch (Exception e){ }
        try {
            db.deleteReferee(p,ref);
            Assert.fail("NoPrivileges Exception expected");
        } catch (NoPrivileges noPrivileges) { }
        // check methods work properly with the correct agent
        try{
            FootballAssociationAgent agent = new FootballAssociationAgent("fName","lName","root","root");
            Assert.assertTrue(db.addReferee(agent,ref));
            Assert.assertEquals(1,db.getReferees(agent).size());
            Assert.assertTrue(db.deleteReferee(agent,ref));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testUser(){
        // Check that only System Manager can temper with Users
        try {
            db.addUser(user,manager);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.getUsers(user);
            Assert.fail("NoPrivileges Exception expected");
        }catch (Exception e){ }
        try {
            db.deleteUser(user,manager);
            Assert.fail("NoPrivileges Exception expected");
        } catch (NoPrivileges noPrivileges) { }
        // check methods work properly with the correct sys manager
        try{
            Assert.assertTrue(db.addUser(manager,user));
            Assert.assertEquals(1,db.getUsers(manager).size());
            Assert.assertTrue(db.deleteUser(manager,user));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testTeam(){
        // Check that only System Manager can temper with Teams
        try {
            db.addTeam(user,team);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.getTeam(user);
            Assert.fail("NoPrivileges Exception expected");
        }catch (Exception e){ }
        try {
            db.deleteTeam(user,team);
            Assert.fail("NoPrivileges Exception expected");
        } catch (NoPrivileges noPrivileges) { }
        // check methods work properly with the correct sys manager
        try{
            Assert.assertTrue(db.addTeam(manager,team));
            Assert.assertEquals(1,db.getTeam(manager).size());
            Assert.assertTrue(db.deleteTeam(manager,team));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testLog(){
        try {
            db.getLog(user);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            Assert.assertNotNull(db.getLog(manager));
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testInitialize(){
        try {
            db.initialize(user);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.initialize(manager);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    public void testLeagueSeasonController() throws Exception {
        League league = new League(LeagueType.PremierLeague);
        // Check that only System Manager can temper with Teams
        try {
            db.addLeagueSeasonController(user,league,season1);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        try{
            db.getLeagueSeasonController(user,league,season1);
            Assert.fail("NoPrivileges Exception expected");
        }catch (Exception e){ }
        try {
            db.deleteLeagueSeasonController(user,null);
            Assert.fail("NoPrivileges Exception expected");
        } catch (Exception noPrivileges) { }
        // check methods work properly with the correct sys manager
        try{
            FootballAssociationAgent agent = new FootballAssociationAgent("fName","lName","root","root");
            Assert.assertTrue(db.addLeagueSeasonController(agent,league,season1));
            Assert.assertNotNull(db.getLeagueSeasonController(agent,league,season1));
            Assert.assertTrue(db.deleteLeagueSeasonController(agent,db.getLeagueSeasonController(agent,league,season1)));
        }catch (Exception e){
            Assert.fail();
        }
    }

}
