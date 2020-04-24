package league;

import assets.Stadium;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team.Team;
import users.User;

import java.util.Date;

public class EventTest {
    private Game game;
    private Team guest;
    private Referee mainReferee;

    @Before
    public void setUp() throws Exception {
        User refereeUser = new User("name", "lastName", "userName", "pass");
        User regUser1 = new User("name", "lastName", "userName", "pass");
        User regUser2 = new User("name", "lastName", "userName", "pass");
        User regUser3 = new User("name", "lastName", "userName", "pass");
        Referee regReferee1 = new Referee(regUser1, 100, RefereeType.assistant);
        Referee regReferee2 = new Referee(regUser2, 100, RefereeType.assistant);
        Referee regReferee3 = new Referee(regUser3, 100, RefereeType.assistant);
        Referee[] regular = new Referee[3];
        regular[0] = regReferee1;
        regular[1] = regReferee2;
        regular[2] = regReferee3;
        mainReferee = new Referee(refereeUser, 500, RefereeType.main);
        Stadium hostStadium = new Stadium("st", "place", 100);
        Team host = new Team("host", hostStadium);
        guest = new Team("guest",hostStadium);
        game = new Game(host, guest, hostStadium, new Date(), mainReferee, regular);
    }

    @Test
    public void testDetails(){
        // check basic constructor
        Event event = new Event(new Date(),game,"details",mainReferee);
        Assert.assertEquals("details",event.getDetails());
        // check wrong input
        Assert.assertFalse(event.setDetails(null));
        Assert.assertFalse(event.setDetails("details"));
        // check correct change
        Assert.assertTrue(event.setDetails("new"));
        Assert.assertEquals("new",event.getDetails());
    }

    @Test
    public void  testEquals(){
        Event event1,event2;
        // diff dates
        event1 = new Event(new Date(1,1,1),game,"details",mainReferee);
        event2 = new Event(new Date(1,1,2),game,"details",mainReferee);
        Assert.assertNotEquals(event1, event2);
        // diff details
        event1 = new Event(new Date(1,1,1),game,"details1",mainReferee);
        event2 = new Event(new Date(1,1,1),game,"details2",mainReferee);
        Assert.assertNotEquals(event1, event2);
        // all same diff ID
        event1 = new Event(new Date(1,1,1),game,"details",mainReferee);
        event2 = new Event(new Date(1,1,1),game,"details",mainReferee);
        Assert.assertNotEquals(event1, event2);
    }

}
