package tests;

import controllers.ChooseController;
import controllers.Controller;
import controllers.DefaultController;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class DefaultTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new User("Marina","B","Marina","m");
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/default.fxml"));
        primaryStage.setTitle("Test Register");
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
    public void testRoleBtn(){
        try {
            //ChooseController chooseController = new ChooseController();
            ChooseController.choice = "referee";
            clickOn("See current Role");
            Label lblRole = (Label) scene.lookup("#lblRole");
            Label lblWelcome = (Label) scene.lookup("#lblWelcome");
            if(!lblRole.getText().contains("referee") || !lblWelcome.getText().contains("Marina")){
                Assert.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testBackBtn(){
        try {
            clickOn("Back To Roles");
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
