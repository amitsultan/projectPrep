package league;

import assets.Stadium;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team.Team;
import users.User;

import java.util.Date;

public class RefereeTest {

    private User user;
    private Referee referee;
    private Game game;

    @Before
    public void setUp() {
        try {
            user = new User("fName","lName","root","root");
            Stadium std1 = new Stadium("stadium1", "noWhere", 2);
            Stadium std2 = new Stadium("stadium2", "noWhere",2);
            Team home = new Team("home", std1);
            Team guest = new Team("guest", std2);
            user = new User("ref1", "", "hello", "1234");
            User user2 = new User("ref2", "", "hello", "1234");
            User user3 = new User("ref2", "", "hello", "1234");
            User user4 = new User("ref2", "", "hello", "1234");
            referee = new Referee(user, 12345, RefereeType.main);
            Referee ref1 = new Referee(user2, 12345, RefereeType.assistant);
            Referee ref2 = new Referee(user3, 12345, RefereeType.assistant);
            Referee ref3 = new Referee(user4, 12345, RefereeType.assistant);
            Referee[] refs = new Referee[3];
            refs[0] = ref1;
            refs[1] = ref2;
            refs[2] = ref3;
            game = new Game(home, guest, std1, new Date(), referee, refs);
            referee.addGame(game);
            for(Referee r : refs){
                r.addGame(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void addEvent() {
        // A legal event addition
        try {
            referee.addEvent(new Event(new Date(), game, "Event", referee), game);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        // Try to add an event to a game before the game starts
        try{
            game.getDate().setTime(new Date().getTime() + 1000 * 60);
            referee.addEvent(new Event(new Date(), game, "Event", referee), game);
            Assert.fail();
        } catch (Exception e){

        }
        // Try to add an event to a game that was finished
        try{
            game.getDate().setTime(new Date().getTime() - 1000 * 60 * 95);
            referee.addEvent(new Event(new Date(), game, "Event", referee), game);
            Assert.fail();
        } catch (Exception e){

        }
        // Try to add an event to a game, but pass null for event
        try{
            referee.addEvent(null, game);
            Assert.fail();
        } catch (Exception e){

        }
        // Try to add an event to a game, but pass null for game
        try{
            referee.addEvent(new Event(new Date(), game, "Event", referee), null);
            Assert.fail();
        } catch (Exception e){

        }
    }

    @Test
    public void addGame() {
        try{
            // Try to assign a game that the referee is already assigned to
            Assert.assertFalse(referee.addGame(game));
            // Try to assign a new game to the referee
            Assert.assertTrue(referee.addGame(new Game(game.getHost(), game.getGuest(), game.getStadium(), new Date(), game.getMainReferee(), game.getRegularReferees())));
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void editEvent() {
        Event event = new Event(new Date(), game, "Event", referee);
        // Try to edit an event that does not exist in the game
        try{
            game.getDate().setTime(new Date().getTime() - 1000 * 60 * 60 * 4);
            Assert.assertFalse(referee.editEvent(game, event.getID(), "Edit", new Date()));
        } catch (Exception e){

        }
        // Try to edit an event more than 5 hours after the game ended
        try{
            game.getDate().setTime(new Date().getTime());
            referee.addEvent(event, game);
            game.getDate().setTime(new Date().getTime() - 1000 * 60 * 60 * 7);
            referee.editEvent(game, event.getID(), "Edit", new Date());
            Assert.fail();
        } catch (Exception e){

        }
        // Try to edit an event before the game ended
        try{
            game.getDate().setTime(new Date().getTime() - 1000 * 60 * 85);
            referee.editEvent(game, event.getID(), "Edit", new Date());
            Assert.fail();
        } catch (Exception e){

        }
        // Try to edit an event legally
        try{
            game.getDate().setTime(new Date().getTime() - 1000 * 60 * 60 * 3);
            Assert.assertTrue(referee.editEvent(game, event.getID(), "Edit", new Date()));
        } catch (Exception e){

        }
    }

    @Test
    public void getGameReport() {
        // Try to generate a report to a game that is not assigned to the referee
        try{
            Game g = new Game(game.getHost(), game.getGuest(), game.getStadium(), new Date(), game.getMainReferee(), game.getRegularReferees());
            referee.getGameReport(g);
            Assert.fail();
        } catch (Exception e){

        }
    }

    @Test
    public void testEquals() {
        User user = new User("ref1", "", "hello", "1234");
        Referee ref = new Referee(user, 12345, RefereeType.assistant);
        // Try to compare two different referees
        Assert.assertNotEquals(referee, ref);
    }
}
