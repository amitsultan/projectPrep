package team;

import assets.Stadium;
import controllers.LeagueSeasonController;
import league.League;
import league.LeagueType;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.TeamPage;
import stubs.TeamStub;
import stubs.UserStub;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class TeamOwnerTest {
    private Stadium stadiumStub;
    private TeamStub teamStubforOwner;
    private TeamStub teamStub;
    private UserStub userStubforOwner;
    private UserStub userStubforPlayer;
    private UserStub user3;
    private TeamOwner teamOwner;
    private TeamOwner teamOwner2;
    private LeagueSeasonController controller;
    private League league;
    private Season season1;
    private Season season2;
    private Season season3;
    private Player player1;
    private Player player2;
    private TeamPage page;

    @Before
    public void setUp() throws Exception {
        stadiumStub = new Stadium("name","place",13265);
        teamStubforOwner = new TeamStub("MaccabiHaifa",stadiumStub);
        teamStub = new TeamStub("haPoel", stadiumStub);
        userStubforOwner = new UserStub();
        userStubforPlayer = new UserStub();
        user3 = new UserStub();
        teamOwner = new TeamOwner(teamStubforOwner,userStubforOwner);
        teamOwner2 = new TeamOwner(teamStub,user3);
        league = new League(LeagueType.LeumitA);
        season1 = new Season(2010);
        season2 = new Season(2011);
        season3 = new Season(2012);
        controller = new LeagueSeasonController(season1,league);
        season1.addLeague(league,controller);
        league.addSeasonController(2010,controller);
        player1 = new Player(12,100,teamStubforOwner,userStubforPlayer, season1);
        player2 = new Player(13,100,teamStub,userStubforPlayer, season2);
        Date date = new Date();
        page = new TeamPage(teamStubforOwner,"yes",date);
        teamStubforOwner.setPage(page);
    }

    @Test
    public void testAddAsset() {
        try {
            teamOwner.addAsset(player1, teamStubforOwner, season1);
            teamOwner.addAsset(player2,teamStubforOwner,season2);
            Player checkPlayer = teamStubforOwner.getPlayers().get(season1).getFirst();
            Assert.assertEquals(player1,checkPlayer);
        }catch (notOwnerOfTeam e1){
            Assert.fail();
        }
        try{
            teamOwner.addAsset(player2,teamStub, season1);
            Assert.fail();
        }catch (notOwnerOfTeam e2){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testUpdateAsset(){
        try {
            teamOwner.updateAsset(player2, teamStubforOwner);
        }catch (Exception e){
            Assert.fail();
        }
        try {
            teamOwner.updateAsset(player1, teamStub);
        }catch (Exception e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testRemoveAsset() {
        //should remove correctly
        try {
            teamOwner.addAsset(player1,teamStubforOwner,season1);
            teamOwner.addAsset(player2,teamStubforOwner,season2);
            teamOwner.removeAsset(player1,teamStubforOwner, season1);
            LinkedList<Player> players = teamStubforOwner.getPlayers().get(season1);
            for (Player player: players) {
                if(player.equals(player1)){
                    Assert.fail();
                }
            }
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        //should throw exception because this is not a team he owns
        try{
            teamOwner.removeAsset(player2,teamStub, season2);
        }catch (notOwnerOfTeam e){
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        //should throw exception because player2 is in season2
        try{
            teamOwner.removeAsset(player2,teamStubforOwner, season1);
        }catch (Exception e){
            Assert.assertEquals("Can't remove current asset because player doesn't exist",e.getMessage());
        }
        //should throw exception because player2 removed the first time
        try {
            try {
                teamOwner.removeAsset(player2, teamStubforOwner, season2);
                Assert.assertEquals(0, teamStubforOwner.getPlayers().get(season2).size());
                teamOwner.removeAsset(player2, teamStubforOwner, season2);
            } catch (removeAssetException e) {
                Assert.assertEquals("Can't remove current asset because player doesn't exist", e.getMessage());
            }
            //should throw exception because you cant remove a stadium from a team
            try {
                teamOwner.removeAsset(stadiumStub, teamStubforOwner, season2);
            } catch (removeAssetException e) {
                Assert.assertEquals("Can't remove current asset because team must have stadium", e.getMessage());
            }
            try {
                teamOwner.removeAsset(player1, teamStubforOwner, season3);
            } catch (removeAssetException e) {
                Assert.assertEquals("Can't remove current asset because the season doesn't exist", e.getMessage());
            }
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testAddTeamOwner() {
        try {
            //should throw exception because it isn't his team.
            try {
                teamOwner.addTeamOwner(userStubforPlayer, teamStub);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("You can't add team owner to a team you don't own ", e.getMessage());
            }
            try {
                TeamOwner play = teamOwner.addTeamOwner(userStubforPlayer, teamStubforOwner);
                Assert.assertEquals(2, teamStubforOwner.getOwners().size());
                Assert.assertTrue(teamStubforOwner.getOwners().contains(play));
                //add playerTeamOwner for the second time - should throw exception
                teamOwner.addTeamOwner(userStubforPlayer, teamStubforOwner);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("this user is already owner of this team", e.getMessage());
            }
            try {
                HashMap<Season, TeamManager> teamManager = teamStubforOwner.getManager();
                TeamManager manager = new TeamManager(user3, season1, teamStubforOwner, ManagerPermission.DEFAULT, teamOwner);
                teamManager.put(season1, manager);
                teamStubforOwner.setManager(teamManager);
                teamOwner.addTeamOwner(user3, teamStubforOwner);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("this user is already manager of this team", e.getMessage());
            }
        } catch (Exception e1) {
            Assert.fail();
        }
    }

    @Test
    public void testRemoveTeamOwner() {
        try {
            try {
                teamOwner.removeTeamOwner(teamOwner2, teamStub);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("You can't remove team owner to a team you not own ", e.getMessage());
            }
            try {
                teamOwner.removeTeamOwner(teamOwner2, teamStubforOwner);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("this owner is not an owner of this team", e.getMessage());
            }
            TeamOwner play = teamOwner.addTeamOwner(userStubforPlayer, teamStubforOwner);
            teamOwner.removeTeamOwner(play, teamStubforOwner);
            Assert.assertEquals(1, teamStubforOwner.getOwners().size());
            for (TeamOwner owner : teamStubforOwner.getOwners()) {
                if (owner.equals(play)) {
                    Assert.fail();
                }
            }
        }catch (Exception e1){
            Assert.fail();
        }
    }

    @Test
    public void testAddTeamManager() {
        try {
            teamOwner.addTeamManager(user3,teamStub,season1,ManagerPermission.DEFAULT);
        } catch (InvalidSubscription e) {
            Assert.assertEquals("You can't add team manager to a team you don't own ", e.getMessage());
        }
        try{
            teamOwner.addTeamManager(user3,teamStubforOwner,season1,ManagerPermission.DEFAULT);
            Assert.assertEquals(user3,teamStubforOwner.getManager().get(season1).user);
            teamOwner.addTeamManager(user3,teamStubforOwner,season1,ManagerPermission.DEFAULT);
        } catch (InvalidSubscription e){
            Assert.assertEquals("This user is already a manager or owner of this team",e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManager() {
        try {
            TeamManager mani = teamOwner.addTeamManager(user3, teamStubforOwner, season1, ManagerPermission.DEFAULT);
            try {
                teamOwner.removeTeamManager(mani, teamStub, season1);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("You can't remove team manager from a team you don't own", e.getMessage());
            }
            try {
                teamOwner2.removeTeamManager(mani, teamStub, season3);
            } catch (InvalidSubscription e) {
                Assert.assertEquals("Can't remove manager you didn't subscribed", e.getMessage());
            }
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testRemoveTeam() {
        try {
            teamOwner.removeTeam(teamStub);
        }catch (InvalidSubscription e){
            Assert.assertEquals("You can't remove team you don't own",e.getMessage());
        }
        try{
            teamOwner.removeTeam(teamStubforOwner);
            Assert.assertEquals(Status.NOTACTIVE,teamStubforOwner.getStatus());
            teamOwner.removeTeam(teamStubforOwner);
        }catch (InvalidSubscription e){
            Assert.assertEquals("This team is already closed",e.getMessage());
        }
    }

    @Test
    public void testReactivateTeam() {
        try {
            teamOwner.reactivateTeam(teamStub);
        }catch (InvalidSubscription e){
            Assert.assertEquals("You can't reactivate team you don't own",e.getMessage());
        }
        try{
            teamOwner.removeTeam(teamStubforOwner);
            teamOwner.reactivateTeam(teamStubforOwner);
            Assert.assertEquals(Status.ACTIVE,teamStubforOwner.getStatus());
            teamOwner.reactivateTeam(teamStubforOwner);
        }catch (InvalidSubscription e){
            Assert.assertEquals("This team is already opened",e.getMessage());
        }
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(teamOwner,teamOwner);
        Assert.assertNotEquals(teamOwner,teamOwner2);
    }
}
