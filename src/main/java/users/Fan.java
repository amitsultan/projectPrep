package users;

public class Fan {
    private User user;

    public Fan(int ID, String firstName, String lastName, String userName, String password) {
        User user = new User(ID,firstName,lastName,userName,password);
        this.user = user;

    }

    public Fan(User user){
        this.user = user;
    }

}
