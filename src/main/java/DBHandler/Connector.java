package DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    String DB_URL = "jdbc:mysql://localhost/EMP";
    String USER = "username";
    String PASS = "password";


    public Connector(String URL,String User,String Password){
        this.DB_URL = URL;
        this.USER = User;
        this.PASS = Password;
    }

    private Connection establishConnection(){
        try {
            return DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean closeConnection(Connection conn){
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
