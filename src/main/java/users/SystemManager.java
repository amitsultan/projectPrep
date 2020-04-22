package users;

import team.Team;
import java.util.HashSet;

public class SystemManager extends User {

    private HashSet<Team> teams;
    private HashSet<User> users;
    private HashSet<complaint> complaints;


    public SystemManager(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
        teams=new HashSet<>();
        users=new HashSet<>();
    }

    public void removeTeamPermanentily(Team team){
        teams.remove(team);
    }

    public void removeSubscribe(User user){
        users.remove(user);
    }

    public void addComplaint(complaint com){
        this.complaints.add(com);
    }

    public void showAndHandleComplaints(){
        for(complaint com: complaints){
            com.handle();
            complaints.remove(com);
        }
    }

    public void showActionLog(){
        
    }



}
