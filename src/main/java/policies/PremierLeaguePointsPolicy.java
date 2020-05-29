package policies;

import controllers.LeagueSeasonController;
import league.Game;
import team.Team;

import java.util.List;

public class PremierLeaguePointsPolicy extends APointsPolicy {
    @Override
    public double getPoints(Team team, LeagueSeasonController leagueSeasonController) {
        int points = 0;
        List<Game> playedGames = getPlayedGamesForTeam(team, leagueSeasonController);

        // Calculate score: 3 points for a win and 1 point for a tie
        for(Game g : playedGames){
            int[] gameScore = g.getScore();
            if(g.getHost().equals(team) && gameScore[0] > gameScore[1])
                points += 3;
            else if(g.getGuest().equals(team) && gameScore[0] < gameScore[1])
                points += 3;
            else if (gameScore[0] == gameScore[1])
                points += 1;
        }

        return points;
    }
}
