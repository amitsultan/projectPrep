package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import league.Event;
import league.Game;
import league.Referee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class RefereeController extends AController {

    @FXML
    public Label lblReferee;
    public ChoiceBox gameChooser;
    public ChoiceBox eventChooser;
    public TextField comments;
    public Button event_btn;
    private Game currGame=null;
    private Referee currReferee = null;
    private LinkedList<String> games = null;

    @FXML
    protected void initialize(){
        lblReferee.setText("Hello Referee "+DefaultController.user.getFirstName()+"!");
        ArrayList<String> gameOptions = new ArrayList<>();
        ArrayList<String> updateOptions = new ArrayList<>();

        //get all the games that referee can add events to
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("getRefereeGames");
            output.println(DefaultController.user.getID());
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if(!(obj instanceof LinkedList)){
                raiseError("Error occurred", obj.toString());
            }else{
                games = (LinkedList)obj;
                gameOptions.addAll(games);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        ObservableList<String> list = FXCollections.observableArrayList(gameOptions);
        gameChooser.setItems(list);

        //set all the possible events there is
        updateOptions.add("HOST_GOAL");
        updateOptions.add("GUEST_GOAL");
        updateOptions.add("OFFSIDE");
        updateOptions.add("FOUL");
        updateOptions.add("RED_TICKET");
        updateOptions.add("YELLOW_TICKET");
        updateOptions.add("INJURY");
        updateOptions.add("PLAYER_SWITCH");

        ObservableList<String> list2 = FXCollections.observableArrayList(updateOptions);
        eventChooser.setItems(list2);
    }

    @FXML
    protected void enableFields(){
        if(gameChooser.isShowing()) {
            eventChooser.setDisable(false);
            comments.setDisable(false);
        }
    }

    @FXML
    protected void enableButton(){
        event_btn.setDisable(false);
    }

    @FXML
    protected void updateEvent(){
        String game = gameChooser.getValue().toString();
        String eventType = eventChooser.getValue().toString();
        String comment = comments.getText();
        if(comment.isEmpty() || game.isEmpty() || eventType.isEmpty()){
            raiseError("Incorrect Event creation","Please enter a comment to your event");
        }
        // the referee can add an event
        else{
            try {
                Socket socket = new Socket(IP, PORT);
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                output.println("addEventToGame");
                output.println(game);
                output.println(comment);
                output.println(eventType);
                output.flush();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object obj = ois.readObject();
                if (obj instanceof String) {
                    raiseError("Error occurred", (String) obj);
                } else {

                }
            } catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("I/O error: " + ex.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
