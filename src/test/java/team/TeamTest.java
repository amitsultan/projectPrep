package team;

import assets.Stadium;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.TeamPage;
import users.User;

import java.util.Date;


public class TeamTest {
    private Stadium stadium;
    private Coach coach;
    private Player player;
    private Player player2;
    private Team team;
    private Season season1;
    private Season season2;
    private User user1;
    private User user2;
    private TeamOwner teamOwner;

    @Before
    public void setUp() throws Exception{
        stadium = new Stadium("stadium","Beer-Sheva",100);
        team = new Team("Hapoel",stadium);
        season1 = new Season(2010);
        season2 = new Season(2011);
        user1 = new User("Alice","Bob","AliceBob","no");
        user2 = new User("Alice","Bob","AliceBob","no");
        coach = new Coach(user1,season1,team,CoachType.Assistant);
        player = new Player(11,10,team,user2,season1);
        player2 = new Player(10,10,team,user1,season2);
        teamOwner = new TeamOwner(team,user1);
    }

    @Test
    public void addAsset() {
        team.addAsset(coach,season1);
        Assert.assertEquals(coach,team.getCoach().get(season1));
        team.addAsset(player,season1);
        Assert.assertEquals(player,team.getPlayers().get(season1).getFirst());
        team.addAsset(stadium,season1);
        Assert.assertEquals(stadium,team.getStadium());
        try {
            team.addAsset(null, season1);
            Assert.fail();
        }catch (NullPointerException e){
            Assert.assertTrue(true);
        }
        try {
            team.addAsset(coach, null);
            Assert.fail();
        }catch (NullPointerException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeAsset() {
        try {
            team.addAsset(coach, season1);
            team.removeAsset(coach, season1);
            Assert.assertNull(team.getCoach().get(season1));
            try {
                team.removeAsset(coach, season2);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertEquals("Can't remove current asset because the season doesn't exist", e.getMessage());
            }
            team.addAsset(player, season1);
            try {
                team.removeAsset(player, season2);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertEquals("Can't remove current asset because the season doesn't exist", e.getMessage());
            }
            team.addAsset(player2, season2);
            team.removeAsset(player, season1);
            Assert.assertEquals(0, team.getPlayers().get(season1).size());
            try {
                team.removeAsset(player, season2);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertEquals("Can't remove current asset because player doesn't exist", e.getMessage());
            }
            try {
                team.removeAsset(stadium, season2);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertEquals("Can't remove current asset because team must have stadium", e.getMessage());
            }
            try{
                team.addAsset(null,season2);
                Assert.fail();
            }catch (NullPointerException e){
                Assert.assertTrue(true);
            }
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void addTeamOwner() {
        team.addTeamOwner(teamOwner);
        team.addTeamOwner(teamOwner);
        Assert.assertEquals(1, team.getOwners().size());
        try {
            team.addTeamOwner(null);
            Assert.fail();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeTeamOwner() {
        team.addTeamOwner(teamOwner);
        team.removeTeamOwner(teamOwner);
        Assert.assertEquals(1, team.getOwners().size());
        try {
            team.removeTeamOwner(null);
            Assert.fail();
        } catch (NullPointerException n) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void checkAvailability() {
        try {
            TeamManager manager = new TeamManager(user1, season1, team, ManagerPermission.ASSISTENT, teamOwner);
            team.addTeamOwner(teamOwner);
            team.addManager(manager, season1);
            Assert.assertFalse(team.checkAvailability(teamOwner.getUser()));
            Assert.assertFalse(team.checkAvailability(manager.getUser()));
            Assert.assertTrue(team.checkAvailability(user2));
            try {
                team.addTeamOwner(null);
                Assert.fail();
            } catch (NullPointerException e) {
                Assert.assertTrue(true);
            }
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void addPersonalPage() {
        TeamPage page = new TeamPage(team,"fff",new Date());
        team.addPersonalPage(page);
        Assert.assertTrue(team.getPage()!=null);
        Assert.assertEquals("fff",team.getPage().getDescription());
    }

    @Test
    public void addManager() {
        TeamManager manager = new TeamManager(user1, season1, team, ManagerPermission.ASSISTENT, teamOwner);
        team.addManager(manager,season1);
        Assert.assertEquals(manager,team.getManager().get(season1));
        try{
            team.addManager(null,season1);
            Assert.fail();
        }catch (NullPointerException e){
            Assert.assertEquals("all values must not be null",e.getMessage());
        }
    }

    @Test
    public void removeTeamManager() {
        TeamManager manager = new TeamManager(user1, season1, team, ManagerPermission.ASSISTENT, teamOwner);
        team.addManager(manager,season1);
        try{
            team.removeTeamManager(manager,season2);
            Assert.fail();
        }catch (Exception e){
            Assert.assertEquals("This team manager is not on this season",e.getMessage());
        }
        try {
            team.removeTeamManager(manager, season1);
        }catch (Exception e){
            Assert.fail();
        }
        try{
            team.removeTeamManager(manager,null);
            Assert.fail();
        }catch (NullPointerException e){
            Assert.assertEquals("all values must not be null",e.getMessage());
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void updateAsset() {
    }
}
