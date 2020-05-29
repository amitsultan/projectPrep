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

public class FootballAssociationTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new User(2,"Dor","Elkabetz","dore","dor931992");
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/FootballAssociationScreen.fxml"));
        primaryStage.setTitle("Test Football Association Screen");
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
    public void testBackBtn(){
        try{
            clickOn("back");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#chooserPane");
            if (pane == null) {
                Assert.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGoToPageBtn() {
        try {
            ChoiceBox box = (ChoiceBox) scene.lookup("#chooser");
            if (box.getItems().isEmpty()) {
                Assert.fail();
            }
            clickOn("Go To Page");
            try {
                clickOn("OK");
            } catch (Exception e) {
                Assert.fail();
            }
            String role = (String) box.getItems().get(0);
            clickOn("#chooser");
            clickOn(role);
            clickOn("Go To Page");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#AssociationPane");
            if (pane != null) {
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
