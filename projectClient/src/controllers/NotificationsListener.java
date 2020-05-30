package controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.net.*;
import java.io.IOException;

public class NotificationsListener extends Thread {

    private static boolean run;
    private DatagramSocket socket;
    private byte[] buf = new byte[256];

    public NotificationsListener() throws SocketException {
        socket = new DatagramSocket(5005);
        run = true;
    }

    public void run() {
        try{
            while (run) {
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.equals("Ping")) {
                    socket.send(packet);
                } else if (received.equals("Close")) {
                    socket.close();
                    break;
                } else {
                    showAlert(received);
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 5005", e);
        }
    }

    protected static void showAlert(String msg) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setId("notification");
                alert.setTitle("New Notification");
                alert.setHeaderText("Received a new notification");
                alert.setContentText(msg);
                alert.showAndWait();
                return null;
            }
        });
    }

    public static void stopListening(){
        run = false;
    }
}