package league;

import dbhandler.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Event {

    private static AtomicInteger count;
    private int ID;
    private Date date;
    private Game game;
    private String details;
    private Referee assigned;


    static{
        try{
            Connection conn = Connector.getInstance().establishConnection();
            ResultSet rs = Connector.getInstance().selectQuery(conn,"Select ID from event");
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

    public Event(Date date, Game game, String details,Referee assigned){
        this.ID = count.incrementAndGet();
        this.date = date;
        this.game = game;
        this.details = details;
        this.assigned = assigned;
    }

    public int getID(){
        return ID;
    }
    public boolean setDetails(Date date,String newDetails) throws TimeLimitPass {
        if(newDetails == null ||details.equals(newDetails))
            return false;
//        long diffInMillies = Math.abs(date.getTime() - this.date.getTime());
//        long diffInHours = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.HOURS);
//        if(diffInHours >= 5){
//            throw new TimeLimitPass();
//        }
        this.details = newDetails;
        return true;
    }
    @Override
    public String toString(){
        return "[ eventID = " + ID + " Date = " + date.toString() + " Details = " + details + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return ID == event.ID;
    }
}
