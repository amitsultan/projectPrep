package dbhandler;

import java.sql.*;

public class Connector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static String DB_URL = "jdbc:mysql://localhost:3306/projectDB";
    private static String USER = "root";
    private static String PASS = "124512";
    private static Connector connector;

    static {
        DB_URL += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    private Connector(){

    }

    public boolean checkConnection(){
        try {
            Connection conn = establishConnection();
            if(establishConnection() != null)
                return closeConnection(conn);
        } catch (DriverClassNotFound driverClassNotFound) {
            driverClassNotFound.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ResultSet selectQuery(Connection conn,String query){
        try {
            if(conn.isClosed())
                return null;
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(query);
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Connector getInstance(){
        if(connector == null){
            connector = new Connector();
        }
        return connector;
    }


    public Connection establishConnection() throws DriverClassNotFound, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn =  DriverManager.getConnection(DB_URL,USER,PASS);
            return conn;
        } catch (SQLException e) {
            throw new SQLException("Please make sure the DB is running and credentials are right");
        } catch (ClassNotFoundException e) {
            throw new DriverClassNotFound("Please make sure you use the right jdbc driver");
        }
    }

    public boolean closeConnection(Connection conn) throws SQLException {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            throw new SQLException("Please make sure the DB is running and credentials are right");
        }
    }
}
