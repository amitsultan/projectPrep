package policies;

import controllers.LeagueSeasonController;
import league.Game;
import team.Team;

import java.util.List;

public class SimplePointsPolicy extends APointsPolicy{
    @Override
    public double getPoints(Team team, LeagueSeasonController leagueSeasonController) {
        List<Game> playedGames = getPlayedGamesForTeam(team, leagueSeasonController);
        int points = 0;
        for(Game g: playedGames){
            int hostGoals = g.getScore()[0];
            int guestGoals = g.getScore()[1];
            if(team.equals(g.getHost()) && hostGoals > guestGoals)
                points += 3;
            else if(team.equals(g.getGuest()) && guestGoals > hostGoals)
                points += 3;
            else if(hostGoals == guestGoals)
                points += 1;
        }
        return points;
    }
}
