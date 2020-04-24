package league;

import controllers.LeagueSeasonController;
import dbhandler.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Season {
    protected HashMap<League, LeagueSeasonController> leagueMap;
    private static AtomicInteger count;
    private int seasonId;

    public int getYear() {
        return year;
    }

    private int year;

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

    public Season(int year) throws Exception {
        if(year <= 0){
            throw new Exception("Year cannot be a non positive number");
        }
        leagueMap = new HashMap<>();
        seasonId = count.incrementAndGet();
        this.year = year;
    }

    public HashSet<LeagueSeasonController> getControllersByType(LeagueType type){
        HashSet<LeagueSeasonController> set = new HashSet<>();
        for (League l : leagueMap.keySet()) {
            if(l.getType().equals(type))
                set.add(leagueMap.get(l));
        }
        return set;
    }

    public void addLeague(League league, LeagueSeasonController controller) throws Exception {
        if(league == null || controller == null){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return seasonId == season.seasonId &&
                year == season.year;
    }

}
