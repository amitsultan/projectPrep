package controllers;

import league.Game;
import league.League;
import league.Season;
import league.Referee;
import team.Staff;
import team.Team;

import java.util.LinkedList;

public class LeagueSeasonController {
    private Season season;
    private League league;
    private LinkedList<Team> teams;
    private LinkedList<Staff> staff;
    private LinkedList<Referee> referees;
    private LinkedList<Game> games;
}
