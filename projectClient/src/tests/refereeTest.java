package tests;

import controllers.Controller;
import controllers.DefaultController;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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


public class refereeTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new User("Marina","B","Marina","m");
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/refereePage.fxml"));
        primaryStage.setTitle("Test Referee");
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
    public void testLblWelcome(){
        Label label = (Label) scene.lookup("#lblReferee");
        if(!label.getText().contains("Marina")){
            Assert.fail();
        }
    }

    @Test
    public void testUpdate(){
        try {
            ChoiceBox box = (ChoiceBox) scene.lookup("#gameChooser");
            box.getItems().add("gameTest");
            clickOn("#gameChooser");
            clickOn("gameTest");
            ChoiceBox box2 = (ChoiceBox) scene.lookup("#eventChooser");
            box2.getItems().add("eventTest");
            if(!box2.isDisable()){
                clickOn("#eventChooser");
                clickOn("eventTest");
            }
            else{
                Assert.fail();
            }
            Node com = scene.lookup("#comments");
            if(com.isDisable()){
                Assert.fail();
            }
            else {
                clickOn("#comments");
                write("testing", 100);
            }
            clickOn("Update Event");
        }
        catch (Exception e){
            //TODO: Check what happened to wrong event request (game not exist)
        }
    }

    @Test
    public void testBackBtn(){
        try {
            clickOn("Back");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#chooserPane");
            if (pane == null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            Assert.fail();
        }
    }

}
