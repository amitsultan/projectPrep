package users;

import assets.Stadium;
import dbhandler.Database;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import team.Team;
import users.SystemManager;

import java.util.Date;

public class systemManagerTest {
    private SystemManager systemManager;
    Database db;
    Team team;

    @Before
    public void setUp() {
        try {
            systemManager=new SystemManager("Dor","Elkabetz","dorelakb","1234");
            Stadium std=new Stadium("Avi Ran", "Haifa", 30000);
            team=new Team("Maccabi Haifa",std);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db=Database.getInstance();
    }

    @Test
    public void addTeam(){
        try {
            this.systemManager.addTeam(null);
            Assert.fail();
        }catch (Exception e){
        }
        try {
            this.systemManager.addTeam(team);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(1,db.getNumOfTeams());
    }

    @Test
    public void testRemoveTeamPermanently(){
        try {
            this.systemManager.addTeam(team);
        } catch (Exception e) {
        }
        int n=db.getNumOfTeams();
        try {
            this.systemManager.removeTeamPermanently(null);
            Assert.fail();
        }catch (Exception e){
        }
        Stadium std1= null;
        Team team2=null;
        try {
            std1 = new Stadium("bla","bla",123);
            team2=new Team("blabla",std1);
        } catch (Exception e) {
        }
        try{
            this.systemManager.removeTeamPermanently(team2);
            Assert.fail();
        }
        catch (Exception e){
        }
        try{
            this.systemManager.removeTeamPermanently(team);
            Assert.assertEquals(db.getNumOfTeams(),n-1);
        }
        catch (Exception e){
        }
    }

    @Test
    public void testAddSubscribe(){
        try{
            this.systemManager.addSubscribe(null);
            Assert.fail();
        }
        catch (Exception e){
        }
        int n=db.getNumOfUsers();
        try {
            User user=new User("sdf","fds","fsdf","sdfsdfs");
            this.systemManager.addSubscribe(user);
            Assert.assertEquals(db.getNumOfUsers(),n+1);
        } catch (Exception e) {
            Assert.fail();;
        }
    }

    @Test
    public void removeSubscribe(){
        try{
            this.systemManager.removeSubscribe(null);
            Assert.fail();
        }
        catch (Exception e){
        }
        try {
            this.systemManager.removeTeamPermanently(null);
            Assert.fail();
        }catch (Exception e){
        }
        try {
            User user=new User("sdf","fds","fsdf","sdfsdfs");
            this.systemManager.addSubscribe(user);
            int n=db.getNumOfUsers();
            this.systemManager.removeSubscribe(user);
            Assert.assertEquals(db.getNumOfUsers(),n-1);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void TestShowAndHandleComplaints(){
        try {
            SystemManager.addComplaint(null);
            Assert.fail();
        } catch (Exception e) {
        }
        int n=this.systemManager.getNumOfComplaints();
        try {
            Complaint com=new Complaint("blabla",new Date(1992,12,12));
            SystemManager.addComplaint(com);
            Assert.assertEquals(this.systemManager.getNumOfComplaints(),n+1);
            Assert.assertEquals(this.systemManager.unSolvedComplaints(),n+1);
            this.systemManager.showAndHandleComplaints();
            Assert.assertEquals(this.systemManager.getNumOfComplaints(),0);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void TestShowActionLog(){
        try {
            int n = systemManager.showActionLog().size();
            db.addToLog("asdas");
            Assert.assertEquals(systemManager.showActionLog().size(), n + 1);
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void TestInitializeSystem(){
        try {
            systemManager.initializeSystem();
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
