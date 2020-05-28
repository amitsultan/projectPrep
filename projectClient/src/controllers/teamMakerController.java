package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import users.User;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import static controllers.AController.screenController;

public class teamMakerController extends AController {

    @FXML
    public TextField teamNameTxt;
    public TextField cityNameTxt;
    public ChoiceBox stadiumNameTxt;
    public TextField ownerIDTxt;

    @FXML
    public void initialize(){
        Socket socket = null;
        ArrayList<String> options = new ArrayList<>();
        try {
            socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("get stadiums");
            output.flush();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String option;
            while((option=input.readLine())!=null){
                options.add(option);
            }
            ObservableList<String> list = FXCollections.observableArrayList(options);
            stadiumNameTxt.setItems(list);
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void back(){
        screenController.removeScreen("FootballAssociation");
        screenController.addScreen("Football Association", "/view/FootballAssociationScreen.fxml");
        screenController.activate("Football Association");
    }

    @FXML
    public void makeATeam(ActionEvent event){
        String teamName = teamNameTxt.getText();
        String cityName = cityNameTxt.getText();
        String stadiumName = stadiumNameTxt.getValue().toString();
        String ownerID = ownerIDTxt.getText();
        //DOR YA OMO
        if(!cityName.matches("^[a-zA-Z]*$")){
            raiseError("Illegal charecters","cityName must contain legal charecters only");
            return;
        }
        if(!teamName.matches("^[a-zA-Z ]*$")){
            raiseError("Illegal charecters","teamName must contain legal charecters only");
            return;
        }
        if(!stadiumName.matches("^[a-zA-Z]*$")){
            raiseError("Illegal charecters","stadiumName must contain legal charecters only");
            return;
        }
        if(!ownerID.matches("^[0-9]*$")){
            raiseError("not a num","Number of seats must contain numerical value");
            return;
        }
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("make team");
            output.println(DefaultController.getUser().getID());
            output.println(teamName);
            output.println(cityName);
            output.println(stadiumName);
            output.println(ownerID);
            output.flush();
            boolean done=false;
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while(!done) {
                Object obj = ois.readObject();
                switch ((String) obj) {
                    case "0":
                        raiseError("couldnt save", "The server couldnt save the team successfully");
                        done=true;
                        return;
                    case "1":
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("Team added successfully");
                        a.show();
                        done=true;
                        return;
                    case "2":
                        raiseError("Duplicate", "This team already exists");
                        done=true;
                        return;
                    case "3":
                        raiseError("unauthorized", "You are unauthorized to make this action");
                        done=true;
                        return;
                    case "4":
                        Alert a2 = new Alert(AlertType.CONFIRMATION);
                        a2.setContentText("This stadium is already a home pitch to another team do you want to add another?");
                        Optional<ButtonType> result = a2.showAndWait();
                        if (!result.isPresent()) {
                            output.println("no");
                            output.flush();
                            done=true;
                        } else if (result.get() == ButtonType.OK) {
                            output.println("ok");
                            output.flush();
                        } else if (result.get() == ButtonType.CANCEL) {
                            output.println("no");
                            output.flush();
                            done=true;
                        }
                        break;
                    case "5":
                        raiseError("user not found","No registered users found! please try again");
                        return;
                    default:
                        raiseError("Error occurred", "unexpected value returned from server");
                        done=true;
                        return;
                }
            }
            output.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
