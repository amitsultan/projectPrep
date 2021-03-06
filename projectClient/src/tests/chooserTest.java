package tests;

import controllers.Controller;
import controllers.DefaultController;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import users.User;

import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class chooserTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    private User user;
    protected static String IP;
    protected static int PORT;

    @Override
    public void start(Stage primaryStage) throws Exception{
        IP = "132.72.65.62";
        PORT = Integer.parseInt("6969");
        Socket socket = new Socket(IP, PORT);
        socket.setSoTimeout(1000);
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        output.println("login");
        output.println("dore");
        output.println("19ada01725d49e01122c77c53f5ddbedb6d0408b3333273d9e9e640be614498");
        output.flush();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        user = (User) ois.readObject();
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/chooseYourRole.fxml"));
        primaryStage.setTitle("Test Chooser");
        Scene main = new Scene(root, 600, 400);
        primaryStage.setScene(main);
        this.scene = main;
        this.stage = primaryStage;
        primaryStage.show();
        ScreenController screenController = new ScreenController(primaryStage);
        Controller.initController(screenController);
    }
    @Before
    public void setUp () throws Exception {

    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testChooseBtn(){
        try {
            ChoiceBox box = (ChoiceBox) scene.lookup("#chooser");
            if(box.getItems().isEmpty()){
                Assert.fail();
            }
            clickOn("Go To Page");
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            String role = (String) box.getItems().get(0);
            clickOn("#chooser");
            clickOn(role);
            clickOn("Go To Page");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#chooserPane");
            if (pane != null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}
