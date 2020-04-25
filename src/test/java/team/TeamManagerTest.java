package team;
import assets.Stadium;
import league.Season;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import users.User;

import static team.ManagerPermission.*;

public class TeamManagerTest {
    TeamOwner owner;
    Team team;
    TeamManager manager;
    TeamOwner owner2;
    Season season;
    @Before
    public void setUp(){
        try {
            Stadium std=new Stadium("Avi Ran", "Haifa", 30000);
            team=new Team("Maccabi Haifa", std);
            User user=new User("Yaakov","Shachar","Yankale","Yanaklech");
            owner=new TeamOwner(team,user);
            User user2=new User("Assaf","Ben Dov", "assi","123123");
            season=new Season(2020);
            manager=new TeamManager(user2,season,team,DEFAULT,owner);
            Team team2= new Team("happoel",std);
            User user3=new User("Yoav","Katz","sdas","asd");
            owner2=new TeamOwner(team2,user3);
            team.addManager(manager,season);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGetSuperior(){
        TeamOwner ownerTest=manager.getSuperior();
        Assert.assertTrue(ownerTest.equals(owner));
    }

    @Test
    public void testSetPremission(){
        Assert.assertFalse(manager.setPremission(OWNER,owner2.getUser()));
        Assert.assertTrue(manager.setPremission(OWNER,owner.getUser()));
    }

    @Test
    public void TestAddAsset(){
        try{
            manager.addAsset(null, null);
            Assert.fail();
        }catch (Exception e){
        }
        User playerUser= null;
        try {
            playerUser = new User("asd","dasd","asdasdas","123123");
            Player player=new Player(12,2000,team,playerUser,season);
            manager.setPremission(DEFAULT,owner.getUser());
            Assert.assertFalse(manager.addAsset(player,season));
            manager.setPremission(ASSISTENT,owner.getUser());
            Assert.assertTrue(manager.addAsset(player,season));
            manager.setPremission(OWNER,owner.getUser());
            Assert.assertTrue(manager.addAsset(player,season));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestUpdateAsset(){
        try{
            manager.updateAsset(null);
            Assert.fail();
        }catch (Exception e){
        }
        User playerUser= null;
        try {
            playerUser = new User("asd","dasd","asdasdas","123123");
            Player player=new Player(12,2000,team,playerUser,season);
            manager.setPremission(DEFAULT,owner.getUser());
            Assert.assertFalse(manager.updateAsset(player));
            manager.setPremission(ASSISTENT,owner.getUser());
            Assert.assertTrue(manager.updateAsset(player));
            manager.setPremission(OWNER,owner.getUser());
            Assert.assertTrue(manager.updateAsset(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeAsset(){
        try{
            manager.removeAsset(null,null);
            Assert.fail();
        }catch (Exception e){
        }
        User playerUser= null;
        try {
            playerUser = new User("asd","dasd","asdasdas","123123");
            Player player=new Player(12,2000,team,playerUser,season);
            manager.setPremission(DEFAULT,owner.getUser());
            Assert.assertFalse(manager.removeAsset(player,season));
            manager.setPremission(ASSISTENT,owner.getUser());
            Assert.assertTrue(manager.removeAsset(player,season));
            manager.setPremission(OWNER,owner.getUser());
            Assert.assertTrue(manager.removeAsset(player,season));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTeamOwner(){
        try{
            manager.addTeamOwner(null);
            Assert.fail();
        }catch (Exception e){
        }
        User ownerTest= null;
        try {
            ownerTest = new User("asdfsfsf","fsdfdf","asdasdas","123123");
            try {
                manager.setPremission(DEFAULT, owner.getUser());
                manager.addTeamOwner(ownerTest);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(ASSISTENT, owner.getUser());
                manager.addTeamOwner(ownerTest);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(OWNER, owner.getUser());
                manager.addTeamOwner(ownerTest);
            }
            catch(Exception e){
                Assert.fail();
            }
            try {
                manager.addTeamOwner(ownerTest);
                Assert.fail();
            }
            catch(Exception e){
            }
            try {
                manager.addTeamOwner(manager.getUser());
                Assert.fail();
            }
            catch(Exception e){
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveTeamOwner(){
        try{
            manager.removeTeamOwner(null);
            Assert.fail();
        }catch (Exception e){
        }
        User ownerTest;
        try {
            ownerTest = new User("dasdas","fsdfsdfdffdf","asdasdas","123123");
            manager.setPremission(OWNER,owner.getUser());
            manager.addTeamOwner(ownerTest);
            TeamOwner ownerTest2=null;
            for (TeamOwner owner:team.getOwners()) {
                if (owner.getUser().equals(ownerTest)) {
                    ownerTest2=owner;
                }
            }
            try {
                manager.setPremission(DEFAULT, owner.getUser());
                manager.removeTeamOwner(ownerTest2);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(ASSISTENT, owner.getUser());
                manager.removeTeamOwner(ownerTest2);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(OWNER, owner.getUser());
                manager.removeTeamOwner(ownerTest2);
            }
            catch(Exception e){
                Assert.fail();
            }
            try {
                manager.removeTeamOwner(ownerTest2);
                Assert.fail();
            }
            catch(Exception e){
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTeamManager(){
        try{
            manager.addTeamManager(null,null,null);
            Assert.fail();
        }catch (Exception e){
        }
        User managerTest;
        try {
            managerTest = new User("dasbdas","fsdbbdf","assddas","12d23");
            try {
                manager.setPremission(DEFAULT, owner.getUser());
                manager.addTeamManager(managerTest,season,DEFAULT);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(ASSISTENT, owner.getUser());
                manager.addTeamManager(managerTest,season,DEFAULT);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(OWNER, owner.getUser());
                manager.addTeamManager(managerTest,season,DEFAULT);
            }
            catch(Exception e){
                Assert.fail();
            }
            try {
                manager.addTeamManager(managerTest,season,DEFAULT);
                Assert.fail();
            }
            catch(Exception e){
            }
            try {
                manager.addTeamManager(owner.getUser(),season,DEFAULT);
                Assert.fail();
            }
            catch(Exception e){
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testRemoveTeamManager(){
        try{
            manager.removeTeamManager(null,null);
            Assert.fail();
        }catch (Exception e){
        }
        User managerTest;
        try {
            managerTest = new User("dasbdas","fsdbbdf","assddas","12d23");
            manager.setPremission(OWNER, owner.getUser());
            Season season2=new Season(2002);
            manager.addTeamManager(managerTest,season2,DEFAULT);
            TeamManager manTest=null;
            for (Season control :team.getManager().keySet()) {
                if(team.getManager().get(control).getUser().equals(managerTest)){
                    manTest=team.getManager().get(control);
                }
            }
            try {
                manager.setPremission(DEFAULT, owner.getUser());
                manager.removeTeamManager(manTest,season2);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(ASSISTENT, owner.getUser());
                manager.removeTeamManager(manTest,season2);
                Assert.fail();
            }catch(Exception e){
            }
            try {
                manager.setPremission(OWNER, owner.getUser());
                manager.removeTeamManager(manTest,season2);
            }
            catch(Exception e){
                Assert.fail();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
