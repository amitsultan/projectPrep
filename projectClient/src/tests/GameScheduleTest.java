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

public class GameScheduleTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new User("Marina","B","marinab","123456");
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameSchedulingPolicy.fxml"));
        primaryStage.setTitle("Test Scheduling");
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
    public void testUpdate(){
        try {
            ChoiceBox box = (ChoiceBox) scene.lookup("#policyChooser");
            box.getItems().add("policyTest");
            clickOn("#policyChooser");
            clickOn("policyTest");
            ChoiceBox box2 = (ChoiceBox) scene.lookup("#leagueChooser");
            box2.getItems().add("leagueTest");
            clickOn("#leagueChooser");
            clickOn("leagueTest");
            ChoiceBox box3 = (ChoiceBox) scene.lookup("#seasonChooser");
            box3.getItems().add("seasonTest");
            clickOn("#seasonChooser");
            clickOn("seasonTest");
            clickOn("Set Policy");
            clickOn("OK");
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testBackBtn(){
        try {
            clickOn("Back");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#chooserPane");
            System.out.println(pane);
            if (pane == null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
