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
    private Game[] games = null;

    @FXML
    protected void initialize(){
        lblReferee.setText("Hello Referee "+DefaultController.user.getFirstName()+"!");
        ArrayList<String> gameOptions = new ArrayList<>();
        ArrayList<String> updateOptions = new ArrayList<>();
        gameOptions.add("game1");
        ObservableList<String> list = FXCollections.observableArrayList(gameOptions);
        gameChooser.setItems(list);
/**
        //get all the games that referee can add events to
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("getRefereeGames");
            output.println(DefaultController.user.getID());
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if(obj instanceof String){
                raiseError("Error occurred", (String) obj);
            }else{
                games = (Game[])obj;
                for (Game g: games) {
                    gameOptions.add(g.getDate() + "," + g.getStadium().getName());
                }
            }

            output.println("getReferee");
            output.println(DefaultController.user.getID());
            output.flush();
            output.close();
            obj = ois.readObject();
            if(obj instanceof String){
                raiseError("Error occurred", (String) obj);
            }else{
                currReferee = (Referee) obj;
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("I/O error: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<String> list = FXCollections.observableArrayList(gameOptions);
        gameChooser.setItems(list);
**/

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
        String dateGame = game.split(",")[0];
        Date date = new Date(dateGame);
        String stadiumName = game.split(",")[1];
        String eventType = eventChooser.getValue().toString();
        String comment = comments.getText();
        if(comment.isEmpty() || game.isEmpty() || stadiumName.isEmpty()){
            raiseError("Incorrect Event creation","Please enter a comment to your event");
        }
        for(Game g : games){
            if(g.getDate().equals(date) && g.getStadium().equals(stadiumName)){
                currGame = g;
                break;
            }
        }
        Date currDate = new Date();
        TimeUnit minutes = TimeUnit.MINUTES;
        long diffInMillies = currDate.getTime() - date.getTime();
        long timeOfGame = minutes.convert(diffInMillies,TimeUnit.MILLISECONDS);

        //its the main referee but its been more than 5 hours after the game ended
        if(currGame.getMainReferee().equals(currReferee) && timeOfGame >= 390){
            raiseError("Incorrect Event creation","Sorry, Main referee can update event up to 5 hours after the game." +
                    " It's been "+ (timeOfGame-90)/60 + "hours after");
        }
        //its a regular referee but the game ended
        else if(!currGame.getMainReferee().equals(currReferee) && timeOfGame > 90){
            raiseError("Incorrect Event creation","Sorry, regular referee can update event only during the game." +
                    " It's been "+ (timeOfGame-90) + "minutes after the game ends");
        }
        // the referee can add an event
        else{
            Event event = new Event(new Date(), currGame, comment, currReferee);
            try {
                Socket socket = new Socket(IP, PORT);
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                output.println("addEventToGameLog");
                output.println(event.getDetails());
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
