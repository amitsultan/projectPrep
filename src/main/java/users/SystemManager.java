package users;

import dbhandler.Database;
import team.Team;
import java.util.HashSet;
import java.util.LinkedList;

public class SystemManager extends User {
    private static HashSet<Complaint> complaints=new HashSet<>();
    private Database db;


    public SystemManager(String firstName, String lastName, String userName, String password) throws Exception {
        super(firstName, lastName, userName, password);
        db=Database.getInstance();
    }

    public void addTeam(Team team)throws Exception{
        if(team==null)
            throw new Exception("null value detected");
        db.addTeam(this,team);
    }

    public void removeTeamPermanently(Team team)throws Exception{
        if(team==null)
            throw new Exception("null value detected");
        db.deleteTeam(this,team);
    }

    public void addSubscribe(User user)throws Exception{
        if(user==null)
            throw new Exception("null value detected");
        db.addUser(this,user);
    }

    public void removeSubscribe(User user)throws Exception{
        if(user==null)
            throw new Exception("null value detected");
        db.deleteUser(this,user);
    }

    public int getNumOfComplaints(){
        return complaints.size();
    }

    public static void addComplaint(Complaint com)throws Exception{
        if(com==null)
            throw new Exception("null value detected");
        complaints.add(com);
    }

    public void showAndHandleComplaints(){
        for(Complaint com: complaints){
            com.handle();
        }
        complaints.clear();
    }

    public LinkedList<String> showActionLog() throws Exception {
        LinkedList<String> log=null;
        return db.getLog(this);
    }

    public void turnOnRecommendation(){
        /* TODO */
    }

    public void initializeSystem()throws Exception{
        db.initialize(this);
    }

    public int totalNumberOfComplaints(){
        return complaints.size();
    }

    public int unSolvedComplaints(){
        int count = 0;
        for (Complaint c : complaints){
            if(!c.handle)
                count++;
        }
        return count;
    }
}
