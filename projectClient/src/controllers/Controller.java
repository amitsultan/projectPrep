package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import users.User;

public class Controller extends AController {


    // buttons
    @FXML
    public Button btnTest = new Button();
    public TextField txtFieldusername;
    public PasswordField txtFieldpassword;
    public Hyperlink registerLink;
    // configuration
    private Map<?, ?> config;

    public static void initController(ScreenController sc){
        screenController = sc;
        screenController.addScreen("login","/view/login.fxml");
        screenController.activate("login");
    }

    @FXML
    protected void initialize(){
        registerLink.setBorder(Border.EMPTY);
        registerLink.setPadding(new Insets(4,0,4,0));
    }

    @FXML
    private void registerClick(ActionEvent event){
        screenController.addScreen("register","/view/register.fxml");
        screenController.activate("register");
    }

    @FXML
    private void guestClick(ActionEvent event){
        screenController.addScreen("guest","/view/guest.fxml");
        screenController.activate("guest");
    }


    @FXML
    private void loginBtn(ActionEvent event) {
        String username = txtFieldusername.getText();
        String password = txtFieldpassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            raiseError("login credential error", "Username and password must not be empty");
            return;
        }
        try (Socket socket = new Socket(IP, PORT)) {
            socket.setSoTimeout(1000);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("login");
            output.println(username);
            output.println(toHexString(getSHA(password)));
            output.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            User s = (User)ois.readObject();
            if(s != null){
                DefaultController.initUserDetails(s);
                screenController.addScreen("choose", "/view/chooseYourRole.fxml");
                screenController.activate("choose");
            }else{
                raiseError("Wrong login information","Username or password are incorrect");
            }
        } catch (UnknownHostException ex) {
            raiseError("Connection error","Server not found");
        } catch (IOException ex) {
            raiseError("Connection error","Could'nt connect to db");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error with sha256 algorithm!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
