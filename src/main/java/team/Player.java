package team;

import controllers.LeagueSeasonController;

import java.util.LinkedHashMap;

public class Player extends Staff {

    protected int number;

    /**
     * Constructor assign player to a team and save basic
     * details about the player such as full name,number,ID
     * and
     * @param firstN - first name
     * @param lastN - last name
     * @param ID - ID of the player
     * @param owners - team that owns the player
     * @param number - Player number
     * @throws Exception - IncorrectPlayerDetails - Player name incorrect
     * @throws Exception - NullPointerException - Player must be in a valid team
     */
    public Player(String firstN, String lastN, int ID, Team owners, int number, int salary, Team team, LeagueSeasonController seasonController) throws Exception {
        if((firstN == null || lastN == null) || (firstN.isEmpty() || lastN.isEmpty()))
            throw new IncorrectPlayerDetails("Player name must consists of valid strings!");
        this.firstName = firstN;
        this.lastName = lastN;
        this.ID = ID;
        if(team == null)
            throw new NullPointerException("team cannot be null!");
        this.teamHistory = new LinkedHashMap<LeagueSeasonController,Team>();
        teamHistory.put(seasonController,team);
        this.number = number;
        this.salary = salary;
    }

    
    public int getPlayerNumber(){
        return number;
    }
}
