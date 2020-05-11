package clientViewModel;

import java.io.*;
import java.net.Socket;

public class ViewModel {
    private ViewModel viewModel;

    private ViewModel() {

    }

    public static Runnable handel(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String username = input.readLine();
            System.out.println("Username: " + username);
            String password = input.readLine();
            System.out.println("password: " + password);
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write("Hello world bebe".getBytes());
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        return null;
    }

    public ViewModel getInstance(){
        if(viewModel == null){
            viewModel = new ViewModel();
        }
        return viewModel;
    }
}
