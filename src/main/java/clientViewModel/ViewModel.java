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
            if(action.equals("login")){
                login(clientSocket,input);
            }
            long time = System.currentTimeMillis();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        return null;
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
            input.close();
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
