package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import users.User;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChooseController extends AController {

    @FXML
    public ChoiceBox chooser;
    public static String choice;

    @FXML
    public void initialize(){
        try {
            ArrayList<String> options = new ArrayList<>();
            User user = DefaultController.getUser();
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("options");
            output.println(user.getID());
            output.flush();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String option;
            while((option=input.readLine())!=null){
                options.add(option);
            }
            ObservableList<String> list = FXCollections.observableArrayList(options);
            chooser.setItems(list);
            choice ="";
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
               case "referee":
                   screenController.removeScreen("referee");
                   screenController.addScreen("referee", "/view/refereePage.fxml");
                   screenController.activate("referee");
                   break;
               case "Football Association representetive":
//                   if(isAgent()) {
                       screenController.removeScreen("FootballAssociation");
                       screenController.addScreen("FootballAssociation", "/view/FootballAssociationScreen.fxml");
                       screenController.activate("FootballAssociation");
//                   }
//                   else{
//                       Alert a = new Alert(Alert.AlertType.WARNING);
//                       a.setContentText("You are not authorized to enter this page");
//                       a.show();
//                   }
                   break;
               case "fan":
                   screenController.addScreen("fan", "/view/fanPage.fxml");
                   screenController.activate("fan");
                   break;
               default:
                   screenController.removeScreen("default");
                   screenController.addScreen("default", "/view/default.fxml");
                   screenController.activate("default");
                   break;
           }
        }
    }

    @FXML
    public void logOut(){
        screenController.activate("login");
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
            if(!(obj instanceof String)){
                raiseError("Error occurred", (String) obj);
                return false;
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
