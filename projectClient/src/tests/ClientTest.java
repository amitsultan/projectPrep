package tests;
import controllers.Controller;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
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
import org.testfx.*;


public class ClientTest extends ApplicationTest{


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Hello World");
        Scene main = new Scene(root, 300, 400);
        primaryStage.setScene(main);
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
    public void testLoginBtn () throws InterruptedException {
        write("amitsul",100);
        type(KeyCode.TAB);
        write("asgsag");
        clickOn("Login");
        try{
            clickOn("OK");
        }catch (Exception e){
            Assert.fail();
        }
    }
}
