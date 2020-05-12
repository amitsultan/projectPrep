package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import users.User;

public class HomeController {


    @FXML
    public Label lblWelcome;
    private static User user;

    static void initUserDetails(User user){
        HomeController.user = user;
    }

    @FXML
    protected void initialize(){
        lblWelcome.setText("Welcome back "+user.getFirstName()+"!");
    }
}
