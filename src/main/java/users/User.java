package users;


import dbhandler.Connector;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {

    private int ID;
    private static AtomicInteger count;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    static{
        try{
            Connection conn = Connector.getInstance().establishConnection();
            ResultSet rs = Connector.getInstance().selectQuery(conn,"Select ID from user");
            int max = 0;
            while(rs.next()){
                max = Math.max(max,rs.getInt("ID"));
            }
            count = new AtomicInteger(max);
            conn.close();
        }catch(Exception e){
            count = new AtomicInteger(0);
        }
    }

    public User(String firstName, String lastName, String userName, String password) throws Exception {
        if(firstName == null || lastName == null || userName == null || password == null)
            throw new Exception("Arguments can't be null");
        this.ID = count.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }
    public User(int ID,String firstName, String lastName, String userName, String password) throws Exception {
        if(firstName == null || lastName == null || userName == null || password == null)
            throw new Exception("Arguments can't be null");
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
    }

    public void setPassword(String password) throws Exception {
        if(password == null)
            throw new Exception("Password can't be null");
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws Exception {
        if(firstName == null)
            throw new Exception("First name can't be null");
        this.firstName = firstName;
    }

    public int getID() {
        return ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws Exception {
        if(lastName == null)
            throw new Exception("Last name can't be null");
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID;
    }
}
