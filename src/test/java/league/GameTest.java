package league;

import assets.Stadium;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team.Team;
import users.User;

import java.util.Date;

public class GameTest {
    private Game game;
    private Team host;
    private Team guest;
    private Stadium hostStadium;
    private Stadium guestStadium;
    private Referee mainReferee;
    private Referee regReferee1;
    private Referee regReferee2;
    private Referee regReferee3;
    private Referee regReferee4;
    private User refereeUser;
    private User regUser1;
    private User regUser2;
    private User regUser3;
    private User regUser4;
    private Event event;
    private Date date;

    @Before
    public void setUp() throws Exception {
        refereeUser = new User("name","lastName","userName","pass");
        regUser1 = new User("name","lastName","userName","pass");
        regUser2 = new User("name","lastName","userName","pass");
        regUser3 = new User("name","lastName","userName","pass");
        regUser4 = new User("name","lastName","userName","pass");
        regReferee1 = new Referee(regUser1,100,RefereeType.assistant);
        regReferee2 = new Referee(regUser2,100,RefereeType.assistant);
        regReferee3 = new Referee(regUser3,100,RefereeType.assistant);
        regReferee4 = new Referee(regUser4,100,RefereeType.assistant);
        Referee[] regular = new Referee[3];
        regular[0]= regReferee1;
        regular[1]= regReferee2;
        regular[2]= regReferee3;
        mainReferee = new Referee(refereeUser,500,RefereeType.main);
        hostStadium = new Stadium("st","place",100);
        guestStadium = new Stadium("st","place",100);
        host = new Team("host",hostStadium);
        guest = new Team("guest",guestStadium);
        date = new Date();
        game = new Game(host,guest,hostStadium,date, mainReferee,regular);
        event = new Event(new Date(),game,"details",regReferee1);
    }

    @Test
    public void addEvent() {
        try {
            game.addEvent(null);
            Assert.fail();
        }catch (Exception e){
            Assert.assertEquals("Event can't be null",e.getMessage());
        }
        try{
            game.addEvent(event);
            Assert.assertEquals(1,game.getGameEvents().size());
            game.addEvent(event);
            Assert.fail();
        }catch (Exception e){
            Assert.assertEquals("Event already exists",e.getMessage());
        }

    }

    @Test
    public void editEvent() {
        try {
            game.addEvent(event);
            game.editEvent(event.getID(), "new", new Date());
            Assert.assertEquals("new", game.getGameEvents().getFirst().getDetails());
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testEquals() {
        Referee[] regular = new Referee[3];
        regular[0]= regReferee1;
        regular[1]= regReferee2;
        regular[2]= regReferee3;
        try {
            Game game1 = new Game(host,guest,hostStadium,date, mainReferee,regular);
            Assert.assertEquals(game, game1);
            Game game4 = new Game(host,guest,guestStadium,date, mainReferee,regular);
            Assert.assertNotEquals(game, game4);
            regular[2]= regReferee4;
            Game game2 = new Game(host,guest,hostStadium,date, mainReferee,regular);
            Assert.assertNotEquals(game, game2);
            Assert.assertNotEquals(null, game1);
            Game game3 = new Game(guest,host,hostStadium,date, mainReferee,regular);
            Assert.assertNotEquals(game, game3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}