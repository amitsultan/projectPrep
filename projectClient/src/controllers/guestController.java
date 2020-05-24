package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class guestController extends AController {
    @FXML
    private void loginBack(ActionEvent event){
        screenController.addScreen("login","/view/login.fxml");
        screenController.activate("login");
    }
}
