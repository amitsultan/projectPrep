package clientViewModel;
import dbhandler.Connector;
import external.BugLogger;
import users.User;
import external.logger;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;


public class ViewModel {
    private ViewModel viewModel;
    private BufferedReader input;
    private static Connector connector = Connector.getInstance();
    private static logger logger = external.logger.getInstance();
    private static BugLogger bugLogger = BugLogger.getInstance();


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

    private static void getStadiums(Socket clientSocket, BufferedReader input){
        try {
            PrintWriter output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stadiums");
            ResultSet set = stmt.executeQuery();
            while(set.next()) {
                String stadiumName = set.getString("name");
                output.println(stadiumName);
            }
            output.close();
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
            stmt2.setString(1,ownerID);
            ResultSet set2 = stmt.executeQuery();
            if(!set2.next()){
                oos.writeObject("5");
                out.close();
                return;
            }
            PreparedStatement stmt3 = conn.prepareStatement("SELECT * FROM stadium WHERE name=?");
            stmt2.setString(1,stadiumName);
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
            PreparedStatement stmt6 = conn.prepareStatement("UPDATE stadium SET teamName=? WHERE name="+stadiumName);
            stmt6.setString(1,teamName);
            if(stmt6.executeUpdate()==0){
                oos.writeObject("0");
                out.close();
                return;
            }
            logger.log("Team: "+teamName+" successfully created!");
            oos.writeObject("1");
            out.close();
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

    public ViewModel getInstance(){
        if(viewModel == null){
            viewModel = new ViewModel();
        }
        return viewModel;
    }
}
