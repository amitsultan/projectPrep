package users;

import java.util.Date;

public class Complaint {
    String body;
    Date dateOfSubmit;
    boolean handle;

    public Complaint(String body, Date submitDate){
        this.body=body;
        this.dateOfSubmit = submitDate;
        handle=false;
    }

    public void handle(){
        handle=true;
    }
}
