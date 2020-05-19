package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class ChooseController extends AController {

    @FXML
    public ChoiceBox chooser;
    public static String choice;

    public void initialize(){
        ArrayList<String> options = new ArrayList<>();
        options.add("staff");
        options.add("player");
        options.add("coach");
        options.add("teammanager");
        ObservableList<String> list = FXCollections.observableArrayList(options);
        chooser.setItems(list);
        choice ="";
    }

    public void setCorrectPage(){
        String role = chooser.getValue().toString();
        if(role.isEmpty()){
            raiseError("Incorrect Role choice","Please choose the role you want to activate");
        }
        else {
            choice = role;
           switch (role) {
               case "judge":
                   break;
               default:
                   screenController.removeScreen("default");
                   screenController.addScreen("default", "/view/default.fxml");
                   screenController.activate("default");
                   break;
           }
        }
    }
}
