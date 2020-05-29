package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class FootballAssociationController extends AController {
    @FXML
    public ChoiceBox chooser;
    public static String choice;

    @FXML
    public void initialize(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Make A Team");
        ObservableList<String> list = FXCollections.observableArrayList(options);
        chooser.setItems(list);
        choice ="";
    }

    @FXML
    public void setCorrectPage(){
        if(chooser.getValue()==null){
            raiseError("Empty role","Please chose a role page");
            return;
        }
        String role = chooser.getValue().toString();
        if(role.isEmpty()){
            raiseError("Incorrect Role choice","Please choose the role you want to activate");
        }
        else {
            choice = role;
            switch (role) {
                case "Make A Team":
                    screenController.removeScreen("Team");
                    screenController.addScreen("Team", "/view/FootballAssociationScreen.fxml");
                    screenController.activate("Team");
                default:
                    screenController.removeScreen("TeamMaker");
                    screenController.addScreen("TeamMaker", "/view/TeamMaker.fxml");
                    screenController.activate("TeamMaker");
                    break;
            }
        }
    }

    @FXML
    public void back(){
        screenController.removeScreen("choose");
        screenController.addScreen("choose", "/view/chooseYourRole.fxml");
        screenController.activate("choose");
    }
}
