import clientViewModel.MultiThreadedServer;

public class main {
    public static void main(String[] args){
        MultiThreadedServer server = new MultiThreadedServer(6969);
        server.run();
    }
}