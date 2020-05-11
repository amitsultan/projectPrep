package sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import users.User;

public class Controller {

    static {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("resources/config.json"));
            Map<?, ?> config = gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Config file not found");
            System.exit(-1);
        }
    }

    // buttons
    @FXML
    public Button btnTest = new Button();
    public TextField txtFieldusername;
    public TextField txtFieldpassword;
    // configuration
    private Map<?, ?> config;

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }


    @FXML
    private void loginBtn(ActionEvent event) {
        String username = txtFieldusername.getText();
        String password = txtFieldpassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            raiseError("login credential error", "Username and password must not be empty");
            return;
        }
        try (Socket socket = new Socket("132.72.65.62", 6969)) {
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.println("login");
            output.println(username);
            output.println(toHexString(getSHA(password)));
            output.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            User s = (User)ois.readObject();
            System.out.println(s);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error with sha256 algorithm!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void raiseError(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error has occurred");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
