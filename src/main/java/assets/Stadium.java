package assets;

import dbhandler.Connector;
import team.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Stadium {
    private static AtomicInteger count;
    private int id;
    private String name;
    private Team owner;
    private int chairs;

    static{
        try{
            Connection conn = Connector.getInstance().establishConnection();
            ResultSet rs = Connector.getInstance().selectQuery(conn,"Select ID from stadium");
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


    public Stadium(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
    }

    public void setTeam(Team team){
        this.owner = team;
    }
    public int getId() {
        return id;
    }

    public Team getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Stadium))
            return false;
        Stadium stadium = (Stadium) object;
        return this.id == stadium.id;
    }
}
