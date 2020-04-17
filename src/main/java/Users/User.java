package main.java.Users;
import Team.TeamManager;
import main.java.Team.*;


public abstract class User {
    int userID;
    String name;
    boolean isManager;
    boolean isOwner;
    Team.TeamManager whoMakeManager;
    TeamOwner whoMakeOwner;

    /**
     *
     * @param userID
     * @param name
     * @param isManager
     * @param isOwner
     * @param whoMakeManager
     * @param whoMakeOwner
     */
    public User(int userID, String name, boolean isManager, boolean isOwner, TeamManager whoMakeManager, TeamOwner whoMakeOwner) {
        this.userID = userID;
        this.name = name;
        this.isManager = isManager;
        this.isOwner = isOwner;
        this.whoMakeManager = whoMakeManager;
        this.whoMakeOwner = whoMakeOwner;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public boolean isManager() {
        return isManager;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public TeamManager getWhoMakeManager() {
        return whoMakeManager;
    }

    public TeamOwner getWhoMakeOwner() {
        return whoMakeOwner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setWhoMakeManager(TeamManager whoMakeManager) {
        this.whoMakeManager = whoMakeManager;
    }

    public void setWhoMakeOwner(TeamOwner whoMakeOwner) {
        this.whoMakeOwner = whoMakeOwner;
    }

    public boolean canBeOwner(){
    }
}
