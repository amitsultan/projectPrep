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

    public void removeTeamPermanently(Team team){
        try {
            db.deleteTeam(this,team);
        } catch (Exception e) {
            e.printStackTrace();
        }    }

    public void removeSubscribe(User user){
        try {
            db.deleteUser(this,user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addComplaint(Complaint com){
        complaints.add(com);
    }

    public void showAndHandleComplaints(){
        for(Complaint com: complaints){
            com.handle();
            complaints.remove(com);
        }
    }

    public void showActionLog(){
        try {
            LinkedList<String> log = db.getLog(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOnRecommendation(){

    }

    public void initializeSystem(){
        try {
            db.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
