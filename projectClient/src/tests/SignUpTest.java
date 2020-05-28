package tests;

import controllers.Controller;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


public class SignUpTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/register.fxml"));
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
    public void testSignUp() throws InterruptedException {
        //username
        write("marinaBot", 100);
        type(KeyCode.TAB);
        //password
        write("123456Mm!", 100);
        type(KeyCode.TAB);
        //firstName
        write("Marina", 100);
        type(KeyCode.TAB);
        //lastName
        write("Botvinnik", 100);
        clickOn("Register");
        try {
            clickOn("OK");
        } catch (Exception e) {
            Assert.fail();
        }

        /**
         * Test for incorrect password
         */
        doubleClickOn(scene.lookup("#usernameTxt"));
        //username
        write("marinaBot", 100);
        type(KeyCode.TAB);
        //password
        write("1234", 100);
        type(KeyCode.TAB);
        //firstName
        write("Marina", 100);
        type(KeyCode.TAB);
        //lastName
        write("Botvinnik", 100);
        clickOn("Register");
        try {
            clickOn("OK");
        } catch (Exception e) {
            Assert.fail();
        }

        /**
         * Test for one empty cell
         */
        doubleClickOn(scene.lookup("#usernameTxt"));
        //username
        write("marinaBot", 100);
        type(KeyCode.TAB);
        //password
        write("123456Mm!", 100);
        type(KeyCode.TAB);
        //firstName
        doubleClickOn(scene.lookup("#fnameTxt"));
        type(KeyCode.DELETE);
        type(KeyCode.TAB);
        //lastName
        write("Botvinnik", 100);
        clickOn("Register");
        try {
            clickOn("OK");
        } catch (Exception e) {
            Assert.fail();
        }

        /**
         * Test for incorrect userName
         */
        doubleClickOn(scene.lookup("#usernameTxt"));
        //username
        write("mar", 100);
        type(KeyCode.TAB);
        //password
        write("123456Mm!", 100);
        type(KeyCode.TAB);
        //firstName
        write("Marina", 100);
        type(KeyCode.TAB);
        //lastName
        write("Botvinnik", 100);
        clickOn("Register");
        try {
            clickOn("OK");
        } catch (Exception e) {
            Assert.fail();
        }

        /**
         * Test for correct register
         */

        //TODO: how to check without adding to DB
    }

    @Test
    public void testBackBtn(){
        try {
            clickOn("Back To Login");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#loginPane");
            if (pane == null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
