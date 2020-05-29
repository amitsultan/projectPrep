package policies;

import controllers.LeagueSeasonController;
import league.Game;
import league.Referee;
import league.RefereeType;
import team.Team;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OneGameGameSchedulingPolicy extends AGameSchedulingPolicy {
    @Override
    public List<Game> getScheduling(List<Team> teams, LeagueSeasonController leagueSeasonController) {
        try {
            List<Game> games = new LinkedList<>();
            int year = leagueSeasonController.getSeasonYear();
            Date nextGameDate = new Date(year, Calendar.JANUARY, 1);
            for (int i = 0; i < teams.size(); i++) {
                for (int j = i + 1; j < teams.size(); j++) {
                    games.add(new Game(teams.get(i), teams.get(j), teams.get(i).getStadium(), nextGameDate, getRandomMainRefereeForGame(leagueSeasonController), getRandomAssistantRefereesForGame(leagueSeasonController)));
                }
                nextGameDate = new Date(nextGameDate.getTime() + 1000 * 60 * 60 * 24);
            }
            return games;
        } catch (Exception e) {
            return null;
        }
    }
}
