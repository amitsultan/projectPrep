package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import users.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChooseController extends AController {

    @FXML
    public ChoiceBox chooser;
    public static String choice;

    @FXML
    public void initialize(){
        ArrayList<String> options = new ArrayList<>();
        options.add("staff");
        options.add("player");
        options.add("coach");
        options.add("teammanager");
        options.add("Football Association representetive");
        options.add("referee");
        ObservableList<String> list = FXCollections.observableArrayList(options);
        chooser.setItems(list);
        choice ="";
    }

    @FXML
    public void setCorrectPage(){
        String role = chooser.getValue().toString();
        if(role.isEmpty()){
            raiseError("Incorrect Role choice","Please choose the role you want to activate");
        }
        else {
            choice = role;
           switch (role) {
               case "referee":
                   screenController.removeScreen("referee");
                   screenController.addScreen("referee", "/view/refereePage.fxml");
                   screenController.activate("referee");
               case "Football Association representetive":
                   if(isAgent()) {
                       screenController.removeScreen("FootballAssociation");
                       screenController.addScreen("Football Association", "/view/FootballAssociationScreen.fxml");
                       screenController.activate("Football Association");
                   }
                   else{
                       Alert a = new Alert(Alert.AlertType.WARNING);
                       a.setContentText("You are not authorized to enter this page");
                       a.show();
                   }
                   break;
               case "fan":
                   screenController.removeScreen("fan");
                   screenController.addScreen("fan", "/view/fanPage.fxml");
                   screenController.activate("fan");
               default:
                   screenController.removeScreen("default");
                   screenController.addScreen("default", "/view/default.fxml");
                   screenController.activate("default");
                   break;
           }
        }
    }

    public boolean isAgent(){
        boolean isAgent=false;
        try {
            User user = DefaultController.getUser();
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("checkRepresentetive");
            output.println(user.getID());
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            output.close();
            if(obj instanceof String){
                raiseError("Error occurred", (String) obj);
            }else{
                int isAgentBin=Integer.parseInt((String)obj);
                if(isAgentBin==1)
                    isAgent=true;
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("I/O error: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isAgent;
    }
}
