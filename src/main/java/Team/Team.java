package Team;
import Users.TeamOwner;

import java.util.HashMap;
import java.util.LinkedList;

public class Team {
    private String name;
    private Stadium stdm;
    private PersonalPage page;
    private HashMap<String, LeagueSeasonController> leagueSeasonController;
    private HashMap<String,Game> Games;
    private TeamOwner owner;
    private HashMap<LeagueSeasonController, TeamManager> manager;
    private HashMap<LeagueSeasonController, Coach> coach;
    private HashMap<LeagueSeasonController, LinkedList<Player>> players;

    public Team(String name, Stadium stdm, PersonalPage page, TeamOwner owner){
        this.name=name;
        this.stdm=stdm;
        this.page=page;
        this.owner=owner;
    }

    public String getName(){
        return this.name;
    }


}
