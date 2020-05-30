package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

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

    private NotificationsListener notificationsSocket = null;
    private Thread notificationsThread;

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
                allGames.addAll(games);
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
        chooseGame.setItems(list);
    }

    @FXML
    protected void addToUpdateList(){
        try {
            if(chooseGame.getValue() == null)
                return;
            String game = chooseGame.getValue().toString();
            if (game.isEmpty()) {
                raiseError("Game choice error", "Please choose the game you want to follow");
            } else {
                ObservableList games = chooseUpdate.getItems();
                if (!games.isEmpty()) {
                    for (Object g : games) {
                        String currGame = (String) g;
                        if (currGame.equals(game)) {
                            return;
                        }
                    }
                }
                chooseUpdate.getItems().add(0, game);
                Socket socket = new Socket(IP, PORT);
                if (notificationsSocket == null) {
                    notificationsSocket = new NotificationsListener();
//                    notificationsThread = new Thread(notificationsSocket);
                    notificationsSocket.start();
                    TimeUnit.SECONDS.sleep(1);
                }
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                output.println("addGameToFollow");
                output.println(game);
                output.println(DefaultController.getUser().getID());
                output.println(5005);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                    score.setText("Score:\n" + allEvents.get("hostGoals") + ":" + allEvents.get("guestGoals"));
                    events.setText("Events:\n"+ allEvents.get("events"));
                }
                gamePane.setDisable(false);
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
