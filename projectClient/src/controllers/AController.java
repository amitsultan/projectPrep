package controllers;

import com.google.gson.Gson;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

abstract class AController {

    protected static String IP;
    protected static int PORT;
    static ScreenController screenController;

    static {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get("resources/serverConfig.json"));
            Map<?, ?> config = gson.fromJson(reader, Map.class);
            IP = (String) config.get("ip");
            PORT = Integer.parseInt((String) config.get("port"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    protected static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }


    protected void raiseError(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error has occurred");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
