package league;

import controllers.LeagueSeasonController;
import dbhandler.Connector;
import team.SameControllerToDifferentLeagues;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Season {
    private HashMap<League, LeagueSeasonController> leagueMap;
    private static AtomicInteger count;
    private int seasonId;

    static {
        try {
            Connection conn = Connector.getInstance().establishConnection();
            ResultSet rs = Connector.getInstance().selectQuery(conn, "Select ID from season");
            int max = 0;
            while (rs.next()) {
                max = Math.max(max, rs.getInt("ID"));
            }
            count = new AtomicInteger(max);
            conn.close();
        } catch (Exception e) {
            count = new AtomicInteger(0);
        }
    }

    public Season() {
        leagueMap = new HashMap<League, LeagueSeasonController>();
        seasonId = count.incrementAndGet();
    }

    public HashMap<League, LeagueSeasonController> getLeagueMap() {
        return leagueMap;
    }

    public void setLeagueMap(HashMap<League, LeagueSeasonController> leagueMap) {
        this.leagueMap = leagueMap;
    }

    public void addLeague(League league, LeagueSeasonController controller) throws Exception {
        if(league.equals(null) || controller.equals(null)){
            throw new Exception("League or Controller is null");
        }
        if(leagueMap.containsKey(league)){
            throw new Exception("League is already connected to this season");
        }
        for (League l: leagueMap.keySet()) {
            if(leagueMap.get(l).equals(controller)){
                throw new SameControllerToDifferentLeagues("The controller must be unique for each league&Season pair");
            }
        }
        leagueMap.put(league,controller);
    }

    public int getSeasonId() {
        return seasonId;
    }

}
