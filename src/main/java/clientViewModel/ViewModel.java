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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ViewModel {
    private ViewModel viewModel;
    private BufferedReader input;
    private static Connector connector = Connector.getInstance();
    private static logger logger = external.logger.getInstance();
    private static BugLogger bugLogger = BugLogger.getInstance();
    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

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
            String leagueID = input.readLine();
            String policy = input.readLine();
            Connection conn = connector.establishConnection();
            if(isRepresentative){
                PreparedStatement setPointsPolicyStatement = conn.prepareStatement("UPDATE league SET pointsPolicy=? WHERE leagueID=?");
                setPointsPolicyStatement.setString(1,policy);
                setPointsPolicyStatement.setString(2,leagueID);
                if(setPointsPolicyStatement.executeUpdate()==0){
                    oos.writeObject("Set the policy successfully");
                } else {
                    oos.writeObject("Could not update the policy");
                }
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
            String leagueID = input.readLine();
            String seasonID = input.readLine();
            String policy = input.readLine();
            Connection conn = connector.establishConnection();
            if(isRepresentative){
                PreparedStatement setPointsPolicyStatement = conn.prepareStatement("UPDATE league_season SET gameSchedulingPolicy=? WHERE leagueID=? and seasonID=?");
                setPointsPolicyStatement.setString(1,policy);
                setPointsPolicyStatement.setString(2,leagueID);
                setPointsPolicyStatement.setString(2,seasonID);
                if(setPointsPolicyStatement.executeUpdate()==0){
                    oos.writeObject("Set the policy successfully");
                } else {
                    oos.writeObject("Could not update the policy");
                }
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
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO game_followers (gameID,userID) values (?,?)");
            stmt.setString(1, gameID);
            stmt.setString(2, userID);
            oos.writeBoolean(stmt.executeUpdate() > 0);
            stmt = conn.prepareStatement("SELECT * FROM open_sessions WHERE userID=?");
            stmt.setString(1, userID);
            ResultSet set = stmt.executeQuery();
            if(!set.next()){
                PreparedStatement insertNewUserToUserSessionsTable = conn.prepareStatement("INSERT INTO user_sessions (userID,ipAddress,port) values (?,?,?)");
                insertNewUserToUserSessionsTable.setString(1, userID);
                insertNewUserToUserSessionsTable.setString(2, ((InetSocketAddress)clientSocket.getRemoteSocketAddress()).getAddress().toString());
                insertNewUserToUserSessionsTable.setString(3, port);
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
            String id = input.readLine();
            String[] split = id.split(",");
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT HostGoals, GuestGoals, EventsFilePath FROM game WHERE Host=? AND Guest=? AND Date=?");
            stmt.setString(1, split[0]);
            stmt.setString(2, split[1]);
            stmt.setString(3, split[2]);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                String eventsFilePath = set.getString("EventsFilePath");
                FileInputStream reader = new FileInputStream(eventsFilePath);
                String events = new String(reader.readAllBytes());
                reader.close();
                HashMap<String, String> results = new HashMap<>();
                results.put("host", split[0]);
                results.put("guest", split[1]);
                results.put("hostGoals", set.getString("HostGoals"));
                results.put("guestGoals", set.getString("GuestGoals"));
                results.put("events", events);
                oos.writeObject(results);
                reader.close();
                logger.log("Sent game details for game: " + split[0] + ',' + split[1] + ',' + split[2]);
            }
            else{
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
            EventType eventType = EventType.fromInteger(Integer.parseInt(input.readLine()));
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT EventsFilePath, HostGoals, GuestGoals FROM game WHERE Host=? AND Guest=? AND Date=?");
            stmt.setString(1, split[0]);
            stmt.setString(2, split[1]);
            stmt.setString(3, split[2]);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                String eventsFilePath = set.getString("EventsFilePath");
                FileOutputStream writer = new FileOutputStream(eventsFilePath, true);
                writer.write(eventDetails.getBytes());
                writer.close();
                int hostGoals = set.getInt("HostGoals");
                int guestGoals = set.getInt("GuestGoals");
                if(eventType == EventType.HOST_GOAL){
                    PreparedStatement addHostGoalStatement = conn.prepareStatement("UPDATE game SET HostGoals=? WHERE Host=? AND Guest=? AND Date=?");
                    addHostGoalStatement.setInt(1, hostGoals + 1);
                    addHostGoalStatement.setString(2, split[0]);
                    addHostGoalStatement.setString(3, split[1]);
                    addHostGoalStatement.setString(4, split[2]);
                } else if (eventType == EventType.GUEST_GOAL){
                    PreparedStatement addHostGoalStatement = conn.prepareStatement("UPDATE game SET GuestGoals=? WHERE Host=? AND Guest=? AND Date=?");
                    addHostGoalStatement.setInt(1, guestGoals + 1);
                    addHostGoalStatement.setString(2, split[0]);
                    addHostGoalStatement.setString(3, split[1]);
                    addHostGoalStatement.setString(4, split[2]);
                }
                oos.writeBoolean(true);
                logger.log("Added event " + eventDetails + "to game " + split[0] + ',' + split[1] + ',' + split[2]);
                notifyGameFollowers(gameID, eventDetails, hostGoals, guestGoals);
                logger.log("Notified followers of game: " + gameID);
            }
            else{
                oos.writeBoolean(false);
                logger.log("Can't add event " + eventDetails + "to game " + split[0] + ',' + split[1] + ',' + split[2]);
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
            PreparedStatement stmt = conn.prepareStatement("SELECT Host, Guest, Date FROM game WHERE MainRefereeID=? OR FirstAssistantRefereeID=? OR SecondAssistantRefereeID=?");
            stmt.setString(1,id);
            stmt.setString(2,id);
            stmt.setString(3,id);
            ResultSet set = stmt.executeQuery();
            LinkedList<String> refereeGames = new LinkedList<>();
            boolean refereeHasGames = false;
            while(set.next()){
                String hostName = set.getString("Host");
                String guestName = set.getString("Guest");
                String date = set.getString("Date");
                refereeGames.add(hostName + ',' + guestName + ',' + date);
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

    public static boolean isClientAlive(InetAddress address, int port){
        try {
            byte[] pingMessage = "Ping".getBytes();
            DatagramPacket packet = new DatagramPacket(pingMessage, pingMessage.length, address, port);
            socket.send(packet);
            packet = new DatagramPacket(pingMessage, pingMessage.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
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
            connector.closeConnection(conn);
            ResultSet set = stmt.executeQuery();
            if(set.next()){
                InetAddress address = InetAddress.getByName(set.getString("ipAddress"));
                Integer port = set.getInt("port");
                LinkedList<Object> list = new LinkedList<>();
                list.add(address);
                list.add(port);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getGameFollowers(String gameID){
        try {
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT userID FROM gameFollowers WHERE gameID=?");
            stmt.setString(1,gameID);
            connector.closeConnection(conn);
            return stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void notifyGameFollowers(String gameID, String eventDetails, int hostGoals, int guestGoals){
        try {
            ResultSet followers = getGameFollowers(gameID);
            while (followers != null && followers.next()){
                int followerID = followers.getInt("userID");
                LinkedList<Object> userAddressAndPort = getUserAddressAndPort(followerID);
                if(userAddressAndPort == null || userAddressAndPort.size() != 2 || !(userAddressAndPort.get(0) instanceof InetAddress) || !(userAddressAndPort.get(1) instanceof Integer)){
                    removeFollower(followerID);
                    continue;
                }
                InetAddress address = (InetAddress) userAddressAndPort.get(0);
                int port = (Integer) userAddressAndPort.get(1);
                if(!isClientAlive(address, port)){
                    removeFollower(followerID);
                    continue;
                }
                String update = "UPDATE:" + gameID + ":" + eventDetails + ":" + hostGoals + ":" + guestGoals;
                byte[] gameUpdate = update.getBytes();
                DatagramPacket packet = new DatagramPacket(gameUpdate, gameUpdate.length, address, port);
                socket.send(packet);
                socket.close();
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
