package policies;

import controllers.LeagueSeasonController;
import league.Game;
import league.Referee;
import league.RefereeType;
import team.Team;

import java.util.LinkedList;
import java.util.List;

public abstract class AGameSchedulingPolicy {
    public abstract List<Game> getScheduling(List<Team> teams, LeagueSeasonController leagueSeasonController);

    protected Referee[] getRandomAssistantRefereesForGame(LeagueSeasonController leagueSeasonController){
        List<Referee> leagueSeasonControllerReferees = leagueSeasonController.getReferees();
        List<Referee> leagueSeasonControllerAssistantReferees = new LinkedList<>();

        // Get only assistant referees
        for(Referee r : leagueSeasonControllerReferees){
            if(r.getType() == RefereeType.assistant)
                leagueSeasonControllerAssistantReferees.add(r);
        }

        Referee[] referees = new Referee[2];

        // Set first assistant referee
        int random = (int) (Math.random() * leagueSeasonControllerAssistantReferees.size());
        referees[0] = leagueSeasonControllerAssistantReferees.get(random);
        leagueSeasonControllerAssistantReferees.remove(random);

        // Set second assistant referee
        random = (int) (Math.random() * leagueSeasonControllerAssistantReferees.size());
        referees[1] = leagueSeasonControllerAssistantReferees.get(random);

        return referees;
    }

    protected Referee getRandomMainRefereeForGame(LeagueSeasonController leagueSeasonController){
        List<Referee> leagueSeasonControllerReferees = leagueSeasonController.getReferees();
        List<Referee> leagueSeasonControllerMainReferees = new LinkedList<>();

        // Get only main referees
        for(Referee r : leagueSeasonControllerReferees){
            if(r.getType() == RefereeType.main)
                leagueSeasonControllerMainReferees.add(r);
        }

        // Get a random main referee
        int random = (int) (Math.random() * leagueSeasonControllerMainReferees.size());

        return leagueSeasonControllerMainReferees.get(random);
    }
}
