package controllers;

import assets.Stadium;
import league.*;
import org.junit.Assert;
import org.junit.Test;
import team.Coach;
import team.CoachType;
import team.Staff;
import team.Team;
import users.User;

import java.util.Date;
import java.util.LinkedList;

public class LeagueSeasonControllerTest {

    private League league;
    private Season season;

    @Test
    public void testConstructor(){
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            new LeagueSeasonController(season, league);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            new LeagueSeasonController(season, league);
            Assert.fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void addGame() {
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
            Assert.assertTrue(leagueSeasonController.leagueEquals(league));
            Assert.assertFalse(leagueSeasonController.leagueEquals(null));
            Assert.assertFalse(leagueSeasonController.leagueEquals(new League(LeagueType.LeumitA)));
            Assert.assertTrue(leagueSeasonController.seasonEquals(season));
            Assert.assertFalse(leagueSeasonController.seasonEquals(null));
            Assert.assertFalse(leagueSeasonController.seasonEquals(new Season(2020)));
        } catch (Exception e) {
            Assert.fail();
        }
        Game game = null;
        try {
            Stadium std1 = new Stadium("stadium1", "noWhere", 2);
            Stadium std2 = new Stadium("stadium2", "noWhere", 2);
            Team home = new Team("home", std1);
            Team guest = new Team("guest", std2);
            User user = new User("ref1", "", "hello", "1234");
            User user2 = new User("ref2", "", "hello", "1234");
            User user3 = new User("ref2", "", "hello", "1234");
            User user4 = new User("ref2", "", "hello", "1234");
            Referee referee = new Referee(user, 12345, RefereeType.main);
            Referee ref1 = new Referee(user2, 12345, RefereeType.assistant);
            Referee ref2 = new Referee(user3, 12345, RefereeType.assistant);
            Referee ref3 = new Referee(user4, 12345, RefereeType.assistant);
            Referee[] refs = new Referee[3];
            refs[0] = ref1;
            refs[1] = ref2;
            refs[2] = ref3;
            game = new Game(home, guest, std1, new Date(), referee, refs);
        } catch (Exception e){
            Assert.fail();
        }
        try {
            Assert.assertTrue(leagueSeasonController.addGame(game));
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            Assert.assertFalse(leagueSeasonController.addGame(game));
        } catch (Exception ignored) {
        }
        try {
            leagueSeasonController.addGame(null);
            Assert.fail();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void addTeam() {
        Team team = null;
        try {
            Stadium std = new Stadium("stadium1", "noWhere", 2);
            team = new Team("team", std);
        } catch (Exception e){
            Assert.fail();
        }
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            Assert.assertTrue(leagueSeasonController.addTeam(team));
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            Assert.assertFalse(leagueSeasonController.addTeam(team));
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            leagueSeasonController.addTeam(null);
            Assert.fail();
        } catch (Exception ignored) {

        }
    }

    @Test
    public void assignStaff() {
        LinkedList<Staff> staff = new LinkedList<>();
        Team team = null;
        try {
            User user = new User("coach", "", "hello", "1234");
            Stadium std = new Stadium("stadium1", "noWhere", 2);
            team = new Team("team", std);
            Coach coach = new Coach(user, new Season(2019), team, CoachType.Main);
            staff.add(coach);
        } catch (Exception e){
            Assert.fail();
        }
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController.assignStaff(team, staff);
        } catch (Exception e) {
            Assert.fail();
        }
        try {
            Assert.assertFalse(leagueSeasonController.assignStaff(null, staff));
        } catch (Exception ignored) {

        }
        try {
            Assert.assertFalse(leagueSeasonController.assignStaff(team, null));
        } catch (Exception ignored) {

        }
        try {
            User user = new User("coach2", "", "hello", "1234");
            Coach coach = new Coach(user, new Season(2019), team, CoachType.Assistant);
            staff = new LinkedList<>(staff);
            staff.add(coach);
        } catch (Exception e){
            Assert.fail();
        }
        try{
            leagueSeasonController.assignStaff(team, staff);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getGames() {
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
        } catch (Exception e) {
            Assert.fail();
        }
        Game game1 = null;
        Game game2 = null;
        try {
            Stadium std1 = new Stadium("stadium1", "noWhere", 2);
            Stadium std2 = new Stadium("stadium2", "noWhere", 2);
            Team home = new Team("home", std1);
            Team guest = new Team("guest", std2);
            User user = new User("ref1", "", "hello", "1234");
            User user2 = new User("ref2", "", "hello", "1234");
            User user3 = new User("ref2", "", "hello", "1234");
            User user4 = new User("ref2", "", "hello", "1234");
            Referee referee = new Referee(user, 12345, RefereeType.main);
            Referee ref1 = new Referee(user2, 12345, RefereeType.assistant);
            Referee ref2 = new Referee(user3, 12345, RefereeType.assistant);
            Referee ref3 = new Referee(user4, 12345, RefereeType.assistant);
            Referee[] refs = new Referee[3];
            refs[0] = ref1;
            refs[1] = ref2;
            refs[2] = ref3;
            game1 = new Game(home, guest, std1, new Date(), referee, refs);
            game2 = new Game(guest, home, std1, new Date(), referee, refs);
        } catch (Exception e){
            Assert.fail();
        }
        try {
            Assert.assertTrue(leagueSeasonController.addGame(game1));
            Assert.assertTrue(leagueSeasonController.addGame(game2));
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(2, leagueSeasonController.getGames(game1.getHost()).size());
    }

    @Test
    public void addReferee() {
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
        } catch (Exception e) {
            Assert.fail();
        }
        User user = null;
        try {
            user = new User("ref", "", "hello", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(leagueSeasonController.addReferee(new Referee(user, 20, RefereeType.main)));
        } catch (Exception ignored){
        }
        try {
            Assert.assertFalse(leagueSeasonController.addReferee(new Referee(user, 20, RefereeType.main)));
        } catch (Exception ignored){
        }
    }

    @Test
    public void testEquals() {
        LeagueSeasonController leagueSeasonController = null;
        try {
            league = new League(LeagueType.PremierLeague);
            season = new Season(2019);
        } catch (Exception e) {
            Assert.fail();
        }
        try{
            leagueSeasonController = new LeagueSeasonController(season, league);
            Assert.assertEquals(leagueSeasonController, leagueSeasonController);
            Assert.assertNotEquals(leagueSeasonController, null);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
