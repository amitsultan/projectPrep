package clientViewModel;

import dbhandler.Connector;
import users.User;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewModel {
    private ViewModel viewModel;
    private BufferedReader input;
    private static Connector connector = Connector.getInstance();

    private ViewModel() {

    }

    public static Runnable handel(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String action = input.readLine();
            switch(action){
                case "login":{
                    login(clientSocket,input);
                }
                case "register":{
                    register(clientSocket,input);
                }
                case "checkRepresentetive":{
                    checkRepresentetive(clientSocket,input);
                }
            }
            input.close();
            long time = System.currentTimeMillis();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        return null;
    }

    private static void checkRepresentetive(Socket clientSocket, BufferedReader input){
        try {
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            String id = input.readLine();
            Connection conn = connector.establishConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE useID=?");
            stmt.setString(1,id);
            ResultSet set = stmt.executeQuery();
            boolean approved = false;
            if(set.next()){
                String isRepresentetive = set.getString("isRepresentetive");
                if(Integer.parseInt(isRepresentetive)==1){
                    approved=true;
                }
            }
            if(approved){
                oos.writeObject("1");
            }else {
                oos.writeObject(null);
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void login(Socket clientSocket, BufferedReader input) {
        try{
            OutputStream out = clientSocket.getOutputStream();
            ObjectOutputStream  oos = new ObjectOutputStream(out);
            String username = input.readLine();
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
            } else {
                oos.writeObject(null);
            }
            oos.close();
            out.close();
            connector.closeConnection(conn);
        } catch (Exception e) {
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
            e.printStackTrace();
        } catch (Exception e) {
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
