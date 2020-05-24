package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FanController extends AController {
    @FXML
    public Label lblWelcome;
    public ChoiceBox chooseGame;
    public ChoiceBox chooseUpdate;
    public Pane gamePane;
    public TextArea host;
    public TextArea guest;
    public TextArea score;
    public TextArea events;

    @FXML
    protected void initialize(){
        lblWelcome.setText("Welcome back "+DefaultController.user.getFirstName()+"!");
        ArrayList<String> allGames = new ArrayList<>();

        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("getAllGamesIn5Hours");
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if (obj instanceof String) {
                raiseError("Error occurred", (String) obj);
            } else {
                LinkedList<String> games = (LinkedList<String>)obj;
                for (String game:games) {
                    allGames.add(game);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("I/O error: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> list = FXCollections.observableArrayList(allGames);
        chooseUpdate.setItems(list);
    }

    @FXML
    protected void addToUpdateList(){
        String game = chooseGame.getValue().toString();
        if(game.isEmpty()){
            raiseError("Game choice error","Please choose the game you want to follow");
        }
        else {
           ObservableList games = chooseUpdate.getItems();
           if(!games.isEmpty()){
               for (Object g: games) {
                   String currGame = (String)g;
                   if(currGame.equals(game)){
                       return;
                   }
               }
           }
            chooseUpdate.getItems().add(0,game);
        }
    }

    @FXML
    protected void setAllUpdates() {
        String game = chooseUpdate.getValue().toString();
        if(game.isEmpty()){
            raiseError("Game Update error","Please choose the game you want to follow");
        }
        else{
            try {
                Socket socket = new Socket(IP, PORT);
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                output.println("getEventsOfGame");
                output.println(game);
                output.flush();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object obj = ois.readObject();
                if (!(obj instanceof HashMap)) {
                    raiseError("Error occurred", (String) obj);
                } else {
                    HashMap<String,String> allEvents = (HashMap)obj;
                    host.setText("Host:\n"+ allEvents.get("host"));
                    guest.setText("Guest:\n" + allEvents.get("guest"));
                    score.setText("Score:\n" + allEvents.get("score"));
                    events.setText("Events:\n"+ allEvents.get("events"));
                }
                gamePane.setVisible(false);
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
