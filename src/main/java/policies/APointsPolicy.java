package policies;

import controllers.LeagueSeasonController;
import league.Game;
import team.Team;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class APointsPolicy {
    public abstract double getPoints(Team team, LeagueSeasonController leagueSeasonController);

    protected List<Game> getPlayedGamesForTeam(Team team, LeagueSeasonController leagueSeasonController){
        List<Game> allGames = leagueSeasonController.getGames(team);

        // Get previous games of the team in the league's season
        List<Game> playedGames = new LinkedList<>();
        Date currentDate = new Date((new Date()).getTime() + 1000 * 60 * 95);
        for(Game g : allGames){
            if(g.getDate().after(currentDate))
                playedGames.add(g);
        }

        return playedGames;
    }
}
