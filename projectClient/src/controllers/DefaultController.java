package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import users.User;

public class DefaultController extends AController{


    @FXML
    public Label lblWelcome;
    public Label lblRole;
    protected static User user;

    static void initUserDetails(User user){
        DefaultController.user = user;
    }

    public static User getUser(){
        return user;
    }

    @FXML
    protected void initialize(){
        lblWelcome.setText("Welcome back "+user.getFirstName()+"!");
        lblRole.setText("");
    }
    @FXML
    protected void setRoleLabel(){
        lblRole.setText("You are in the role of "+ ChooseController.choice + " now.");
    }
}
