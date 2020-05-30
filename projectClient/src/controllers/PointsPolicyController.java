package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class PointsPolicyController extends AController {

    @FXML
    public ChoiceBox policyChooser;
    public ChoiceBox leagueChooser;

    @FXML
    public void initialize(){
        ArrayList<String> policies = new ArrayList<>();
        policies.add("SimplePointsPolicy");
        ObservableList<String> policiesList = FXCollections.observableArrayList(policies);
        policyChooser.setItems(policiesList);

        Socket socket = null;
        LinkedList<String> leagues = null;
        try {
            socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("getLeagues");
            output.flush();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Object o = input.readObject();
            if(!(o instanceof LinkedList) || !(((LinkedList) o).element() instanceof String)) {
                raiseError("Can't get leagues", "Could not receive leagues from server");
                return;
            }
            leagues = (LinkedList) o;
            ObservableList<String> leagueList = FXCollections.observableArrayList(leagues);
            leagueChooser.setItems(leagueList);
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setPolicy(){
        if(policyChooser.getValue()==null){
            raiseError("No policy was chosen","Please choose a policy");
            return;
        }
        String policy = policyChooser.getValue().toString();
        if(policy.isEmpty()){
            raiseError("Incorrect policy choice","Please choose the policy you want to set");
        }
        if(leagueChooser.getValue()==null){
            raiseError("No league was chosen","Please chose a league");
            return;
        }
        String leagueID = leagueChooser.getValue().toString();
        if(leagueID.isEmpty()) {
            raiseError("Incorrect league choice","Please choose the league you want to set a policy for");
        }
        else {
            Socket socket = null;
            try {
                socket = new Socket(IP, PORT);
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                output.println("setPointsPolicy");
                output.println(DefaultController.getUser().getID());
                output.println(leagueID);
                output.println(policy);
                output.flush();
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Object option = input.readObject();
                if(!(option instanceof String) ||  !option.equals("Set the policy successfully")){
                    raiseError("Can't set policy","There was a problem and the policy can't be set");
                }
                output.close();
                input.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void back(){
        screenController.removeScreen("FootballAssociation");
        screenController.addScreen("FootballAssociation", "/view/FootballAssociationScreen.fxml");
        screenController.activate("FootballAssociation");
    }
}
