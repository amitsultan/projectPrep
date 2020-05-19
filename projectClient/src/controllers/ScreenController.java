package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Scene> screenMap = new HashMap<>();
    private Stage main;

    public ScreenController(Stage main) {
        this.main = main;
    }

    protected void addScreen(String name,String fxmlPath){
        if(screenMap.containsKey(name))
        return;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            screenMap.put(name, new Scene(root, 800, 600));
        } catch (IOException e) {

        }
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        if(screenMap.containsKey(name)){
            main.setScene(screenMap.get(name));
        }
    }
}