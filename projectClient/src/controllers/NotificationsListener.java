package controllers;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class NotificationsListener implements Runnable {

    protected int serverPort;
    protected String ip;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    public NotificationsListener(int port, String ip) {
        this.serverPort = port;
        this.ip = ip;
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(handelNotifications(clientSocket)).start();
        }
        System.out.println("Server Stopped.");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(new InetSocketAddress(this.ip, this.serverPort));
            this.serverPort = this.serverSocket.getLocalPort();
            System.out.println("Server is running at port: "+ serverPort + "with ip: " + this.ip);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }

    public static Runnable handelNotifications(Socket clientSocket){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String action = input.readLine();
            if(action.equals("UPDATE")){
                String update = input.readLine();
                showAlert(update);
            } else if(action.equals("Ping")) {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject("Pong");
                out.close();
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId("notification");
        alert.setTitle("New Notification");
        alert.setHeaderText("Received a new notification");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public int getServerPort() {
        return this.serverSocket.getLocalPort();
    }
}