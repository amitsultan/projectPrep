package league;

import controllers.LeagueSeasonController;
import dbhandler.Connector;
import league.Season;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class League {

    private int ID;
    private static AtomicInteger count;
    private LeagueType type;
    private HashMap<Integer,Season> seasons;
    private HashMap<Integer, LeagueSeasonController> seasonControllers;

    static{
        try{
            Connection conn = Connector.getInstance().establishConnection();
            ResultSet rs = Connector.getInstance().selectQuery(conn,"Select ID from league");
            int max = 0;
            while(rs.next()){
                max = Math.max(max,rs.getInt("ID"));
            }
            count = new AtomicInteger(max);
            conn.close();
        }catch(Exception e){
            count = new AtomicInteger(0);
        }
    }

    public League(LeagueType type) throws Exception {
        if(type!=null) {
            this.ID = count.incrementAndGet();
            this.type = type;
            seasons = new HashMap<>();
            seasonControllers = new HashMap<>();
        }
        else throw new Exception("type must not be null");
    }

    public boolean addSeason(int year,Season season) throws Exception {
        if(season == null)
            throw new NullPointerException("Season cannot be null");
        if(season.getYear()!= year){
            throw new Exception("year must be equal to season year");
        }
        if(!seasons.containsKey(year)){
            seasons.put(year,season);
            return true;
        }
        return false;
    }

    public boolean addSeasonController(int year, LeagueSeasonController lcs)  {
        if(lcs == null)
            throw new NullPointerException("Season Controller cannot be null");
        if(!seasonControllers.containsKey(year)){
            seasonControllers.put(year,lcs);
            return true;
        }
        return false;
    }
    public LeagueType getType(){
        return this.type;
    }

    public int getID() {
        return ID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return ID == league.ID && type == league.type;
    }
}



