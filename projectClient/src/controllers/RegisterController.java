package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import users.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class RegisterController extends AController{

    @FXML
    public TextField usernameTxt;
    public PasswordField passwordTxt;
    public TextField fnameTxt;
    public TextField lnameTxt;


    @FXML
    private void registerBtn(ActionEvent event) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String fname = fnameTxt.getText();
        String lname = lnameTxt.getText();
//      (?=.*[0-9])       # a digit must occur at least once
//      (?=.*[a-z])       # a lower case letter must occur at least once
//      (?=.*[A-Z])       # an upper case letter must occur at least once
//      (?=.*[@#$%^&+=])  # a special character must occur at least once
//      (?=\S+$)          # no whitespace allowed in the entire string
//      .{8,}             # anything, at least eight places though
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,}$";
        if(username.isEmpty() || password.isEmpty() || fname.isEmpty() || lname.isEmpty()){
            raiseError("Empty fields","All fields must'nt be empty");
            return;
        }
        if(username.length() < 6 || username.contains(" ")){
            raiseError("Username is wrong","Username must contain at least 6 characters with no spaces");
            return;
        }
        if(!password.matches(pattern)){
            raiseError("Password is wrong","Password must be at least " +
                    "8 characters with digits" +
                    ", lower/upper case and special character" +
                    "and not contain spaces");
            return;
        }
        try {
            password = toHexString(getSHA(password));
            Socket socket = new Socket(IP, PORT);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("register");
            output.println(username);
            output.println(password);
            output.println(fname);
            output.println(lname);
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            output.close();
            if(obj instanceof String){
                raiseError("Error occurred", (String) obj);
            }else{
                HomeController.initUserDetails((User)obj);
                screenController.addScreen("login","/view/home.fxml");
                screenController.activate("login");
            }
        } catch (NoSuchAlgorithmException e) {
            raiseError("Registration failed","Please try again later");
            e.printStackTrace();
            return;
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
