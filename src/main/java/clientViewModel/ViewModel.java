package clientViewModel;
import dbhandler.Connector;
import external.BugLogger;
import league.EventType;
import users.User;
import external.logger;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class ViewModel {
    private ViewModel viewModel;
    private BufferedReader input;
    private static Connector connector = Connector.getInstance();
    private static logger logger = external.logger.getInstance();
    private static BugLogger bugLogger = BugLogger.getInstance();
    private static Socket socket;

    private ViewModel() {

    }

    public static Runnable handel(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String action = input.readLine();
            switch(action){
                case "login":{
                    login(clientSocket,input);
                    break;
                }
                case "register":{
                    register(clientSocket,input);
                    break;
                }
                case "checkRepresentetive":{
                    autherizeAssosicationAgent(clientSocket,input);
                    break;
                }
                case "make team":{
                    teamMaker(clientSocket,input);
                    break;
                }
                case "get stadiums":{
                    getStadiums(clientSocket,input);
                    break;
                }

                case "options":{
                    getOptions(clientSocket,input);
                    break;
                }
                case "getAllGamesIn5Hours":{
                    getAllGamesIn5Hours(clientSocket,input);
                    break;
                }
                case "getRefereeGames":{
                    getRefereeGames(clientSocket,input);
                    break;
                }
                case "addEventToGame":{
                    addEventToGame(clientSocket,input);
                    break;
                }
                case "getEventsOfGame":{
                    getEventsOfGame(clientSocket,input);
                    break;
                }
                case "addGameToFollow":{
                    addGameToFollow(clientSocket,input);
                    break;
                }
                case "setGameSchedulingPolicy":{
                    setGameSchedulingPolicy(clientSocket,input);
                    break;
                }
                case "setPointsPolicy":{
                    setPointsPolicy(clientSocket,input);
                    break;
                }
                case "getLeagues":{
                    getLeagues(clientSocket, input);
                    break;
                }
                case "getSeasons":{
                    getSeasons(clientSocket, input);
                    break;
                }
                default:{
                    break;
                }
            }
            input.close();
            long time = System.currentTimeMillis();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            bugLogger.log("Illegal server request!");
            //report exception somewhere.
            e.printStackTrace();
        }
        return null;
    }

    private static void getAllGamesIn5Hours(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Host, Guest, Date FROM game");
            ResultSet set = stmt.executeQuery();
            LinkedList<String> games = new LinkedList<>();
            Date currentDate = new Date();
            while(set.next()){
                String hostName = set.getString("Host");
                String guestName = set.getString("Guest");
                java.sql.Date gameDate = set.getDate("Date");
                if(!(gameDate.getTime() < currentDate.getTime() + 1000 * 60 * 390 || (gameDate.getTime() > currentDate.getTime() - 1000 * 60 * 90)))
                    continue;
                games.add(hostName + ',' + guestName + ',' + gameDate.toString());
            }
            oos.writeObject(games);
            oos.close();
            out.close();
            connector.closeConnection(conn);
            logger.log("Sent games to fan");
        } catch (Exception e) {
            bugLogger.log("Error on sending games to fan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getOptions(Socket clientSocket, BufferedReader input){
        try {
            PrintWriter output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Connection conn = connector.establishConnection();
            String id = input.readLine();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE userID=?");
            stmt.setString(1,id);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                String isRepresentetive = set.getString("isRepresentetive");
                if(Integer.parseInt(isRepresentetive)==1){
                    output.println("Football Association representetive");
                    logger.log("Assosication agent with id: "+id+" has been approved");
                }
            }
            stmt = conn.prepareStatement("SELECT * FROM teamowners WHERE OwnerID=?");
            stmt.setString(1,id);
            set = stmt.executeQuery();
            if(set.next()){
                output.println("Team owner");
                logger.log("Team owner with id: "+id+" has been approved");
            }
            stmt = conn.prepareStatement("SELECT * FROM teammanager WHERE userID=?");
            stmt.setString(1,id);
            set = stmt.executeQuery();
            if(set.next()){
                output.println("team manager");
                logger.log("team manager with id: "+id+" has been approved");
            }
            stmt = conn.prepareStatement("SELECT * FROM player WHERE userID=?");
            stmt.setString(1,id);
            set = stmt.executeQuery();
            if(set.next()){
                output.println("player");
                logger.log("player with id: "+id+" has been approved");
            }
            stmt = conn.prepareStatement("SELECT * FROM coach WHERE userID=?");
            stmt.setString(1,id);
            set = stmt.executeQuery();
            if(set.next()){
                output.println("coach");
                logger.log("coach with id: "+id+" has been approved");
            }
            stmt = conn.prepareStatement("SELECT * FROM referee WHERE userID=?");
            stmt.setString(1,id);
            set = stmt.executeQuery();
            if(set.next()){
                output.println("referee");
                logger.log("referee with id: "+id+" has been approved");
            }
            output.println("fan");
            output.close();
            connector.closeConnection(conn);
        }  catch (IOException e) {
            bugLogger.log("IO exception occurred : "+e.getMessage());
            e.printStackTrace();
        } catch (SQLException throwables) {
            bugLogger.log("SQL exception occurred : "+throwables.getMessage());
            throwables.printStackTrace();
        }catch (Exception e) {
            bugLogger.log("General error while getting options: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void setPointsPolicy(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            boolean isRepresentative = checkRepresentetive(clientSocket, input);
            String leagueIDString = input.readLine();
            int leagueID = Integer.parseInt(leagueIDString);
            String policy = input.readLine();
            Connection conn = connector.establishConnection();
            if(isRepresentative){
                PreparedStatement setPointsPolicyStatement = conn.prepareStatement("UPDATE league SET pointsPolicy=? WHERE leagueID=?");
                setPointsPolicyStatement.setString(1,policy);
                setPointsPolicyStatement.setInt(2,leagueID);
                setPointsPolicyStatement.executeUpdate();
                oos.writeObject("Set the policy successfully");
            } else {
                oos.writeObject("You are not a football association agent! You can't set a policy");
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);

        } catch (Exception e) {
            bugLogger.log("Error on setting points policy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void setGameSchedulingPolicy(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            boolean isRepresentative = checkRepresentetive(clientSocket, input);
            String leagueIDString = input.readLine();
            int leagueID = Integer.parseInt(leagueIDString);
            String seasonIDString = input.readLine();
            int seasonID = Integer.parseInt(seasonIDString);
            String policy = input.readLine();
            Connection conn = connector.establishConnection();
            if(isRepresentative){
                PreparedStatement setPointsPolicyStatement = conn.prepareStatement("UPDATE league_season SET gameSchedulingPolicy=? WHERE leagueID=? and seasonID=?");
                setPointsPolicyStatement.setString(1,policy);
                setPointsPolicyStatement.setInt(2,leagueID);
                setPointsPolicyStatement.setInt(3,seasonID);
                setPointsPolicyStatement.executeUpdate();
                oos.writeObject("Set the policy successfully");
            } else {
                oos.writeObject("You are not a football association agent! You can't set a policy");
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);

        } catch (Exception e) {
            bugLogger.log("Error on setting points policy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addGameToFollow(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            String gameID = input.readLine();
            String userID = input.readLine();
            String port = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM game_followers WHERE gameID=? AND userID=?");
            stmt.setString(1, gameID);
            stmt.setString(2, userID);
            ResultSet set = stmt.executeQuery();
            if(!set.next()) {
                PreparedStatement insertFollower = conn.prepareStatement("INSERT INTO game_followers (gameID,userID) values (?,?)");
                insertFollower.setString(1, gameID);
                insertFollower.setString(2, userID);
                oos.writeBoolean(insertFollower.executeUpdate() > 0);
                PreparedStatement getFollowerAddressAndPort = conn.prepareStatement("SELECT * FROM open_sessions WHERE userID=?");
                getFollowerAddressAndPort.setInt(1, Integer.parseInt(userID));
                ResultSet addressAndPortSet = getFollowerAddressAndPort.executeQuery();
                if (!addressAndPortSet.next()) {
                    PreparedStatement insertNewUserToUserSessionsTable = conn.prepareStatement("INSERT INTO open_sessions (userID,ipAddress,port) values (?,?,?)");
                    insertNewUserToUserSessionsTable.setInt(1, Integer.parseInt(userID));
                    insertNewUserToUserSessionsTable.setString(2, ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress().toString().substring(1));
                    insertNewUserToUserSessionsTable.setString(3, port);
                    insertNewUserToUserSessionsTable.executeUpdate();
                }
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getStadiums(Socket clientSocket, BufferedReader input){
        try {
            PrintWriter output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stadium");
            ResultSet set = stmt.executeQuery();
            while(set.next()) {
                String stadiumName = set.getString("name");
                output.println(stadiumName);
            }
            output.close();
            connector.closeConnection(conn);
        }  catch (SQLException throwables) {
            bugLogger.log("SQL exception occurred : "+throwables.getMessage());
            throwables.printStackTrace();
        } catch (Exception e) {
            bugLogger.log("General error while getting stadium: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getLeagues(Socket clientSocket, BufferedReader input){
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT leagueID FROM league");
            ResultSet set = stmt.executeQuery();
            LinkedList<String> leagues = new LinkedList<>();
            while(set.next()){
                leagues.add(set.getString("leagueID"));
            }
            oos.writeObject(leagues);
            oos.close();
            out.close();
            connector.closeConnection(conn);
            logger.log("Sent leagues to client");

        } catch (Exception e) {
            bugLogger.log("Error on send leagues to client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getSeasons(Socket clientSocket, BufferedReader input){
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT seasonID FROM season");
            ResultSet set = stmt.executeQuery();
            LinkedList<String> seasons = new LinkedList<>();
            while(set.next()){
                seasons.add(set.getString("seasonID"));
            }
            oos.writeObject(seasons);
            oos.close();
            out.close();
            connector.closeConnection(conn);
            logger.log("Sent seasons to client");

        } catch (Exception e) {
            bugLogger.log("Error on send seasons to client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void teamMaker(Socket clientSocket, BufferedReader input){
        try {
            Connection conn = connector.establishConnection();
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            boolean approved=checkRepresentetive(clientSocket, input);
            if(!approved) {
                oos.writeObject("3");
                out.close();
                return;
            }
            String teamName=input.readLine();
            String cityName=input.readLine();
            String stadiumName=input.readLine();
            String ownerID=input.readLine();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM team WHERE name=?");
            stmt.setString(1,teamName);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                oos.writeObject("2");
                out.close();
                return;
            }
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM user WHERE userID=?");
            stmt2.setInt(1,Integer.parseInt(ownerID));
            ResultSet set2 = stmt2.executeQuery();
            if(!set2.next()){
                oos.writeObject("5");
                out.close();
                return;
            }
            PreparedStatement stmt3 = conn.prepareStatement("SELECT * FROM stadium WHERE name=?");
            stmt3.setString(1,stadiumName);
            ResultSet set3 = stmt3.executeQuery();
            String stadiumID=null;
            if(set3.next()){
                if(set3.getString("teamName")!=null){
                    oos.writeObject("4");
                    input.mark(1000);
                    String answer;
                    while((answer=input.readLine())==null){
                        input.reset();
                    }
                    if(answer.equals("no")) {
                        out.close();
                        return;
                    }
                }
                stadiumID=set3.getString("stadiumID");
            }
            PreparedStatement stmt4 = conn.prepareStatement("INSERT INTO team values (?,?,?)");
            stmt4.setString(1,teamName);
            stmt4.setString(2,"ACTIVE");
            stmt4.setString(3,stadiumID);
            if(stmt4.executeUpdate()==0){
                oos.writeObject("0");
                out.close();
                return;
            }
            PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO teamowners values (?,?,?,?)");
            stmt5.setString(1,teamName);
            stmt5.setString(2,ownerID);
            stmt5.setString(3, Calendar.getInstance().get(Calendar.YEAR)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.DATE));
            stmt5.setString(4,null);
            if(stmt5.executeUpdate()==0){
                oos.writeObject("0");
                out.close();
                return;
            }
            PreparedStatement stmt6 = conn.prepareStatement("UPDATE stadium SET teamName=? WHERE name=?");
            stmt6.setString(1,teamName);
            stmt6.setString(2,stadiumName);
            if(stmt6.executeUpdate()==0){
                oos.writeObject("0");
                out.close();
                return;
            }
            logger.log("Team: "+teamName+" successfully created!");
            oos.writeObject("1");
            out.close();
            connector.closeConnection(conn);
        } catch (IOException e) {
            bugLogger.log("Error on making team I/O Exception: "+e.getMessage());
            e.printStackTrace();
        } catch (SQLException throwables) {
            bugLogger.log("Error on making team SQL Exception: "+throwables.getMessage());
            throwables.printStackTrace();
        }catch (Exception e) {
            bugLogger.log("Error on making team General error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getEventsOfGame(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            String gameID = input.readLine();
            String[] split = gameID.split(",");
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT HostGoals, GuestGoals, Date, EventsFilePath FROM game WHERE Host=? AND Guest=? AND Date=?");
            stmt.setString(1, split[0]);
            stmt.setString(2, split[1]);
            stmt.setString(3, split[2]);
            ResultSet set = stmt.executeQuery();
            boolean hasGames = false;
            if(set.next()){
                String eventsFilePath = set.getString("EventsFilePath");
                String events = "";
                if(eventsFilePath.isEmpty()){
                    eventsFilePath = "GameEvents/" + gameID + ".txt";
                    File eventsFile = new File(eventsFilePath);
                    eventsFile.createNewFile();
                    PreparedStatement setEventsFile = conn.prepareStatement("UPDATE game SET EventsFilePath=? WHERE Host=? AND Guest=? AND Date=?");
                    setEventsFile.setString(1, eventsFilePath);
                    setEventsFile.setString(2, split[0]);
                    setEventsFile.setString(3, split[1]);
                    setEventsFile.setDate(4, set.getDate("Date"));
                    setEventsFile.executeUpdate();
                } else {
                    FileInputStream reader = new FileInputStream(eventsFilePath);
                    events = new String(reader.readAllBytes());
                    reader.close();
                }
                HashMap<String, String> results = new HashMap<>();
                results.put("host", split[0]);
                results.put("guest", split[1]);
                results.put("hostGoals", set.getString("HostGoals"));
                results.put("guestGoals", set.getString("GuestGoals"));
                results.put("events", events);
                oos.writeObject(results);
                logger.log("Sent game details for game: " + split[0] + ',' + split[1] + ',' + split[2]);
                hasGames = true;
            }
            if(!hasGames){
                oos.writeObject("There was a problem");
                logger.log("Can't send game details for game: " + split[0] + ',' + split[1] + ',' + split[2]);
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void autherizeAssosicationAgent(Socket clientSocket, BufferedReader input){
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            boolean approved=checkRepresentetive(clientSocket, input);
            if(approved){
                oos.writeObject("1");
            }else {
                oos.writeObject(null);
            }
            oos.close();
            out.close();
        } catch (IOException e) {
            bugLogger.log("Error on checking auth for association agent: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addEventToGame(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            String gameID = input.readLine();
            String[] split = gameID.split(",");
            String eventDetails = input.readLine() + "\n";
            EventType eventType = EventType.fromString(input.readLine());
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT EventsFilePath, HostGoals, GuestGoals FROM game WHERE Host=? AND Guest=? AND Date=?");
            stmt.setString(1, split[0]);
            stmt.setString(2, split[1]);
            stmt.setString(3, split[2]);
            ResultSet set = stmt.executeQuery();
            int guestGoals = 0;
            int hostGoals = 0;
            if(set.next()){
                String eventsFilePath = set.getString("EventsFilePath");
                if(eventsFilePath.isEmpty()){
                    File eventsFile = new File("GameEvents/" + gameID + ".txt");
                    eventsFile.createNewFile();
                    eventsFilePath = "GameEvents/" + gameID + ".txt";
                }
                FileOutputStream writer = new FileOutputStream(eventsFilePath, true);
                writer.write(eventDetails.getBytes());
                writer.close();
                hostGoals = set.getInt("HostGoals");
                guestGoals = set.getInt("GuestGoals");
                oos.writeBoolean(true);
                logger.log("Added event " + eventDetails + "to game " + split[0] + ',' + split[1] + ',' + split[2]);
                notifyGameFollowers(gameID, eventDetails, hostGoals, guestGoals);
                logger.log("Notified followers of game: " + gameID);
            }
            else{
                oos.writeBoolean(false);
                logger.log("Can't add event " + eventDetails + "to game " + split[0] + ',' + split[1] + ',' + split[2]);
            }
            if(eventType == EventType.HOST_GOAL){
                PreparedStatement addHostGoalStatement = conn.prepareStatement("UPDATE game SET HostGoals=? WHERE Host=? AND Guest=? AND Date=?");
                addHostGoalStatement.setInt(1, hostGoals + 1);
                addHostGoalStatement.setString(2, split[0]);
                addHostGoalStatement.setString(3, split[1]);
                addHostGoalStatement.setString(4, split[2]);
                addHostGoalStatement.executeUpdate();
            } else if (eventType == EventType.GUEST_GOAL){
                PreparedStatement addGuestGoalStatement = conn.prepareStatement("UPDATE game SET GuestGoals=? WHERE Host=? AND Guest=? AND Date=?");
                addGuestGoalStatement.setInt(1, guestGoals + 1);
                addGuestGoalStatement.setString(2, split[0]);
                addGuestGoalStatement.setString(3, split[1]);
                addGuestGoalStatement.setString(4, split[2]);
                addGuestGoalStatement.executeUpdate();
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
            bugLogger.log("Error on add event to game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getRefereeGames(Socket clientSocket, BufferedReader input) {
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            String id = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM game WHERE MainRefereeID=? OR FirstAssistantRefereeID=? OR SecondAssistantRefereeID=?");
            stmt.setString(1,id);
            stmt.setString(2,id);
            stmt.setString(3,id);
            ResultSet set = stmt.executeQuery();
            LinkedList<String> refereeGames = new LinkedList<>();
            boolean refereeHasGames = false;
            Date currentDate = new Date();
            while(set.next()){
                String hostName = set.getString("Host");
                String guestName = set.getString("Guest");
                Date gameDate = set.getDate("Date");
                if(gameDate.getTime() > currentDate.getTime() + 1000 * 60 * 390 || (gameDate.getTime() > currentDate.getTime() + 1000 * 60 * 90 && !("" + set.getInt("MainReferee")).equals(id)))
                    continue;
                refereeGames.add(hostName + ',' + guestName + ',' + gameDate.toString());
                refereeHasGames = true;
            }
            oos.writeObject(refereeGames);
            oos.close();
            out.close();
            connector.closeConnection(conn);
            if(refereeHasGames)
                logger.log("Sent referee's games for referee with id: " + id);
            else
                logger.log("Referee with id: " + id + " asked for his games, but he has no games");
        } catch (Exception e) {
            bugLogger.log("Error on return referee games: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean checkRepresentetive(Socket clientSocket, BufferedReader input){
        boolean approved = false;
        try {
            String id = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE userID=?");
            stmt.setString(1,id);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                String isRepresentetive = set.getString("isRepresentetive");
                if(Integer.parseInt(isRepresentetive)==1){
                    approved=true;
                    logger.log("Assosication agent with id: "+id+" has been approved");
                }
            }
            connector.closeConnection(conn);
        } catch (Exception e) {
            bugLogger.log("Error on representetive check: "+e.getMessage());
            e.printStackTrace();
        }
        if(approved){
            return true;
        }else {
            return false;
        }
    }

    private static void login(Socket clientSocket, BufferedReader input) {
        try{
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream  oos = new ObjectOutputStream(out);
            String username = input.readLine();
            logger.log("Login attempt by: "+username);
            String password = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username=?");
            stmt.setString(1,username);
            ResultSet set = stmt.executeQuery();
            boolean approved = false;
            if(set.next()){
                String dbPassword = set.getString("password");
                if(password.equals(dbPassword)){
                    approved = true;
                }
            }
            if (approved) {
                User user = new User(set.getInt("userID"),set.getString("fname"),set.getString("lname"),username,password);
                oos.writeObject(user);
                logger.log("Login approved for: "+username);
            } else {
                oos.writeObject(null);
                logger.log("Login failed for: "+username);
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
            bugLogger.log("Error on login confirmation : "+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void register(Socket clientSocket, BufferedReader input){
        try{
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream  oos = new ObjectOutputStream(out);
            String username = input.readLine();
            String password = input.readLine();
            String fname = input.readLine();
            String lname = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username=?");
            stmt.setString(1,username);
            ResultSet set = stmt.executeQuery();
            if(!set.next()){
                User user = new User(username,password,fname,lname);
                stmt = conn.prepareStatement("INSERT INTO user (userID,username,password,fname,lname)" +
                        "values (?,?,?,?,?)");
                stmt.setInt(1,user.getID());
                stmt.setString(2,username);
                stmt.setString(3,password);
                stmt.setString(4,fname);
                stmt.setString(5,lname);
                if(stmt.executeUpdate() > 0){
                    oos.writeObject(user);
                    logger.log("Successful register by: "+username);
                }else{
                    oos.writeObject("Could'nt add user");
                }
            }else{ // username Already taken
                oos.writeObject("Username is taken!");
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (IOException e) {
            bugLogger.log("Error on register user I/O Exception: "+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            bugLogger.log("Error on register user: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean isClientAlive(String address, int port){
        try {
            String pingMessage = "Ping";
            socket = new Socket(address, port);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println(pingMessage);
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object received = ois.readObject();
            output.close();
            ois.close();
            socket.close();
            socket = null;
            return received.equals("Pong");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static LinkedList<Object> getUserAddressAndPort(int userID){
        try {
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT ipAddress, port FROM open_sessions WHERE userID=?");
            stmt.setInt(1,userID);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                String address = set.getString("ipAddress");
                Integer port = set.getInt("port");
                LinkedList<Object> list = new LinkedList<>();
                list.add(address);
                list.add(port);
                return list;
            }
            connector.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedList<Integer> getGameFollowers(String gameID){
        try {
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT userID FROM game_followers WHERE gameID=?");
            stmt.setString(1,gameID);
            ResultSet set = stmt.executeQuery();
            LinkedList<Integer> gameFollowers = new LinkedList<>();
            while(set.next()){
                gameFollowers.add(set.getInt("userID"));
            }
            connector.closeConnection(conn);
            return gameFollowers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void notifyGameFollowers(String gameID, String eventDetails, int hostGoals, int guestGoals){
        try {
            LinkedList<Integer> followers = getGameFollowers(gameID);
            if (followers != null) {
                for (int follower : followers) {
                    LinkedList<Object> userAddressAndPort = getUserAddressAndPort(follower);
                    if (userAddressAndPort == null || userAddressAndPort.size() != 2 || !(userAddressAndPort.get(0) instanceof String) || !(userAddressAndPort.get(1) instanceof Integer)) {
                        removeFollower(follower);
                        continue;
                    }
                    String address = (String) userAddressAndPort.get(0);
                    int port = (Integer) userAddressAndPort.get(1);
                    if (!isClientAlive(address, port)) {
                        removeFollower(follower);
                        continue;
                    }
                    String update = "UPDATE:" + gameID + ":" + eventDetails + ":" + hostGoals + ":" + guestGoals;
                    socket = new Socket(address, port);
                    PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    output.println(update);
                    output.flush();
                    output.close();
                    socket.close();
                    socket = null;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void removeFollower(int followerID) {
        try {
            Connection conn = connector.establishConnection();
            PreparedStatement deleteFollower = conn.prepareStatement("DELETE FROM game_followers WHERE userID=?");
            deleteFollower.setInt(1,followerID);
            deleteFollower.executeUpdate();
            deleteFollower = conn.prepareStatement("DELETE FROM open_sessions WHERE userID=?");
            deleteFollower.setInt(1,followerID);
            deleteFollower.executeUpdate();
            connector.closeConnection(conn);
        } catch (Exception e) {
            bugLogger.log("Error on deleting follower: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ViewModel getInstance(){
        if(viewModel == null){
            viewModel = new ViewModel();
        }
        return viewModel;
    }
}
