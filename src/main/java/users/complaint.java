package users;

public class complaint {
    String body;
    boolean handle;

    public complaint(String body){
        this.body=body;
        handle=false;
    }

    public void handle(){
        handle=true;
    }
}
